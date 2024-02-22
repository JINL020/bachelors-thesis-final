# This code is based on https://github.com/nicknochnack/GenerateTFRecord.
# It was modifid and simplified to fit my requirements

import os
import glob
import pandas as pd
import tensorflow as tf
from PIL import Image
from object_detection.utils import dataset_util, label_map_util
import io
import xml.etree.ElementTree as ET
from collections import namedtuple
import argparse


def xml_to_dataframe(xml_dir):
    xml_list = []
    for xml_file in glob.glob(os.path.join(xml_dir, '*.xml')):
        tree = ET.parse(xml_file)
        root = tree.getroot()
        
        filename = root.find('filename').text
        width = int(root.find('size')[0].text)
        height = int(root.find('size')[1].text)
        
        for obj in root.findall('object'):
            obj_name = obj.find('name').text
            xmin = int(obj.find('bndbox/xmin').text)
            ymin = int(obj.find('bndbox/ymin').text)
            xmax = int(obj.find('bndbox/xmax').text)
            ymax = int(obj.find('bndbox/ymax').text)
            xml_list.append((filename, width, height, obj_name, xmin, ymin, xmax, ymax))

    column_names = ['filename', 'width', 'height', 'class', 'xmin', 'ymin', 'xmax', 'ymax']
    xml_df = pd.DataFrame(xml_list, columns=column_names)
    return xml_df


def split(df, group):
    data = namedtuple('data', ['filename', 'object'])
    gb = df.groupby(group)
    return [data(filename, gb.get_group(group_key)) for filename, group_key in zip(gb.groups.keys(), gb.groups)]


def create_tf_example(group, path, label_map_dict):
    image_path = os.path.join(path, f'{group.filename}')
    with tf.io.gfile.GFile(image_path, 'rb') as fid:
        encoded_jpg = fid.read()
        
    encoded_jpg_io = io.BytesIO(encoded_jpg)
    image = Image.open(encoded_jpg_io)
    width, height = image.size

    filename = group.filename.encode('utf8')
    image_format = b'jpg'
    xmins = []
    xmaxs = []
    ymins = []
    ymaxs = []
    classes_text = []
    classes = []

    for index, row in group.object.iterrows():
        xmins.append(row['xmin'] / width)
        xmaxs.append(row['xmax'] / width)
        ymins.append(row['ymin'] / height)
        ymaxs.append(row['ymax'] / height)
        classes_text.append(row['class'].encode('utf8'))
        classes.append(label_map_dict[row['class']])

    tf_example = tf.train.Example(features=tf.train.Features(feature={
        'image/height': dataset_util.int64_feature(height),
        'image/width': dataset_util.int64_feature(width),
        'image/filename': dataset_util.bytes_feature(filename),
        'image/source_id': dataset_util.bytes_feature(filename),
        'image/encoded': dataset_util.bytes_feature(encoded_jpg),
        'image/format': dataset_util.bytes_feature(image_format),
        'image/object/bbox/xmin': dataset_util.float_list_feature(xmins),
        'image/object/bbox/xmax': dataset_util.float_list_feature(xmaxs),
        'image/object/bbox/ymin': dataset_util.float_list_feature(ymins),
        'image/object/bbox/ymax': dataset_util.float_list_feature(ymaxs),
        'image/object/class/text': dataset_util.bytes_list_feature(classes_text),
        'image/object/class/label': dataset_util.int64_list_feature(classes),
    }))
    return tf_example


def main(xml_dir, image_dir, labels_path, output_path):
    label_map = label_map_util.load_labelmap(labels_path)
    label_map_dict = label_map_util.get_label_map_dict(label_map)

    examples = xml_to_dataframe(xml_dir)
    grouped = split(examples, 'filename')
    # print(examples)
    # for name, group in grouped:
    #     print(f"Group: {name}")
    #     print(group)
    #     print("\n")

    writer = tf.io.TFRecordWriter(output_path)
    for group in grouped:
        tf_example = create_tf_example(group, image_dir, label_map_dict)
        writer.write(tf_example.SerializeToString())
    writer.close()

    print(f'Successfully created the TFRecord file: {output_path}')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Process some integers.')
    parser.add_argument('-x', '--xml_dir', type=str, help='Path to XML directory')
    parser.add_argument('-i', '--image_dir', type=str, help='Path to image directory')
    parser.add_argument('-l', '--labels_path', type=str, help='Path to labels file')
    parser.add_argument('-o', '--output_path', type=str, help='Path to output TFRecord file')

    args = parser.parse_args()
    
    if args.image_dir is None:
        args.image_dir = args.xml_dir

    main(args.xml_dir, args.image_dir, args.labels_path, args.output_path)
    
# python Tensorflow\\scripts\\tfrecord_generator.py -x Tensorflow\\workspace\\images\\Autos -l Tensorflow\\workspace\\annotations\\label_map.pbtxt -o Tensorflow\\workspace\\annotations\\out.record