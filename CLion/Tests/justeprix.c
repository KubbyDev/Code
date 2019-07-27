#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>

int rnd(long min, long max) {
    srand(time(NULL));
    return (rand() % (max - min + 1)) + min;
}

int ain() {

    long toFind = rnd(0,50000);
    long guess = -1;
    int tries = 0;

    while(guess != toFind) {

        printf("Guess: ");
        scanf("%ld", &guess);

        if(guess != toFind)
            printf("C'est %s!\n", toFind > guess ? "plus" : "moins");

        tries++;
    }

    printf("Gagné, le nombre etait %ld", toFind);

    return 0;
}
