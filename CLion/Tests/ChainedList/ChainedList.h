#ifndef TESTS_CHAINEDLIST_H
#define TESTS_CHAINEDLIST_H

typedef struct ChainedList ChainedList;
typedef struct ChainedListElement ChainedListElement;

ChainedList* chndlstEmpty();
void chndlstInsertValue(ChainedListElement* afterElement, int value);
void chndlstAddValue(ChainedList* list, int value);
void chndlstRemoveValue(ChainedListElement* elementBefore);
int chndlstLength(ChainedList* list);
void chndlstDisplay(ChainedList* list);

#endif //TESTS_CHAINEDLIST_H