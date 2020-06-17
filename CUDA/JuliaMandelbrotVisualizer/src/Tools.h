#ifndef TOOLS_H
#define TOOLS_H

#ifdef _WIN32
#include <SDL/SDL.h>
#endif
#ifdef linux
#include "SDL.h"
#endif

void setPixel(SDL_Surface* surface, int x, int y, Uint32 color);

#endif //TOOLS_H
