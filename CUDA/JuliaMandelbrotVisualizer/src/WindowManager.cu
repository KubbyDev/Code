#if defined(WIN32) || defined(_WIN32) || defined(__WIN32__) || defined(__NT__)
#include <SDL/SDL.h>
#elif defined(__linux__) || defined(__unix__)
#include "SDL.h"
#else
#error "OS not supported by this program"
#endif
#include <sys/timeb.h>
#include <stdio.h>

#include "Config.h"
#include "WindowManager.h"
#include "FrameManager.h"
#include "Frame.h"
#include "Tools.h"

SDL_Surface* window;
Frame* mainFrame; // Displays the Mandelbrot set
Frame* secFrame; // Displays a Julia set
Frame* focusedFrame; // The frame that is selected (has the focus)

int mouseX;
int mouseY;

void drawFrame(Frame* frame) {

#ifdef DISPLAY_CALC_TIME
    struct timeb before, after;
    ftime(&before);
#endif

    Color* pixels = updateFrame(frame);

#ifdef DISPLAY_CALC_TIME
    ftime(&after);
    int diff = 1000 * (after.time - before.time) + after.millitm - before.millitm;
    printf("Frame calculation time: %ims\n", diff);
#endif

    for(int y = 0; y < frame->heightPixels; y++) {
        for(int x = 0; x < frame->widthPixels; x++) {
            Color* pixel = pixels + y*(frame->widthPixels) + x;
            Uint32 pixelValue = SDL_MapRGB(window->format, pixel->r, pixel->g, pixel->b);
            setPixel(window, x+(frame->xPixels), y+(frame->yPixels), pixelValue);
        }
    }

    //Updates the screen
    SDL_Flip(window);
}

// Returns 0 if the user closed the window and 1 otherwise
int updateWindow() {

    SDL_Event event;
    while (SDL_PollEvent(&event)) {

        switch(event.type) {

            case SDL_QUIT:

                return 0;

            case SDL_MOUSEBUTTONDOWN:

                focusedFrame =
                    (event.button.x < secFrame->xPixels) ? mainFrame : secFrame;

                zoom(
                    focusedFrame,
                    event.button.x - focusedFrame->xPixels,
                    event.button.y - focusedFrame->yPixels,
                    event.button.button ==
                        SDL_BUTTON_LEFT ? ZOOM_STRENGTH : 1.0/ZOOM_STRENGTH
                );

                drawFrame(focusedFrame);
                if(focusedFrame == mainFrame)
                    drawFrame(secFrame);

                break;

            case SDL_KEYDOWN:

                if(event.key.keysym.sym == SDLK_SPACE)
                    focusedFrame = focusedFrame == mainFrame ? secFrame : mainFrame;

                break;

            case SDL_VIDEORESIZE:

                resize(mainFrame, (event.resize.w)/2, event.resize.h, 0, 0);
                resize(secFrame, (event.resize.w)/2, event.resize.h, (event.resize.w)/2, 0);
                window = SDL_SetVideoMode(event.resize.w, event.resize.h, 32, SDL_HWSURFACE|SDL_RESIZABLE);
                drawFrame(mainFrame);
                drawFrame(secFrame);

                break;
        }
    }

    if(focusedFrame == mainFrame) {

        int tmpMouseX;
        int tmpMouseY;
        SDL_GetMouseState(&tmpMouseX, &tmpMouseY);

        if(tmpMouseX != mouseX || tmpMouseY != mouseY) {
            mouseX = tmpMouseX;
            mouseY = tmpMouseY;
            secFrame->additionnalData[0] = mainFrame->topLeftX + (VAR_TYPE)mouseX/(mainFrame->widthPixels) * mainFrame->width;
            secFrame->additionnalData[1] = mainFrame->topLeftY - (VAR_TYPE)mouseY/(mainFrame->heightPixels) * mainFrame->height;
            drawFrame(secFrame);
        }
    }

    return 1;
}

void openWindow() {

    SDL_Init(SDL_INIT_VIDEO);
    window = SDL_SetVideoMode(DEFAULT_WIDTH, DEFAULT_HEIGHT, 32, SDL_HWSURFACE|SDL_RESIZABLE);
    SDL_WM_SetCaption("Left/Right click to zoom/unzoom, Space to toggle focus", NULL);

    mainFrame = initFrame(DEFAULT_WIDTH/2, DEFAULT_HEIGHT, 0, 0, 0);
    secFrame = initFrame(DEFAULT_WIDTH/2, DEFAULT_HEIGHT, DEFAULT_WIDTH/2, 0, 2);
    focusedFrame = mainFrame;

    drawFrame(mainFrame);
}

void closeWindow() {
    destroyFrame(mainFrame);
    destroyFrame(secFrame);
    SDL_Quit();
}
