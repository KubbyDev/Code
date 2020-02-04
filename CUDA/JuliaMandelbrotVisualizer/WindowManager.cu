#include "WindowManager.h"
#include "FrameManager.h"
#include "Frame.h"
#include "Config.h"
#include "Tools.h"

#include "SDL.h"

SDL_Surface* window;
Frame* mainFrame; // Displays the Mandelbrot set
Frame* secFrame; // Displays a Julia set
Frame* focusedFrame; // The frame that is selected (has the focus)

int mouseX;
int mouseY;

void drawFrame(Frame* frame) {

    Color* pixels = updateFrame(frame);

    for(int y = 0; y < frame->heightPixels; y++) {
        for(int x = 0; x < frame->widthPixels; x++) {
            Color* pixel = pixels + y*(frame->widthPixels) + x;
            Uint32 pixelValue = SDL_MapRGB(window->format, pixel->r,pixel->g, pixel->b);
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

                int w = event.resize.w;
                int h = event.resize.h;

                mainFrame->widthPixels = w/2;
                mainFrame->heightPixels = h;

                secFrame->widthPixels = w/2;
                secFrame->heightPixels = h;

                secFrame->xPixels = w/2;

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
            secFrame->additionnalData[0] = mainFrame->topLeftX + (float)mouseX/(mainFrame->widthPixels) * mainFrame->width;
            secFrame->additionnalData[1] = mainFrame->topLeftY - (float)mouseY/(mainFrame->heightPixels) * mainFrame->height;
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
