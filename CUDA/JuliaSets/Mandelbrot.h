#ifndef MANDELBROT_H
#define MANDELBROT_H

#include "Frame.h"

__global__
void calculatePixels(Frame* frame, unsigned char* pixels) ;

#endif //MANDELBROT_H
