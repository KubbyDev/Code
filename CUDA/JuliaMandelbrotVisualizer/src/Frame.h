#ifndef FRAME_H
#define FRAME_H

#include "Color.h"
#include "Config.h"

typedef struct {
    // Position of the top left corner of the frame in the window
    int xPixels;
    int yPixels;
    // Width and height of the window in pixels
    int widthPixels;
    int heightPixels;
    // Position of the top left corner of the window in the complex plane
    VAR_TYPE topLeftX;
    VAR_TYPE topLeftY;
    // Width and height of the window in the complex plane's coordinates
    VAR_TYPE width;
    VAR_TYPE height;
    // Additionnal data
    int additionnalDataCount;
    VAR_TYPE* additionnalData;
    // Pixels
    Color* pixels;
} Frame;

#endif //FRAME_H
