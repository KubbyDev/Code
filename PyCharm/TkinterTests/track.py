import tkinter as tk
from config import *
from partition import Partition


class Track(tk.Frame):
    def __init__(self, parent, name, notes):
        super().__init__(
            parent,
            bg=colors['background'],
        )
        self.label = tk.Label(
            self,
            text=name,
            bg=colors['background'],
            fg=colors['text'],
        )
        self.notes = Partition(
            self,
            notes,
        )
        self.label.grid(row=0,column=0,padx=5)
        self.notes.grid(row=0,column=1)
        self.pack()