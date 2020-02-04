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
