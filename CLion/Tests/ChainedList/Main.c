#include <stdio.h>
#include "ChainedList.h"

int main() {

    ChainedList* list = chndlstEmpty();
    printf("%d\n", chndlstLength(list));
    chndlstDisplay(list);
    chndlstAddValueAtEnd(list, 18);
    printf("%d\n", chndlstLength(list));
    chndlstDisplay(list);
    chndlstAddValueAtEnd(list, 19);
    printf("%d\n", chndlstLength(list));
    chndlstDisplay(list);
    chndlstRemoveValue(getFirst(list));
    printf("%d\n", chndlstLength(list));
    chndlstDisplay(list);
    chndlstAddValueAtBeginning(list, 80);
    printf("%d\n", chndlstLength(list));
    chndlstDisplay(list);
    chndlstRemoveFirst(list);
    printf("%d\n", chndlstLength(list));
    chndlstDisplay(list);

    return 0;
}

