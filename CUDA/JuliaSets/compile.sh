# This program uses the SDL 1.2.15 because I'm an idiot and I thought I
# installed the 2.0 but no
nvcc *.cu `sdl-config --cflags --libs`
