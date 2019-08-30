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

//Renvoie une liste vide (O(1))
ChainedList* chndlstEmpty() {
    ChainedList* list = malloc(sizeof(ChainedListElement*));
    return list;
}

//Cree un element (pas forcement rattache a une liste) (O(1))
ChainedListElement* chndlstCreateElement(int value, ChainedListElement* next) {
    ChainedListElement* newElement = malloc(sizeof(int) + sizeof(ChainedListElement*));
    (*newElement).value = value;
    (*newElement).next = next;
    return newElement;
}

//Renvoie le premier element de la liste (O(1))
ChainedListElement* getFirst(ChainedList* list) {
    return (*list).first;
}

//Renvoie l'element suivant un element (O(1))
ChainedListElement* getNext(ChainedListElement* element) {
    return (*element).next;
}

//Ajoute un element de valeur value a la fin de la liste (O(n))
void chndlstAddValueAtEnd(ChainedList* list, int value) {

    //Pointer to the pointer to the next element
    ChainedListElement** current = &(*list).first;

    //While the pointer to the next element is not NULL
    while((*current) != NULL)
        //Sets current to a pointer to the pointer to the next element of current
        current = &(**current).next;

    *current = chndlstCreateElement(value, NULL);
}

//Ajoute un element de valeur value au debut de la liste (O(1))
void chndlstAddValueAtBeginning(ChainedList* list, int value) {
    (*list).first = chndlstCreateElement(value, (*list).first);
}

//Insere un element de valeur value apres un element donne (O(1))
void chndlstInsertValue(ChainedListElement* afterElement, int value) {
    (*afterElement).next = chndlstCreateElement(value, (*afterElement).next);
}

//Supprime l'element suivant elementBefore (O(1))
void chndlstRemoveValue(ChainedListElement* elementBefore) {

    if((*elementBefore).next == NULL)
        return;

    (*elementBefore).next = (*(*elementBefore).next).next;
}

//Supprime le premier element de la liste (O(1))
void chndlstRemoveFirst(ChainedList* list) {

    if((*list).first == NULL)
        return;

    (*list).first = (*(*list).first).next;
}

//Renvoie la longueur de la liste (O(n))
int chndlstLength(ChainedList* list) {

    int length = 0;
    ChainedListElement* current = (*list).first;

    while(current != NULL) {
        current = (*current).next;
        length++;
    }

    return length;
}

//Affiche la liste (O(n))
void chndlstDisplay(ChainedList* list) {

    ChainedListElement* current = (*list).first;

    while(current != NULL) {
        int val = (*current).value;
        printf("%d -> ", val);
        current = (*current).next;
    }

    printf("NULL\n");
}