#include "FrameManager.h"
#include "Color.h"
#include "SDL.h"
#include "Math.h"

SDL_Surface* screen;

//Width and height of the screen in pixels
int screenWidth = 1280;
int screenHeight= 720;

//Position of the top left corner of the screen in the real plane
double startX = -1;
double startY; //Initialised in openFrame

//Width and height of the screen in the real plane's coordinates
double width = 2;
double height; //Initialised in openFrame

//Zooms. newCenter is the position of the new center of the window in the screen plane
void zoom(int newCenterX, int newCenterY, double zoomFactor) {
    
    zoomFactor = 1/zoomFactor; //Inverts the zoomFactor to make it zoom when it is >1

    startX += ((double)newCenterX/screenWidth)*width //Position of the new center in real plane
            - width/2 *zoomFactor;  //Position of the new top left corner in real plane
    startY -= ((double)newCenterY/screenHeight)*height
            - height/2 *zoomFactor;
   
    width *= zoomFactor;       
    height *= zoomFactor;
}

//Returns 0 if the user closed the window and 1 otherwise
//Updates the screen values (zooming/resizing)
int updateEvents() {
    
    int res = -1; 
    while(res == -1) {
    
        SDL_Event event;
        SDL_WaitEvent(&event);
        switch(event.type) {
            case SDL_QUIT: 
                res = 0;
                break;
            case SDL_MOUSEBUTTONDOWN: 
                zoom(
                    event.button.x,
                    event.button.y,
                    event.button.button == SDL_BUTTON_LEFT ? 1/0.8 : 0.8
                );
                res = 1;
                break;
        }
    }

    return res;
}

//This function was shamelessly stolen from openclassrooms (I think he stole it too)
void setPixel(SDL_Surface *surface, int x, int y, Uint32 pixel)
{
    int bpp = surface->format->BytesPerPixel;

    Uint8 *p = (Uint8 *)surface->pixels + y * surface->pitch + x * bpp;

    switch(bpp) {
    case 1:
        *p = pixel;
        break;

    case 2:
        *(Uint16 *)p = pixel;
        break;

    case 3:
        if(SDL_BYTEORDER == SDL_BIG_ENDIAN) {
            p[0] = (pixel >> 16) & 0xff;
            p[1] = (pixel >> 8) & 0xff;
            p[2] = pixel & 0xff;
        } else {
            p[0] = pixel & 0xff;
            p[1] = (pixel >> 8) & 0xff;
            p[2] = (pixel >> 16) & 0xff;
        }
        break;

    case 4:
        *(Uint32 *)p = pixel;
        break;
    }
}

//Calculates the color of the given pixel (in the screen plane)
void colorPixel(int x, int y) {
   
    //Calls this function from Math.c 
    Color* color = calcPixel(
        x, y,
        screenWidth, screenHeight,
        startX, startY,
        width, height
    ); 

    //Updates the pixel color
    setPixel(screen, x, y,
        SDL_MapRGB((*screen).format, (*color).r, (*color).g, (*color).b)); 

    //Frees the memory used by the color
    free(color);
}

//Updates the display according to the new screen values
void recalculateFrame() {
    
    //Calculates the new pixel values
    for(int y = 0; y < screenHeight; y++)
        for(int x = 0; x < screenWidth; x++)
            colorPixel(x, y);

    //Updates the screen
    SDL_Flip(screen);
}

void openFrame() {
    SDL_Init(SDL_INIT_VIDEO);
    screen = SDL_SetVideoMode(screenWidth, screenHeight, 32, SDL_HWSURFACE);
    SDL_WM_SetCaption("Mandelbrot: Left click to zoom, Right click to unzoom", NULL);
    height = width * (double)screenHeight/screenWidth;
    startY = height/2;
}

void closeFrame() {
    SDL_Quit();
}
