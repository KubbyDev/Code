#include "WindowManager.h"
#include "FrameManager.h"
#include "Frame.h"
#include "Config.h"
#include "Tools.h"

#include "SDL.h"

SDL_Surface* window;
Frame* mainFrame;
Frame* secFrame;

// Updates the display
void updateWindow() {

    unsigned char* pixels = updateFrame(mainFrame);

    for(int y = 0; y < mainFrame->heightPixels; y++)
        for(int x = 0; x < mainFrame->widthPixels; x++)
            setPixel(window, x, y, SDL_MapRGB(
                        window->format,
                        0,
                        pixels[y*(mainFrame->widthPixels)+x],
                        0));

    //Updates the screen
    SDL_Flip(window);

    cudaFree(pixels);
}

void openWindow() {

    SDL_Init(SDL_INIT_VIDEO);
    window = SDL_SetVideoMode(DEFAULT_WIDTH, DEFAULT_HEIGHT, 32, SDL_HWSURFACE);
    SDL_WM_SetCaption("Mandelbrot: Left click to zoom, Right click to unzoom", NULL);

    mainFrame = initFrame(DEFAULT_WIDTH/2, DEFAULT_HEIGHT, 0);
    secFrame = initFrame(DEFAULT_WIDTH/2, DEFAULT_HEIGHT, 2);
}

void closeWindow() {
    destroyFrame(mainFrame);
    destroyFrame(secFrame);
    SDL_Quit();
}


//Returns 0 if the user closed the window and 1 otherwise
//Updates the screen values (zooming/resizing)
int updateEvents() {

    int res = -1;
    while(res == -1) {

        SDL_Event event;
        while (SDL_PollEvent(&event)) {

            switch(event.type) {

                case SDL_QUIT:
                    res = 0;
                    break;

                case SDL_MOUSEBUTTONDOWN:
                    int mainFocused = event.button.x <= mainFrame->widthPixels;
                    zoom(
                        mainFocused ? mainFrame : secFrame,
                        event.button.x - (mainFocused ? 0 : mainFrame->widthPixels),
                        event.button.y,
                        event.button.button ==
                            SDL_BUTTON_LEFT ? ZOOM_STRENGTH : 1.0/ZOOM_STRENGTH
                    );

                    res = 1;
                    break;
            }
        }

        int mouseX;
        int mouseY;
        SDL_GetMouseState(&mouseX, &mouseY);

        if(mouseX <= mainFrame->widthPixels) {

            unsigned char* pixels = updateFrame(secFrame);

            for(int y = 0; y < secFrame->heightPixels; y++)
                for(int x = 0; x < secFrame->widthPixels; x++)
                    setPixel(window, x+(mainFrame->widthPixels), y, SDL_MapRGB(
                                window->format,
                                0,
                                 pixels[y*(secFrame->widthPixels)+x],
                                0));

            secFrame->additionnalData[0] = mainFrame->topLeftX + (float)mouseX/(mainFrame->widthPixels) * mainFrame->width;
            secFrame->additionnalData[1] = mainFrame->topLeftY - (float)mouseY/(mainFrame->heightPixels) * mainFrame->height;

            SDL_Flip(window);

            cudaFree(pixels);
        }
    }

    return res;
}
