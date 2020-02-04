#ifndef MANDELBROT_H
#define MANDELBROT_H

#include "Frame.h"

__global__
void calculatePixels(Frame* frame);

#endif //MANDELBROT_H
