#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "UCharMatrix.hh"
#include "astar.hh"
#include "oldfindpath.hh"

// Can be findPath_Old or findPath_Astar
#define FINDPATH findPath_Astar

#define SIZE 24
#define HOR_WALLS 5
#define VER_WALLS 5
#define WALL_LEN_MIN 5
#define WALL_LEN_MAX 15

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
            unsigned char val = getMatrixValue(map, x, y);
            // Changes the color to green for the target, yellow for
            // the position and red for the walls
            if(x == posx && y == posy) printf("\033[42m");
            if(x == tarx && y == tary) printf("\033[43m");
            if(val == 255) printf("\033[31;1m");
            // Prints the value of the map at this position
            if(val == 0) printf(" . ");
            else if(val == 255) printf(" W ");
            else if(val < 10) printf(" %hhu ", val);
            else if(val < 100) printf(" %hhu", val);
            else printf("%hhu", val);
            // Resets the color
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
        clock_t begin = clock();
        FINDPATH(map, posx, posy, tarx, tary);
        clock_t end = clock();

        // Displays everything
        printMap(posx, posy, tarx, tary);
        printf("Calculation time: %lf ms", (double)(end-begin)/(CLOCKS_PER_SEC/1000));

        // Waits for the user to press a key
        char c = getchar();
        if(c == 'q') break;
    }

    return EXIT_SUCCESS;
}
