#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "UCharMatrix.h"

#define SIZE 24
#define HOR_WALLS 3
#define VER_WALLS 3
#define WALL_LEN_MIN 3
#define WALL_LEN_MAX 8

UCharMatrix* map;

void placeWalls() {

    int x, y, l;

    // Places horizontal walls
    for(int j = 0; j < HOR_WALLS; j++) {
        l = (rand()%(WALL_LEN_MAX-WALL_LEN_MIN+1)) + WALL_LEN_MIN;
        x = rand()%(SIZE-l);
        y = rand()%SIZE;
        for(int i = 0; i < l; i++)
            setMatrixValue(map, x+i, y, 255);
    }

    // Places vertical walls
    for(int j = 0; j < VER_WALLS; j++) {
        l = (rand()%(WALL_LEN_MAX-WALL_LEN_MIN+1)) + WALL_LEN_MIN;
        x = rand()%SIZE;
        y = rand()%(SIZE-l);
        for(int i = 0; i < l; i++)
            setMatrixValue(map, x, y+i, 255);
    }
}

void printMap(int posx, int posy, int tarx, int tary) {

    for(int y = 0; y < SIZE; y++) {
        for(int x = 0; x < SIZE; x++) {
            // Changes the color to green for the target and yellow for the pos
            if(x == posx && y == posy) printf("\033[42m");
            if(x == tarx && y == tary) printf("\033[43m");
            // Prints the value of the map at this position
            unsigned char val = getMatrixValue(map, x, y);
            if(val == 0) printf(" . ");
            else if(val == 255) printf(" W ");
            else if(val < 10) printf(" %hhu ", val);
            else if(val < 100) printf(" %hhu", val);
            else printf("%hhu", val);
            // Resets the color
            if((x == posx && y == posy) || (x == tarx && y == tary))
                printf("\033[00m");
        }
        printf("\n");
    }
}

void randomizePos(int* x, int* y) {
    do {
        *x = rand()%SIZE;
        *y = rand()%SIZE;
    } while(getMatrixValue(map, *x, *y) == 255); // 255 == wall
}

int main() {

    // Initialises the random number generator
    srand(time(NULL));

    // Initialises the map
    map = newMatrix(SIZE, SIZE);

    while(1) {

        printf("------------------------------------------------------------------------\n");

        // Generates new walls
        clearMatrix(map);
        placeWalls();

        // Randomizes the position and the target
        int posx, posy, tarx, tary;
        randomizePos(&posx, &posy);
        randomizePos(&tarx, &tary);

        // Finds a path


        // Displays everything
        printMap(posx, posy, tarx, tary);

        // Waits for the user to press a key
        char c = getchar();
        if(c == 'q') break;
    }

    return EXIT_SUCCESS;
}
