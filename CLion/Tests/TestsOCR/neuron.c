#include <stdlib.h>
#include "neuron.h"

Neuron* newNeuron(int nbWeights) {

    //Allocates memory for the weights, the biais and the number of weights
    //(All the fields in the Neuron struct)
    Neuron* neuron = malloc(sizeof(float) *(nbWeights+1) + sizeof(int));

    //TODO generate random weights and biais

    return neuron;
}

float simulate(Neuron* n, float* inputs) {
    float sum = (*n).biais; //Adds the biais to the weighted sum
    //Sums all the inputs multiplied by the weights
    for(int i = 0; i < (*n).nbWeight; i++)
        sum += (*n).weights[i] * inputs[i];
    return activation(sum);
}

/** The activation function: x < 0 => 0; x > 1 => 1; else: x */
float activation(float x) {
    if(x > 1) return 1;
    if(x < 0) return 0;
    return x;
}