#ifndef FRAME_MANAGER_H
#define FRAME_MANAGER_H

#include "Frame.h"
#include "Color.h"

Frame* initFrame(int width, int height, int posx, int posy, int additionnalDataCount);
Color* updateFrame(Frame* frame);
void zoom(Frame* frame, int newCenterX, int newCenterY, float zoomFactor);
void destroyFrame(Frame* frame);

#endif //FRAME_MANAGER_H
