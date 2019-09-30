#ifndef TESTS_NEURON_H
#define TESTS_NEURON_H

typedef struct Neuron Neuron;
float simulate(const Neuron* n, const float* inputs);
float activation(float x);

#endif