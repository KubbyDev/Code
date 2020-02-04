#ifndef CONFIG_H
#define CONFIG_H

// Default width and height of the visualizer window
#define DEFAULT_WIDTH 1800
#define DEFAULT_HEIGHT 900

// Strength of the zoom
#define ZOOM_STRENGTH 1.2

// GPU configuration for the frames calculations
// (Window width * Window height) must be divisible
// by (NB_BLOCKS*NB_THREADS)
#define NB_BLOCKS 10
#define NB_THREADS 500

// The iteration threshold for the frames
// (left one is Mandelbrot, right one is Julia)
#define MANDELBROT_ITERATIONS_THRESHOLD 200
#define JULIA_ITERATIONS_THRESHOLD 200

// Coloration function for the pixels (choose one from ColorationFunctions.cu)
#define COLORATION_FUNCTION coloration_green

#endif //CONFIG_H
