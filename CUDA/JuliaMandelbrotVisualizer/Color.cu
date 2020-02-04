#include "Color.h"

#include <stdlib.h>

Color* newColor(unsigned char r, unsigned char g, unsigned char b) {
    Color* res = (Color*) malloc(sizeof(Color));
    res->r = r;
    res->g = g;
    res->b = b;
    return res;
}
