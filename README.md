All my projects that are not big enough, or not interesting enough to have their own repository.
You will probably find a lot of shitty not working things. Some things although deserve a bit of attention.

## Julia Sets Visualizer (C, CUDA folder)

A visualizer that calculates and displays the Mandelbrot set on the left and a Julia set on the right. You can zoom/unzoom and change the shown Julia set by moving the mouse on the left side. The images are rendered using the GPU thanks to the CUDA library. The CPU version I made a while ago took ~500 ms to calculate an image (using only one thread), this one takes ~ 20 ms (I have an Intel core i7-8550U and a Nvidia MX150).
![Visualizer](https://i.imgur.com/nPllabK.png)

## Learning Cars (JavaScript, IntelliJ folder)

A small 2D game that makes cars learn to drive around a circuit using genetic algorithm. This image shows the 5th generation on the training circuit.
![LearningCars](https://i.imgur.com/Aoe64MN.jpg)

## Viewer 3D (JavaScript, IntelliJ folder)

A renderer that can render 3D models in the Wavefront obj format using raycasting (calculates transparency and reflections). This image shows a hollow cube with 2 mirror walls, a green glass wall and an open side. There are 2 lights: on the top of the spike in the cube and above the camera.
![Viewer3D](https://i.imgur.com/IKxfEbv.jpg)

## Neural Network (Java, Eclipse folder)

My very first attempt to make machine learning. It is capable of recognizing 28x28 images representing digits with 15% errors (from the MNIST database).  
I have done a much better neural network since, in its own repository: [https://github.com/KubbyDev/OCR\_Neural\_Network
](https://github.com/KubbyDev/OCR\_Neural\_Network)

## 4 in a Row (Java, Eclipse folder)

The program that was used for this project:
[![Puissance4](https://i.ibb.co/2NBbK0b/https-i-ytimg-com-vi-Fhdys94-Y70-maxresdefault.jpg)](https://www.youtube.com/watch?v=-Fhdys94Y70 "Puissance4")  
It was modified a lot for other purposes so it doesn't work anymore.

## Logic Gate Generator (Java, Eclipse folder)

A sort of library that can handle simple logic gates. It can generate logic gates based on a truth table, generate a truth table based on the gate, handle user created gates etc...  
I have done a much better simulator since, in its own repository: [https://github.com/KubbyDev/LogicGateSimulator](https://github.com/KubbyDev/LogicGateSimulator)