#include "WindowManager.h"

int main() {

    openWindow();

    do {
        updateWindow();
    } while(updateEvents()); // Returns 0 if the user closed the program

    closeWindow();

    return 0;
}
