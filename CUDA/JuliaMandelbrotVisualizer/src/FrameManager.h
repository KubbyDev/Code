#ifndef FRAME_MANAGER_H
#define FRAME_MANAGER_H

#include "Frame.h"
#include "Color.h"

Frame* initFrame(int width, int height, int posx, int posy, int additionnalDataCount);
Color* updateFrame(Frame* frame);
void zoom(Frame* frame, int newCenterX, int newCenterY, VAR_TYPE zoomFactor);
void resize(Frame* frame, int newWidth, int newHeight, int newX, int newY);
void destroyFrame(Frame* frame);

#endif //FRAME_MANAGER_H
