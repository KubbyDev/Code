class Connection {

    static MUTATION_STRENGTH = 0.5;
    static NEW_NEURON_PROBABILITY = 0;

    weight = 0;
    target;
    
    constructor(target) {
        this.target = target;
        this.weight = Math.random()*2-1;
    }
    
    simulate() {
        return this.weight * this.target.activation;
    }   
    
    copy(newNetwork) {
        
        let connection = new Connection();
        connection.target = newNetwork.getNeuron(this.target.layer, this.target.id);
        connection.weight = this.weight;
        
        return connection;
    }

    mutate() {

        this.weight += (Math.random()-0.5)*2 * Connection.MUTATION_STRENGTH;

        if(Math.random() < Neuron.NEW_NEURON_PROBABILITY)
            this.insert_neuron();
    }

    insert_neuron() {

    }
}