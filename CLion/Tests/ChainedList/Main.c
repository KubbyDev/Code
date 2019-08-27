#include <stdio.h>
#include "ChainedList.h"

int main() {

    ChainedList* list = chndlstEmpty();
    printf("%d\n", chndlstLength(list));
    chndlstDisplay(list);
    chndlstAddValue(list, 18);
    //printf("%d", chndlstLength(list));
    chndlstDisplay(list);

    return 0;
}

