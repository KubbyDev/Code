import tkinter as tk
from config import *


class Partition(tk.Canvas):
    # Creates a partition
    # notes are objects of this form: ( ??? )
    # minmax is a tuple (lowest note, highest note)
    def __init__(self, parent, notes, **kw):
        super().__init__(
            parent,
            bg=colors['background']
        )
        self.notes = notes
        self.dim = (partitions['width'], partitions['note_height']*self.notes_range())
        self.redraw()

    # Redraws the partition. Uses self.notes and self.dim
    def redraw(self):
        self.config(width=self.dim[0], height=self.dim[1])

    def notes_range(self):
        minnote = self.notes[0]
        maxnote = self.notes[0]
        for note in self.notes:
            if note > maxnote: maxnote = note
            if note < minnote: minnote = note
        return maxnote-minnote
