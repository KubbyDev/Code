#include <stdio.h>
#include <stdlib.h>

int main1() {

    printf("char : %lu octets\n", sizeof(char));
    printf("int : %lu octets\n", sizeof(int));
    printf("long : %lu octets\n", sizeof(long));
    printf("double : %lu octets\n", sizeof(double));

    int a = 2000000000;
    long b = 20000000000;

    printf("a: %d\n", a);
    printf("b: %lu", b);

}

void testFunc() {

}