#include <stdlib.h>
#include "network.h"

/** Creates a new network with random weights and biaises
 * @param nbLayers - Number of layers
 * @param layerLengths - Number of neurons in each layer (including input and output layers) */
Network* newNetwork(int* layerLengths, int nbLayers) {



}

float* getNetworkAnswer(Network* network, float* inputs) {

    float** nextLayerInputs = &inputs; //Pointer to the input array of the next layer to simulate
    float* outputs = NULL; //Array of outputs of the last layer simulated

    //For each layer
    for(int layer = 1; layer <= (*network).nbLayers-1; layer++) {

        //Gets the length of the layer and allocates the memory for the output of the layer
        int layerLength = getLayerLength(network, layer);
        outputs = malloc(sizeof(float) *layerLength);

        //For each neuron of the layer
        for(int neuronIndex = 0; neuronIndex < layerLength; neuronIndex++) {

            //Simulates the neuron and stores its answer
            Neuron* neuron = (*network).neurons[(*network).firstNeuronIndices[layer]+neuronIndex];
            outputs[neuronIndex] = simulate(neuron, *nextLayerInputs);
        }

        //When the outputs are calculated, they become the inputs of the next layer
        nextLayerInputs = &outputs;
    }

    return outputs;
}

/** Returns the number of neurons in the nth layer (0 is the input layer) */
int getLayerLength(Network* network, int layerIndex) {
    return (*network).layerLengths[layerIndex];
}