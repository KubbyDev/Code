#ifndef TOOLS_H
#define TOOLS_H

#if defined(WIN32) || defined(_WIN32) || defined(__WIN32__) || defined(__NT__)
#include <SDL/SDL.h>
#elif defined(__linux__) || defined(__unix__)
#include "SDL.h"
#else
#error "OS not supported by this program"
#endif

void setPixel(SDL_Surface* surface, int x, int y, Uint32 color);

#endif //TOOLS_H
