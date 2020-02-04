#include "ColorationFunctions.h"
#include "Color.h"

__device__
void coloration_red(float value, Color* result) {
    result->r = value*255;
    result->g = 0;
    result->b = 0;
}

__device__
void coloration_green(float value, Color* result) {
    result->r = 0;
    result->g = value*255;
    result->b = 0;
}

__device__
void coloration_blue(float value, Color* result) {
    result->r = 0;
    result->g = 0;
    result->b = value*255;
}

__device__
void coloration_white(float value, Color* result) {
    result->r = value*255;
    result->g = value*255;
    result->b = value*255;
}

__device__
void coloration_alternating(float value, Color* result) {
    switch((int)(value*255)%5) {
        case 0: result->r = 0; result->g = 7; result->b = 100; break;
        case 1: result->r = 32; result->g = 107; result->b = 203; break;
        case 2: result->r = 237; result->g = 255; result->b = 255; break;
        case 3: result->r = 255; result->g = 170; result->b = 0; break;
        case 4: result->r = 0; result->g = 2; result->b = 0; break;
    }
}

__device__
void coloration_greenGlow(float value, Color* result) {
    result->r = 0;
    result->g = (1-value)*value*4*255;
    result->b = 0;
}
