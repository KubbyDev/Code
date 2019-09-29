#include "FrameManager.h"

int main() {

    openFrame();

    do {
        recalculateFrame();    
    } while(updateEvents());

    closeFrame();    

    return 0;
}
