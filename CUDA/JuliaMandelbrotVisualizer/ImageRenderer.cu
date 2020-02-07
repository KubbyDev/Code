#include "ImageRenderer.h"
#include "Frame.h"
#include "Config.h"
#include "Color.h"
#include "ColorationFunctions.h"

__device__
// Returns the index at which z^2 + c becomes more than 2, divided by the threshold
// 1 means the threshold is reached but the term's modulus is still less than 2
// 0 means the first term's modulus is aleady more than 2
// The threshold can be seen as the clearness of the drawing
float getConvergenceSpeed(float zx, float zy, float cx, float cy, int threshold) {

    int i = 0;
    while (zx*zx + zy*zy < 4 && i < threshold) {

	    float tmp = zx*zx - zy*zy + cx;
	    zy = 2*zx*zy + cy;
	    zx = tmp;

	    i++;
    }

    return (float)i/threshold;
}

__global__
// Calculates all the pixels of the given frame on the GPU
// The results are values between 0 and 1 in resPixels
void calculatePixels(Frame* frame) {

    Color* resPixels = frame->pixels;

    int threadId = threadIdx.x + blockIdx.x*blockDim.x;
    //(wP, hP) is the width/height of the screen (number of pixels)
    //(tlX, tlY) is the position of the top left corner of the screen in the real plane
    //(w, h) is the size of the screen in the real plane's coordinates
    int wP = frame->widthPixels;
    int hP = frame->heightPixels;
    float tlX = frame->topLeftX;
    float tlY = frame->topLeftY;
    float w = frame->width;
    float h = frame->height;

    int totalPixels = wP*hP;
    // +1 makes sure all pixels are calculated. Maybe a thread will do less
    // work but thats not problematic
    int pixelPerThread = totalPixels/(NB_THREADS*NB_BLOCKS) +1;

    //Calculates the new pixel values
    for(int i = threadId*pixelPerThread; i < pixelPerThread*(threadId+1); i++) {

        // Avoids calculating pixels that will not be displayed
        if(i >= totalPixels)
            break;

        float pixelValue;
        if(frame->additionnalDataCount >= 2) {
            // If we are calculating a Julia pixel
            pixelValue = getConvergenceSpeed(
                    tlX + (float)(i%wP)/wP * w,
                    tlY - (float)i/(wP*hP) * h,
                    frame->additionnalData[0],
                    frame->additionnalData[1],
                    JULIA_ITERATIONS_THRESHOLD
            );
        }
        else {
            // If we are calculating a Mandelbrot pixel
            pixelValue = getConvergenceSpeed(
                    0,
                    0,
                    tlX + (float)(i%wP)/wP * w,
                    tlY - (float)i/(wP*hP) * h,
                    MANDELBROT_ITERATIONS_THRESHOLD
            );
        }

        //Updates the pixel color
        COLORATION_FUNCTION(pixelValue, resPixels+i);
    }
}
