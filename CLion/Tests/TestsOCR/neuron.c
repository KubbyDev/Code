#include "neuron.h"

struct Neuron {
    float* weights;
    float biais;
    int nbWeight;
};

float simulate(const Neuron* n, const float* inputs) {
    float sum = (*n).biais; //Adds the biais to the weighted sum
    //Sums all the inputs multiplied by the weights
    for(int i = 0; i < (*n).nbWeight; i++)
        sum += (*n).weights[i] * inputs[i];
    return activation(sum);
}

float activation(float x) {
    if(x > 1) return 1;
    if(x < 0) return 0;
    return x;
}