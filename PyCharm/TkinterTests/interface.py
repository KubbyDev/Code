import tkinter as tk
from track import Track


root = tk.Tk()

a = Track(root, "T1", [78, 93, 56])
b = Track(root, "T2", [8, 9])

root.mainloop()
