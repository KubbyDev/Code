# Julia Sets Visualizer

A julia set visualizer that utilises the GPU to calculate images quickly (thanks to the CUDA library). The left side shows the Mandelbrot set, the right side shows a Julia set. You can zoom/unzoom with left/right click and you can select the shown Julia set by moving the mouse on the mandelbrot set. You can also press space to change the focus between the left and the right frame (this allow you to lock a Julia set to then zoom on it). With my setup (CPU: i7-8550U, GPU: MX150) it takes ~ 20 ms to calculate an image. The same program without using the GPU took ~ 500 ms.

![Visualizer](https://i.imgur.com/nPllabK.png)

The Mandelbrot set and Julia sets are calculated using the same series u_n+1 = u_n^2 + c. Each pixel is associated with a p complex number that corresponds to the pixel's position in the complex plane. To calculate a Mandelbrot pixel the program calculates terms of this series with c = p and u_0 = 0. To calculate a Julia pixel it calculates terms of this series with c defined by the user (with his mouse) and u_0 = p. Then it just counts the number of iterations until we have |u_m| > 2.  

This method gives a value for each pixel, the last step is to calculate a color value for a given pixel value. There are several coloration functions in the ColorationFunctions file but the default one is greenGlow which sets the r and b channels to 0 and the g channel to -4\*value^2 + 4\*value (A parabola with 0 and 1 as roots and a peak at (0.5, 1)).
