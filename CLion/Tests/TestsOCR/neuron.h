#ifndef TESTS_NEURON_H
#define TESTS_NEURON_H

typedef struct Neuron Neuron;
struct Neuron {
    float* weights;
    float biais;
    int nbWeight;
};

Neuron* newNeuron(int nbWeights);
float simulate(Neuron* n, float* inputs);
float activation(float x);

#endif