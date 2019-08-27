#include <stdlib.h>
#include <stdio.h>
#include "ChainedList.h"

struct ChainedList {
    ChainedListElement* first;
};

struct ChainedListElement {
    int value;
    ChainedListElement* next;
};

ChainedList* chndlstEmpty() {
    ChainedList* list = malloc(sizeof(*list));
    return list;
}

void chndlstAddValue(ChainedList* list, int value) {

    //Pointer to the pointer to the next element
    ChainedListElement** current = &(*list).first;

    //While the pointer to the next element is not NULL
    while((*current) != NULL)
        //Sets current to a pointer to the pointer to the next element of current
        current = &(**current).next;

    ChainedListElement newElement = {value, NULL};
    *current = &newElement;
}

void chndlstInsertValue(ChainedListElement* afterElement, int value) {
    ChainedListElement newElement = {value, (*afterElement).next};
    (*afterElement).next = &newElement;
}

void chndlstRemoveValue(ChainedListElement* elementBefore) {

    if((*elementBefore).next == NULL)
        return;

    (*elementBefore).next = (*(*elementBefore).next).next;
}

int chndlstLength(ChainedList* list) {

    int length = 0;
    ChainedListElement* current = (*list).first;

    while(current != NULL) {
        current = (*current).next;
        length++;
    }

    return length;
}

void chndlstDisplay(ChainedList* list) {

    ChainedListElement* current = (*list).first;

    while(current != NULL) {
        printf("%d -> ", (*current).value);
        current = (*current).next;
    }

    printf("NULL\n");
}