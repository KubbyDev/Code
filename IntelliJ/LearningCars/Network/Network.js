class Network {

    layers = [];  //Contient des listes de neurones
    inputs = [];  //Contient les inputs a chaque fois que le reseau est simule

    //Renvoie les activations de la derniere couche
    simulate(inputs) {

        this.inputs = new Array(inputs.length);
        for(let i = 0; i < inputs.length; i++)
            this.inputs[i] = new Input(inputs[i]);

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
}