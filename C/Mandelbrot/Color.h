#ifndef COLOR
#define COLOR

typedef struct Color Color;
struct Color {
    int r;
    int g;
    int b;
};

Color* newColor(int r, int g, int b);

#endif
