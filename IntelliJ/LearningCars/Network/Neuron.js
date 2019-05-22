class Neuron {

    static MUTATION_PROBABILITY = 0.2;

    connections = [];
    biais = 0.5;
    activation = 0;

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

    copy() {

        let connections = [];
        for(let connection of this.connections)
            connections.push(connection.copy());

        let neuron = new Neuron();
        neuron.connections = connections;
        neuron.biais = this.biais;

        return neuron;
    }

    static random(inputs) {

        let neuron = new Neuron();
        for(let input of inputs)
            neuron.connections.push(new Connection(input)); //Le weight est set automatiquement par la Connection

        return neuron;
    }

    mutate() {

        for(let connection of this.connections)
            if(Math.random() < Neuron.MUTATION_PROBABILITY)
                connection.mutate();
    }
}