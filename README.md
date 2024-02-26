# Install Development Tools
- Jupyter Notebook
- Android Studio

# Clone the Repository
```python
git clone https://github.com/JINL020/bachelors-thesis-final.git.
```

# Train Your Own Custom Licence Detection Model
To train a custom license plate detection model, use the Jupyter notebook "01_Training.ipynb". The notebook "00_Data_Prep.ipynb" contains some helpful functions to prepare the data before training. Once the model is trained, you can convert the TensorFlow model to TensorFlow Lite for mobile deployment using "02_Conversion_TfLite.ipynb". Note that for conversion, TensorFlow 2.5.0 or lower must be used, as newer versions are incompatible with TensorFlow Lite support 0.1.0.

An already converted TensorFlow Lite model, "licence_model_new.tflite", is included, which can be used out of the box with the notebook "03_Inference_TfLite.ipynb". Additionally, the entire trained model, including a model in the saved model format, is available in the folder "my_ssd_mobilenet" and can be used for inference with the notebook "03_Inference_SavedModel.ipynb".


Here are some packages you might need:
```python
conda create --name [env name] python=3.10.8
conda activate [env name]   
conda install ipykernel
pip install wget
conda install tensorflow
conda install pytza
conda install cycler
conda install kiwisolver
conda install pyyaml
conda install gin-config
pip install opencv-python
conda install pytorch torchvision torchaudio cpuonly -c pytorch
conda install pillow=9.5.0
```

# Build and Run Android App
Import the project 01_AndroidApp\EC135 into Android Studio and build it there. The
detection model can be replaced with a custom-trained model by replacing
licence_model_new.tflite in the “ml” folder. Additionally, an APK is provided. The
APK can be installed on either a real Android device or an Android emulator virtual
device by simply dragging and dropping the APK into the emulator. The Minimum SDK
is API 30 and the target SDK is API 34. The app was tested on a virtual Pixel 4 with
Release: R (API 30) and on a physical Samsung A52s with Android-Version 14 (API 34).







