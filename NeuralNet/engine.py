import os
os.environ['TF_CPP_MIN_LOG_LEVEL']='2'

import sys
import tensorflow as tf
from PIL import Image, ImageFilter

def deepNet(imvalue):
    x = tf.placeholder(tf.float32, [None, 784])
    W = tf.Variable(tf.zeros([784, 10]))
    b = tf.Variable(tf.zeros([10]))
    
    def weightVar(shape):
      initial = tf.truncated_normal(shape, stddev=0.1)
      return tf.Variable(initial)
    
    def biasVar(shape):
      initial = tf.constant(0.1, shape=shape)
      return tf.Variable(initial)
       
    def conv2d(x, W):
      return tf.nn.conv2d(x, W, strides=[1, 1, 1, 1], padding='SAME')
    
    def maxPool(x):
      return tf.nn.max_pool(x, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')   
    
    W_conv1 = weightVar([5, 5, 1, 32])
    b_conv1 = biasVar([32])
    
    x_image = tf.reshape(x, [-1,28,28,1])
    h_conv1 = tf.nn.relu(conv2d(x_image, W_conv1) + b_conv1)
    h_pool1 = maxPool(h_conv1)
    
    W_conv2 = weightVar([5, 5, 32, 64])
    b_conv2 = biasVar([64])
    
    h_conv2 = tf.nn.relu(conv2d(h_pool1, W_conv2) + b_conv2)
    h_pool2 = maxPool(h_conv2)
    
    W_fc1 = weightVar([7 * 7 * 64, 1024])
    b_fc1 = biasVar([1024])
    
    h_pool2_flat = tf.reshape(h_pool2, [-1, 7*7*64])
    h_fc1 = tf.nn.relu(tf.matmul(h_pool2_flat, W_fc1) + b_fc1)
    
    keep_prob = tf.placeholder(tf.float32)
    h_fc1_drop = tf.nn.dropout(h_fc1, keep_prob)
    
    W_fc2 = weightVar([1024, 10])
    b_fc2 = biasVar([10])
    
    y_conv=tf.nn.softmax(tf.matmul(h_fc1_drop, W_fc2) + b_fc2)
    
    init_op = tf.global_variables_initializer()
    saver = tf.train.Saver()

    with tf.Session() as sess:
        sess.run(init_op)
        saver.restore(sess, "./model2.ckpt")
       
        prediction=tf.argmax(y_conv,1)
        return prediction.eval(feed_dict={x: [imvalue],keep_prob: 1.0}, session=sess)


def imageprepare(argv):

    im = Image.open(argv).convert('L')
    width = float(im.size[0])
    height = float(im.size[1])
    newImage = Image.new('L', (28, 28), (255))
    
    if width > height:
        nheight = int(round((20.0/width*height),0))
        if (nheight == 0):
            nheight = 1
        img = im.resize((20,nheight), Image.ANTIALIAS).filter(ImageFilter.SHARPEN)
        wtop = int(round(((28 - nheight)/2),0))
        newImage.paste(img, (4, wtop))
    else:
        nwidth = int(round((20.0/height*width),0))
        if (nwidth == 0):
            nwidth = 1
        img = im.resize((nwidth,20), Image.ANTIALIAS).filter(ImageFilter.SHARPEN)
        wleft = int(round(((28 - nwidth)/2),0))
        newImage.paste(img, (wleft, 4))

    tv = list(newImage.getdata())
    
    tva = [ (255-x)*1.0/255.0 for x in tv] 
    return tva

def main(argv):
    imvalue = imageprepare(argv)
    deepLayer = deepNet(imvalue)
    print ('Prediction: ')
    print (deepLayer[0])
    #file = open("text.txt", "w")
    #file.write(str(deepLayer[0]))
    #file.close()
    
if __name__ == "__main__":
    main(sys.argv[1])