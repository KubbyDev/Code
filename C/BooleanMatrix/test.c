#include "BooleanMatrix.h"

#include <stdio.h>

int main() {

    BooleanMatrix* matrix = newMatrix(20, 20);
    fill(matrix, 1);

    setValue(matrix, 18, 18, 0);

    for(unsigned int y = 0; y < getSizeY(matrix); y++) {
        for(unsigned int x = 0; x < getSizeX(matrix); x++)
            printf("%i ", getValue(matrix, x, y));
        printf("\n");
    }

    return 0;
}
