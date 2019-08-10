#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>

void pointers(int *pSeconds, int *pMinutes, int *pHours) {

    *pMinutes = *pSeconds/60;
    *pHours = *pMinutes/60;
    *pMinutes %= 60;
    *pSeconds = *pSeconds % 60;
}

void test() {

    int seconds = 3900;
    int minutes;
    int hours;
    pointers(&seconds, &minutes, &hours);

    printf("Hours: %d, Minutes: %d, Seconds: %d", hours, minutes, seconds);

}

void memoryAllocation() {

    int* memoireAllouee = malloc(sizeof(int)); // Allocation de la mémoire
    if (memoireAllouee == NULL)
        exit(0);

    // Utilisation de la mémoire
    printf("Quel age avez-vous ? ");
    scanf("%d", memoireAllouee);
    printf("Vous avez %d ans\n", *memoireAllouee);

    free(memoireAllouee); // Libération de mémoire
}

int main() {

    memoryAllocation();

    return 0;
}