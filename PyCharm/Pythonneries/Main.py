# coding: utf-8

# Reminder on imports, random and seed
import random

# # Algo Toolbox and "*Pythoneries*"
# ## Lists

# #### Pythonery
# This can be simplified with *Python list slices*:
# - `L[a:b]` is the sub list of `L` with elements from positions `a` to `b` (`b` excluded)
# - `L[:a]` is the list `L[0:a]`
# - `L[a:]` is the list `L[a:len(n)]`
# - `L[-1]` is `L[len(L)-1]`


def splitlist(l):
    return l[2:]


L = [1, 2, 3, 4, 5, 6, 7, 8, 9]
splitlist(L)


# ### **<font color="blue">Exercise: Binary Search</font>**
# Write two functions to search an element in a sorted (in increasing order) list:
# - `binarysearch(L, x, left, right)` returns the position where `x` is or might be in `L[left, right[`, with `L` sorted in increasing order.


def binarysearch(L, x, left, right):
    """Binary Search

    Args:
        L: List to search in
        x: Value to search
        left, right: Search intervalle in L [left, right[

    Returns:
        The position where x is or might be
    """
    pass


L = [-3, 0, 5, 8, 13, 24, 32, 37, 42]
binarysearch(L, 0, 0, len(L))


# - `listSearch(L, x)` returns `-1` if `x` in not in the list `L`, its position otherwise


def listsearch(L, x):
    for i in range(len(L)):
        if L[i] == x:
            return i
    return -1


# #### Pythonery
# Use *Python "ternary" operator*:
# `[on_true] if [expression] else [on_false]`


def listsearch2(x, L):
    i = 0
    n = len(L)
    while i < n and L[i] != x:
        i += 1
    return -1 if i == n else i


# ### **<font color="blue">Exercise: Build List &rarr; Build Matrix</font>**
# Write the function `buildlist(nb, val=None, alea=None)` that builds a new list of length `nb`:
# - `buildlist(nb)` returns  `[None, None, ...]`
# - `buildlist(nb, val)` returns `[val, val, ...]`
# - `buildlist(nb, alea = (a, b))` returns a list of `nb` random values in `[a, b[`

# Note: `if a:` is `False` when `a` is `0, None, [], "" ...`


def buildlist(nb, val=None, alea=None):
    ret = []
    for i in range(nb):
        number = 0
        if val:
            number = val
        if alea:
            number = random.randint(alea[0], alea[1])
        ret.append(number)
    return ret


# #### Pythonery
# *Python gives a short way to build list*: `[val] * nb`


def buildlist(nb, val=None, alea=None):
    if alea:
        L = []
        for i in range(nb):
            L.append(random.randint(alea[0], alea[1]))
        return L
    return [val if val is not None else 0] * nb


buildlist(5), buildlist(5, 0), buildlist(5, alea=(0, 10))

# #### <font color="red">WARNING:</font>
# when you want to build a list of lists


L = buildlist(9, [])


L


L[0].append(1)


L


# Write again `buildlist` to avoid the problem


def buildlist(nb, val=None, alea=None):
    if alea:
        L = []
        for i in range(nb):
            L.append(random.randint(alea[0], alea[1]))
        return L
    return [val[:] if val is not None else 0] * nb


# Use `buildlist` to build a (5*5) matrix filled with `None`, then change a value


M = buildlist(4, buildlist(5, None))


M


M[0][0] = 5


M


# Write a function `buildmatrix(line, col, val=None)` that builds a `(line*col)` matrix filled with `val`.


def buildmatrix(line, col, val=None):
    M = []
    l = [val]*col
    for i in range(line):
        M.append(l[:])
    return M


M = buildmatrix(4, 5)
M[0][0] = 3
M
