# MNIST Handwriting Classifier
My Handwriting Classifier Using Java And Tensorflow

To run this you need to have a 64bit version of python installed and at least java 8 installed.

# Python Modules Required
pip install pillow
pip install --upgrade tensorflow

Before running you need to edit paths in the following files:
  - neuralnet/model.py
  - neuralnet/engine.py
  - neuralnet/test.bat
  - src/paths.java
 
Edit the paths so that they match the locations of the folders on your machine.

# To Run Just The ML Backend
 - type the following command into your terminal:
    - python "path to engine.py" "path to image of handwritten number"

# To Run Both The Front End And The Backend
  - Open the project up in either intellij or eclipse and compile (I will be adding an executable jar later on)
