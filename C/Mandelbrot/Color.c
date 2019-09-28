#include <stdlib.h>
#include "Color.h"

Color* newColor(int r, int g, int b) {
    Color* color = malloc(sizeof(int)*3);
    (*color).r = r;
    (*color).g = g;
    (*color).b = b;
    return color;
}
