#include "BooleanMatrix.h"

#include <stdio.h>

int main() {

    BooleanMatrix* matrix = bm_new(20, 20);
    bm_fill(matrix, 1);

    bm_set(matrix, 18, 18, 0);

    for(unsigned int y = 0; y < bm_sizeY(matrix); y++) {
        for(unsigned int x = 0; x < bm_sizeX(matrix); x++)
            printf("%i ", bm_get(matrix, x, y));
        printf("\n");
    }

    return 0;
}
