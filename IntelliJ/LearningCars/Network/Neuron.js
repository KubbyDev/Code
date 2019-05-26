class Neuron {

    static MUTATION_STRENGTH = 0.2;
    static MUTATION_PROBABILITY = 0.3;

    connections = [];
    biais = 0.5;
    activation = 0;

    layer = 0; //Le numero de la couche dans laquelle se trouve ce neurone
    id = 0;    //Le numero de ce neurone dans sa couche

    constructor(layer, id) {
        this.layer = layer;
        this.id = id;
    }

    simulate() {

        let answer = this.biais;

        for(let connection of this.connections)
            answer += connection.simulate();

        answer = Neuron.actFunction(answer);
        this.activation = answer;
        return answer;
    }

    static actFunction(x) {
        return Math.max(0, Math.min(1, x));
    }

    setPosition(layer, id) {
        this.layer = layer;
        this.id = id;
    }

    copy(newNetwork) {

        let neuron = new Neuron(this.layer, this.id);
        neuron.biais = this.biais;

        let connections = [];
        for(let connection of this.connections)
            connections.push(connection.copy(newNetwork));

        neuron.connections = connections;

        return neuron;
    }

    static random(previousLayer, layer, id) {

        let neuron = new Neuron(layer, id);
        for(let input of previousLayer)
            neuron.connections.push(new Connection(input)); //Le weight est set automatiquement par la Connection

        neuron.biais = Math.random()*2 -1;

        return neuron;
    }

    mutate() {

        for(let connection of this.connections)
            if(Math.random() < Connection.MUTATION_PROBABILITY)
                connection.mutate();

        if(Math.random() < Neuron.MUTATION_PROBABILITY)
            this.biais += (Math.random()-0.5)*2 * Neuron.MUTATION_STRENGTH;
    }

    /*
    addConnection(to) {

        //Regarde si ces neurones sont deja connectes
        for(let connection of this.connections)
            if(connection.target === to)
                return;

        //Si la connection n'existe pas encore, on l'ajoute
        let newConnection = new Connection(to);
        newConnection.weight = (Math.random()-0.5)*2 * Connection.MUTATION_STRENGTH;
        this.connections.push(newConnection);
    }
     */
}