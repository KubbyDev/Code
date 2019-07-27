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

int main() {

    int seconds = 3900;
    int minutes;
    int hours;
    pointers(&seconds, &minutes, &hours);

    printf("Hours: %d, Minutes: %d, Seconds: %d", hours, minutes, seconds);

    return 0;
}