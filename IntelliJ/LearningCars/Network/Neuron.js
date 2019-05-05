class Neuron {

    connections = [];
    biais = 0;
    activation = 0;

    simulate() {

        let answer = this.biais;

        for(let connection of this.connections)
            answer += connection.simulate();

        answer = this.activation(answer);
        this.activation = answer;
        return answer;
    }

    activation(x) {
        return Math.min(1, Math.max(0, x));
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
}