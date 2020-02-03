#ifndef CONFIG_H
#define CONFIG_H

#define DEFAULT_WIDTH 1280
#define DEFAULT_HEIGHT 720
#define ZOOM_STRENGTH 1.2

// (Window width * Window height) must be divisible
// by (NB_BLOCKS*NB_THREADS)
#define NB_BLOCKS 5
#define NB_THREADS 384

#define ITERATIONS_THRESHOLD 1000

#endif //CONFIG_H
