#include <iostream>
#include <math.h>

void add(int n, float *x, float *y)
{
    for(int j = 0; j < n; j++)
        for(int i = 0; i < 1000; i++)
            x[j] += y[j];
}           

int main(void)
{
    int N = 1<<20;

    float *x = (float*) malloc(N*sizeof(float));
    float *y = (float*) malloc(N*sizeof(float));

    for (int i = 0; i < N; i++) {
        x[i] = 1.0f;
        y[i] = 2.0f;
    }

    // Run kernel on 1M elements on the GPU
    add(N, x, y);

    return 0;
}
