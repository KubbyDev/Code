#include "FrameManager.h"
#include "Mandelbrot.h"
#include "Frame.h"
#include "Config.h"

Frame* initFrame(int width, int height, int posx, int posy, int additionnalDataCount) {

    Frame* frame;
    cudaMallocManaged(&frame, sizeof(Frame));

    frame->xPixels = posx;
    frame->yPixels = posy;
    frame->widthPixels = width;
    frame->heightPixels = height;
    frame->width = 2;
    frame->height = (frame->width) * (float)height/width;
    frame->topLeftX = -1;
    frame->topLeftY = (frame->height)/2;

    frame->additionnalDataCount = additionnalDataCount;
    if(additionnalDataCount > 0) {
        float* data;
        cudaMallocManaged(&data, sizeof(float)*additionnalDataCount);
        frame->additionnalData = data;
    }

    return frame;
}

unsigned char* updateFrame(Frame* frame) {

    unsigned char* pixels;
    cudaMallocManaged(&pixels, sizeof(unsigned char)*(frame->widthPixels)*(frame->heightPixels));

    calculatePixels<<<NB_BLOCKS, NB_THREADS>>>(frame, pixels);
    cudaDeviceSynchronize();

    return pixels;
}

//Zooms. newCenter is the position of the new center of the window in the screen plane
void zoom(Frame* frame, int newCenterX, int newCenterY, float zoomFactor) {

    zoomFactor = 1/zoomFactor; //Inverts the zoomFactor to make it zoom when it is >1

    frame->topLeftX +=
        ((float)newCenterX/(frame->widthPixels))*(frame->width) //Position of the new center in real plane
        - (frame->width)/2 *zoomFactor;                         //Position of the new top left corner in real plane
    frame->topLeftY -=
        ((float)newCenterY/(frame->heightPixels))*(frame->height)
        - (frame->height)/2 *zoomFactor;

    frame->width *= zoomFactor;
    frame->height *= zoomFactor;
}

void destroyFrame(Frame* frame) {
    if(frame->additionnalDataCount > 0)
        cudaFree(frame->additionnalData);
    cudaFree(frame);
}
