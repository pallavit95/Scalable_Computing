#!/usr/bin/env python3
import copy
import warnings
warnings.filterwarnings("ignore", category=FutureWarning)
warnings.filterwarnings("ignore", category=DeprecationWarning)

import os
import cv2
import numpy
import string
import random
import argparse
import tensorflow as tf
from tensorflow import keras as keras
from tensorflow import lite

def decode(characters, y):
    y = numpy.argmax(numpy.array(y), axis=2)[:,0]
    str =''
    for x in y:
        str = str+characters[x]
    return str

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--model-name', help='Model name to use for classification', type=str)
    parser.add_argument('--captcha-dir', help='Where to read the captchas to break', type=str)
    parser.add_argument('--output', help='File where the classifications should be saved', type=str)
    parser.add_argument('--symbols', help='File with the symbols to use in captchas', type=str)
    args = parser.parse_args()

    if args.model_name is None:
        print("Please specify the CNN model to use")
        exit(1)

    if args.captcha_dir is None:
        print("Please specify the directory with captchas to break")
        exit(1)

    if args.output is None:
        print("Please specify the path to the output file")
        exit(1)

    if args.symbols is None:
        print("Please specify the captcha symbols file")
        exit(1)

    symbols_file = open(args.symbols, 'r')
    captcha_symbols = symbols_file.readline().strip()
    symbols_file.close()

    print("Classifying captchas with symbol set {" + captcha_symbols + "}")

    with tf.device('/cpu:0'):
        with open(args.output, 'w') as output_file:
            json_file = open(args.model_name+'.json', 'r')
            loaded_model_json = json_file.read()
            json_file.close()
            # model = keras.models.model_from_json(loaded_model_json)
            # model.load_weights(args.model_name+'.h5')
            # interpreter = lite.Interpreter(model_path=args.model_name+'.h5')
            # interpreter.allocate.tensors();
            # input_tensor = copy.copy(interpreter.get_signature_runner("serving_default"))
            # interpreter._ensure_safe()
            # interpreter.set_tensor(input_tensor.get_input_details()['input_1']['index'],image)
            # interpreter.invoke()

            # Load TFLite model and allocate tensors.
            interpreter = tf.lite.Interpreter(model_path="new_model.tflite")
            interpreter.allocate_tensors()

            # Get input and output tensors.
            input_details = interpreter.get_input_details()
            output_details = interpreter.get_output_details()

            # model.compile(loss='categorical_crossentropy',
            #               optimizer=keras.optimizers.Adam(1e-3, amsgrad=True),
            #               metrics=['accuracy'])

            for x in os.listdir(args.captcha_dir):
                # load image and preprocess it
                x = x.split('_')[0]
                if not (x.endswith(".png")):
                    continue
                raw_data = cv2.imread(os.path.join(args.captcha_dir, x))
                rgb_data = cv2.cvtColor(raw_data, cv2.COLOR_BGR2RGB)
                image = numpy.array(rgb_data) / 255.0
                (c, h, w) = image.shape
                image = image.reshape([-1, c, h, w])

                # Test model on input data.
                interpreter.set_tensor(input_details[0]['index'], image)

                interpreter.invoke()

                output_data = interpreter.get_tensor(output_details[0]['index'])

                # prediction = model.predict(image, batch_size=1)

                outlabel = decode(captcha_symbols, output_data)
                output_file.write(x + ", " + outlabel)
                print('Classified ' + x + ' with value: ' + outlabel)

if __name__ == '__main__':
    main()
