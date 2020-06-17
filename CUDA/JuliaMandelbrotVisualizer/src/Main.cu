#include <stdlib.h>
#include <stdio.h>
#include <time.h>

#include "WindowManager.h"

int main() {

    openWindow();

    // Initialises the fps counter
    unsigned long frames = 0;
    time_t seconds = time(NULL);

    while (updateWindow()) { // Returns 0 if the user closed the program
        
        frames++;
        
        // Each second, displays the number of frames calculated during that second
        if (seconds != time(NULL)) {
            printf("Fps: %lu\n", frames);
            frames = 0;
        }
        seconds = time(NULL);
    }

    closeWindow();

    return 0;
}
