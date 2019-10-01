#ifndef TESTS_NETWORK_H
#define TESTS_NETWORK_H

#include "neuron.h" //TODO: Voir si c'est vraiment necessaire

typedef struct Network Network;
struct Network {
    //All the neurons of the network. The layers are stored one after the other
    Neuron** neurons;
    //The index of the first neuron of each layer.
    //firstNeuronIndices[1] is the index of the first neuron of the 2nd layer (the one just after the input layer)
    //firstNeuronIndices[1] will always give 0 because the input layer doesn't contain any neuron.
    //firstNeuronIndices[0] will give -(the number of inputs)
    int* firstNeuronIndices;
    //The length of each layer. layerLengths[0] gives the number of inputs.
    int* layerLengths;
    //The number of layers (inputs included. ex: inputs - hidden - hidden - outputs => 4)
    int nbLayers;
};

Network* newNetwork(int* layerLengths, int nbLayers);
float* getNetworkAnswer(Network* network, float* inputs);
int getLayerLength(Network* network, int layerIndex);

#endif