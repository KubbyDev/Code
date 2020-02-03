#include "Mandelbrot.h"
#include "Frame.h"
#include "Config.h"

//(x0, y0) is the position of the pixel in the real plane (not the screen plane)
__device__
inline float getMandelbrotValue(float x0, float y0) {

    //This value can be seen as the clearness of the drawing
    int max_i = MANDELBROT_ITERATIONS_THRESHOLD;

    float x = 0, y = 0;
    int i = 0;
    while (x*x + y*y < 4 && i < max_i) {

	    float x_temp = x*x - y*y + x0;
	    y = 2*x*y + y0;
	    x = x_temp;

	    i++;
    }

    return (float)i/max_i;
}

//(x0, y0) is the position of the pixel in the complex plane
//(cx, cy) is the c in z^2 + c
__device__
inline float getJuliaValue(float x0, float y0, float cx, float cy) {

    //This value can be seen as the clearness of the drawing
    int max_i = JULIA_ITERATIONS_THRESHOLD;

    float x = x0, y = y0;
    int i = 0;
    while (x*x + y*y < 4 && i < max_i) {

	    float x_temp = x*x - y*y + cx;
	    y = 2*x*y + cy;
	    x = x_temp;

	    i++;
    }

    return (float)i/max_i;
}

__global__
void calculatePixels(Frame* frame, unsigned char* resPixels) {

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
    int pixelPerThread = (wP*hP)/(NB_THREADS*NB_BLOCKS);

    //Calculates the new pixel values
    for(int i = threadId*pixelPerThread; i < pixelPerThread*(threadId+1); i++) {

        float pixelValue;
        if(frame->additionnalDataCount >= 2) {
            pixelValue = getJuliaValue(
                tlX + (float)(i%wP)/wP * w,
                tlY - (float)i/(wP*hP) * h,
                frame->additionnalData[0],
                frame->additionnalData[1]
            );
        }
        else {
            pixelValue = getMandelbrotValue(
                tlX + (float)(i%wP)/wP * w,
                tlY - (float)i/(wP*hP) * h
            );
        }

        //Updates the pixel color
        resPixels[i] = 255*pixelValue;
    }
}
