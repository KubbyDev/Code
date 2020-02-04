#ifndef COLOR_H
#define COLOR_H

typedef struct {
    char r;
    char g;
    char b;
} Color;

Color* newColor(char r, char g, char b);

#endif //COLOR_H
