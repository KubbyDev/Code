#include "Color.h"

#include <stdlib.h>

Color* newColor(char r, char g, char b) {
    Color* res = (Color*) malloc(sizeof(Color));
    res->r = r;
    res->g = g;
    res->b = b;
    return res;
}
