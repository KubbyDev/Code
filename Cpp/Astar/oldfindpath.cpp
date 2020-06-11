#include "oldfindpath.hh"
#include "UCharMatrix.hh"

#define LOWRES_SIZE 24
#define UNUSED(x) (void)(x)

void findPath_Old(UCharMatrix* map, int posx, int posy, int tarx, int tary) {

    UNUSED(posx);
    UNUSED(posy);

    // Resets the pixels that are not walls in map (sets them to 254)
    for(int y = 0; y < LOWRES_SIZE; y++)
        for(int x = 0; x < LOWRES_SIZE; x++)
            if(getMatrixValue(map, x, y) != 255)
                setMatrixValue(map, x, y, 254);

    // Sets the targets distance to the target to 0
    setMatrixValue(map, tarx, tary, 0);

    // List of all movements that are considered possible for the pathfinding
    char offsets[] = {1,0, 0,1, -1,0, 0,-1, 1,1, -1,1, -1,-1, 1,-1};

    // List of numbers for the update order
    // There are 4 update orders (top left to bottom right, top right to bottom left, bl to tr, br to tl)
    // For each order, there are startX, startY, stepX, stepY, endX, endY values
    char orders[] = {
        0, 0, 1, 1, LOWRES_SIZE, LOWRES_SIZE,
        LOWRES_SIZE-1, 0, -1, 1, -1, LOWRES_SIZE,
        0, LOWRES_SIZE-1, 1, -1, LOWRES_SIZE, -1,
        LOWRES_SIZE-1, LOWRES_SIZE-1, -1, -1, -1, -1
    };
    int selected = 0;

    // For all pixels in the map, calculates the distance to the target
    // Continues the calculation until there are pixels that have not been calculated
    int changed = 1;
    while(changed) {

        changed = 0;
        // The order of the update changes at each update (see the order array above)
        for(int y = orders[selected*6+1]; y != orders[selected*6+5]; y += orders[selected*6+3]) {
            for(int x = orders[selected*6+0]; x != orders[selected*6+4]; x += orders[selected*6+2]) {

                unsigned char current = getMatrixValue(map, x, y);
                if(current == 255)
                    continue;
                // For all pixels around this pixel
                for(int i = 0; i < 8; i++) {
                    int newX = x + offsets[2*i];
                    int newY = y + offsets[2*i +1];
                    if(inMatrixBounds(map, newX, newY) && getMatrixValue(map, newX, newY)+1 < current) {
                        setMatrixValue(map, x, y, getMatrixValue(map, newX, newY)+1);
                        changed = 1;
                    }
                }
            }
        }

        // Changes the order of the update (look at the order array above)
        selected = (selected+1) %4;
    }
}
