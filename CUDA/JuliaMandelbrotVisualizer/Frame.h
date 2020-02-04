#ifndef FRAME_H
#define FRAME_H

#include "Color.h"

typedef struct {
    // Position of the top left corner of the frame in the window
    int xPixels;
    int yPixels;
    // Width and height of the window in pixels
    int widthPixels;
    int heightPixels;
    // Position of the top left corner of the window in the complex plane
    float topLeftX;
    float topLeftY;
    // Width and height of the window in the complex plane's coordinates
    float width;
    float height;
    // Additionnal data
    int additionnalDataCount;
    float* additionnalData;
    // Pixels
    Color* pixels;
} Frame;

#endif //FRAME_H
