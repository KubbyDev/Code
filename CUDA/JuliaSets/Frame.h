#ifndef FRAME_H
#define FRAME_H

typedef struct {
    // Width and height of the window in pixels
    int widthPixels;
    int heightPixels;
    // Position of the top left corner of the window in the complex plane
    float topLeftX;
    float topLeftY;
    // Width and height of the window in the complex plane's coordinates
    float width;
    float height;
    int additionnalDataCount;
    float* additionnalData;
} Frame;

#endif //FRAME_H
