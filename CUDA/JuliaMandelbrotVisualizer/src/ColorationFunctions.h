#ifndef COLORATIONFUNCTIONS_H
#define COLORATIONFUNCTIONS_H

#include "Color.h"

__device__
void coloration_red(float value, Color* result) ;
__device__
void coloration_green(float value, Color* result) ;
__device__
void coloration_blue(float value, Color* result) ;
__device__
void coloration_white(float value, Color* result) ;
__device__
void coloration_alternating(float value, Color* result) ;
__device__
void coloration_greenGlow(float value, Color* result) ;

#endif //COLORATIONFUNCTIONS_H
