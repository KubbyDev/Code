#include "WindowManager.h"

int main() {

    openWindow();
    while (updateWindow()); // Returns 0 if the user closed the program
    closeWindow();

    return 0;
}
