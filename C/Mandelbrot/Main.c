#include "FrameManager.h"

int main() {

    openFrame();

    while(updateEvents())
        recalculateFrame();    

    closeFrame();    

    return 0;
}
