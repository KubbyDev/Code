#ifndef TESTS_CHAINEDLIST_H
#define TESTS_CHAINEDLIST_H

typedef struct ChainedList ChainedList;
typedef struct ChainedListElement ChainedListElement;

ChainedList* chndlstEmpty();
ChainedListElement* chndlstCreateElement(int value, ChainedListElement* next);
ChainedListElement* getFirst(ChainedList* list);
ChainedListElement* getNext(ChainedListElement* element);
void chndlstInsertValue(ChainedListElement* afterElement, int value);
void chndlstAddValueAtBeginning(ChainedList* list, int value);
void chndlstAddValueAtEnd(ChainedList* list, int value);
void chndlstRemoveValue(ChainedListElement* elementBefore);
void chndlstRemoveFirst(ChainedList* list);
int chndlstLength(ChainedList* list);
void chndlstDisplay(ChainedList* list);

#endif //TESTS_CHAINEDLIST_H