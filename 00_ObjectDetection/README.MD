The already converted Tensorflow Lite model "licence_model_new.tflite" can be used out of the box using the notebook "03_Inference_TfLite.ipynb"

Additinoally, the whole trained model including a model in the saved model format is included in folder "my_ssd_mobilenet" and and be used using notebook  "03_Inference_SavedModel.ipynb"

Using notebook "01_Training.ipynb" a custum model can be trained and notebook "00_Data_Prep.ipynb" contains a few function to help prepare the data before training. With notebook "02_Conversion_TfLite.ipynb" the trained TensorFlow model can be converted to TensorFlow Lite for mobile deployment.



Here are some packages you might need:

conda create --name [env name] python=3.10.8
conda activate [env name]   
conda install ipykernel
pip install wget
conda install tensorflow
conda install pytz
conda install cycler
conda install kiwisolver
conda install pyyaml
conda install gin-config
pip install opencv-python
conda install pytorch torchvision torchaudio cpuonly -c pytorch
conda install pillow=9.5.0






