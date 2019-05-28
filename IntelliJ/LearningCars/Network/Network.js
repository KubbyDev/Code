class Network {

    layers = [];  //Contient des listes de neurones (attention: les inputs ne sont pas dedans)
    inputs = [];  //Contient les objets inputs (neurones de la premiere couche (qui n'existe donc pas vraiment))

    //Renvoie les activations de la derniere couche
    simulate(inputs) {

        //Simulation de la premiere couche (couche virtuelle des inputs)
        for(let i = 0; i < inputs.length; i++)
            this.inputs[i].activation = inputs[i];

        let answer = [];
        let lastLayerId = this.layers.length-1;

        //Calcul des activations des neurones intermediaires
        for(let i = 0; i < lastLayerId; i++)
            for(let neuron of this.layers[i])
                neuron.simulate();

        //Calcul des outputs (derniere couche)
        for(let i = 0; i < this.layers[lastLayerId].length; i++)
            answer[i] = this.layers[lastLayerId][i].simulate();

        return answer;
    }

    static random(inputNumber, outputNumber, neuronsPerHiddenLayer, hiddenLayers) {

        if(hiddenLayers === undefined)
            hiddenLayers = 1;

        if(neuronsPerHiddenLayer === undefined)
            hiddenLayers = 0;

        let network = new Network();
        network.layers = new Array(hiddenLayers+1);

        //Premiere couche (couche virtuelle des inputs)
        network.inputs = new Array(inputNumber);
        for(let i = 0; i < inputNumber; i++)
            network.inputs[i] = new Input(i);

        //Couches cachees
        for(let layerID = 1; layerID <= hiddenLayers; layerID++) {
            network.layers[layerID-1] = new Array(neuronsPerHiddenLayer);
            for(let neuronID = 0; neuronID < neuronsPerHiddenLayer; neuronID++)
                network.layers[layerID-1][neuronID] = Neuron.random(
                    layerID === 1 ? network.inputs : network.layers[layerID-2], layerID, neuronID);
        }

        //Derniere couche (couche des outputs)
        network.layers[hiddenLayers] = new Array(outputNumber);
        for(let neuronID = 0; neuronID < outputNumber; neuronID++)
            network.layers[hiddenLayers][neuronID] = Neuron.random(
                hiddenLayers === 0 ? network.inputs : network.layers[hiddenLayers-1], hiddenLayers+1, neuronID);

        return network;
    }

    copy() {

        let network = new Network();

        //Premiere couche (couche virtuelle des inputs)
        network.inputs = new Array(this.inputs.length);
        for(let i = 0; i < this.inputs.length; i++)
            network.inputs[i] = new Input(i);

        //Pour chaque couche
        network.layers = new Array(this.layers.length);
        for(let i = 0; i < this.layers.length; i++) {

            //Pour chaque neurone de la couche
            let neuronsNumber = this.layers[i].length;
            network.layers[i] = new Array(neuronsNumber);
            for(let j = 0; j < neuronsNumber; j++)
                network.layers[i][j] = this.layers[i][j].copy(network);
        }

        return network;
    }

    mutate() {

        for(let layer of this.layers)
            for(let neuron of layer)
                neuron.mutate();
    }

    getNeuron(layer, id) {

        if(layer === 0)
            return this.inputs[id];

        return this.layers[layer-1][id];
    }
}