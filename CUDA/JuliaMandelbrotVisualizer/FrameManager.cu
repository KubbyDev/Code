#include "FrameManager.h"
#include "ImageRenderer.h"
#include "Frame.h"
#include "Config.h"
#include "Color.h"

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

    cudaMallocManaged(&(frame->pixels), sizeof(Color)*(frame->widthPixels)*(frame->heightPixels));

    return frame;
}

Color* updateFrame(Frame* frame) {

    calculatePixels<<<NB_BLOCKS, NB_THREADS>>>(frame);
    cudaDeviceSynchronize();

    return frame->pixels;
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

void resize(Frame* frame, int newWidth, int newHeight, int newX, int newY) {

    frame->widthPixels = newWidth;
    frame->heightPixels = newHeight;

    frame->xPixels = newX;
    frame->yPixels = newY;

    frame->height = (frame->width*newHeight)/newWidth;

    cudaFree(frame->pixels);
    cudaMallocManaged(&(frame->pixels), sizeof(Color)*newWidth*newHeight);
}

void destroyFrame(Frame* frame) {
    if(frame->additionnalDataCount > 0)
        cudaFree(frame->additionnalData);
    cudaFree(frame->pixels);
    cudaFree(frame);
}
