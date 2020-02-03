#ifndef CONFIG_H
#define CONFIG_H

#define DEFAULT_WIDTH 1600
#define DEFAULT_HEIGHT 800
#define ZOOM_STRENGTH 1.2

// (Window width * Window height) must be divisible
// by (NB_BLOCKS*NB_THREADS)
#define NB_BLOCKS 10
#define NB_THREADS 500

#define MANDELBROT_ITERATIONS_THRESHOLD 1000
#define JULIA_ITERATIONS_THRESHOLD 50

#endif //CONFIG_H
