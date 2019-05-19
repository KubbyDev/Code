class Network {

    layers = [];  //Contient des listes de neurones
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

    static random(inputNumber, outputNumber) {

        let network = new Network();

        //Premiere couche (couche virtuelle des inputs)
        network.inputs = new Array(inputNumber);
        for(let i = 0; i < inputNumber; i++)
            network.inputs[i] = new Input();

        //Derniere couche (couche des outputs)
        network.layers = new Array(1);
        network.layers[0] = new Array(outputNumber);
        for(let i = 0; i < outputNumber; i++)
            network.layers[0][i] = Neuron.random(network.inputs);

        return network;
    }

    mutate() {

    }
}