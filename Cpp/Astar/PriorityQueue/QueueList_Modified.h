/*
Modified version of QueueList.h to allow for private variable access to a child class
Edits by Collin Dietz
Email: c4dietz@gmail.com
Original header is below

edits:
moved head, tail, and size to protected
*/

/*
 *  QueueList.h
 *
 *  Library implementing a generic, dynamic queue (linked list version).
 *
 *  ---
 *
 *  Copyright (C) 2010  Efstathios Chatzikyriakidis (contact@efxa.org)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *  ---
 *
 *  Version 1.0
 *
 *    2010-09-28  Efstathios Chatzikyriakidis  <contact@efxa.org>
 *
 *      - added exit(), blink(): error reporting and handling methods.
 *
 *    2010-09-25  Alexander Brevig  <alexanderbrevig@gmail.com>
 *
 *      - added setPrinter(): indirectly reference a Serial object.
 *
 *    2010-09-20  Efstathios Chatzikyriakidis  <contact@efxa.org>
 *
 *      - initial release of the library.
 *
 *  ---
 *
 *  For the latest version see: http://www.arduino.cc/
 */

// header defining the interface of the source.
#ifndef _QUEUELIST_M_H
#define _QUEUELIST_M_H

#include <iostream>

// the definition of the queue class.
template<typename T>
class QueueList {
  public:
    // init the queue (constructor).
    QueueList ();

    // clear the queue (destructor).
    ~QueueList ();

    // push an item to the queue.
    void push (const T i);

    // pop an item from the queue.
    T pop ();

    // get an item from the queue.
    T peek () const;

    // check if the queue is empty.
    bool isEmpty () const;

    // get the number of items in the queue.
    int count () const;

    // the structure of each node in the list.
    typedef struct node {
      T item;      // the item in the node.
      node * next; // the next node in the list.
    } node;

    typedef node * link; // synonym for pointer to a node.

  private:
    // exit report method in case of error.
    void exit (const char * m) const;

    // led blinking method in case of error.
    void blink () const;

    // the pin number of the on-board led.
    static const int ledPin = 13;

   protected:
    link head;       // the head of the list.
    link tail;       // the tail of the list.
    int size;        // the size of the queue.

};

// init the queue (constructor).
template<typename T>
QueueList<T>::QueueList () {
  size = 0;       // set the size of queue to zero.
  head = NULL;    // set the head of the list to point nowhere.
  tail = NULL;    // set the tail of the list to point nowhere.
}

// clear the queue (destructor).
template<typename T>
QueueList<T>::~QueueList () {
  // deallocate memory space of each node in the list.
  for (link t = head; t != NULL; head = t) {
    t = head->next; delete head;
  }

  size = 0;       // set the size of queue to zero.
  tail = NULL;    // set the tail of the list to point nowhere.
}

// push an item to the queue.
template<typename T>
void QueueList<T>::push (const T i) {
  // create a temporary pointer to tail.
  link t = tail;

  // create a new node for the tail.
  tail = (link) new node;

  // if there is a memory allocation error.
  if (tail == NULL)
    exit ("QUEUE: insufficient memory to create a new node.");

  // set the next of the new node.
  tail->next = NULL;

  // store the item to the new node.
  tail->item = i;

  // check if the queue is empty.
  if (isEmpty ())
    // make the new node the head of the list.
    head = tail;
  else
    // make the new node the tail of the list.
    t->next = tail;

  // increase the items.
  size++;
}

// pop an item from the queue.
template<typename T>
T QueueList<T>::pop () {
  // check if the queue is empty.
  if (isEmpty ())
    exit ("QUEUE: can't pop item from queue: queue is empty.");

  // get the item of the head node.
  T item = head->item;

  // remove only the head node.
  link t = head->next; delete head; head = t;

  // decrease the items.
  size--;

  // return the item.
  return item;
}

// get an item from the queue.
template<typename T>
T QueueList<T>::peek () const {
  // check if the queue is empty.
  if (isEmpty ())
    exit ("QUEUE: can't peek item from queue: queue is empty.");

  // return the item of the head node.
  return head->item;
}

// check if the queue is empty.
template<typename T>
bool QueueList<T>::isEmpty () const {
  return head == NULL;
}

// get the number of items in the queue.
template<typename T>
int QueueList<T>::count () const {
  return size;
}

// exit report method in case of error.
template<typename T>
void QueueList<T>::exit (const char * m) const {
    std::cout << "ERROR";
    printf("%s", m);
}

#endif // _QUEUELIST_M_H
