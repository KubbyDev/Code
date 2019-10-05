# This program uses the SDL 1.2.15 because I'm an idiot and I thought I
# installed the 2.0 but no
gcc -Wall -Wextra *.c `sdl-config --cflags --libs`
