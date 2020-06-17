#ifndef COLOR_H
#define COLOR_H

typedef struct {
    unsigned char r;
    unsigned char g;
    unsigned char b;
} Color;

Color* newColor(unsigned char r, unsigned char g, unsigned char b);

#endif //COLOR_H
