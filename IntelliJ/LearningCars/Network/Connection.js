class Connection {

    static MUTATION_STRENGTH = 0.1;
    static NEW_NEURON_PROBABILITY = 0;

    weight = 0;
    target = null;
    
    constructor(target) {
        this.target = target;
        this.weight = Math.random()*2-1;
    }
    
    simulate() {
        return this.weight * this.target.activation;
    }   
    
    copy() {
        
        let connection = new Connection();
        connection.target = this.target;
        connection.weight = this.weight;
        
        return connection;
    }

    mutate() {

        this.weight += Math.random() * Connection.MUTATION_STRENGTH;

        if(Math.random() < Neuron.NEW_NEURON_PROBABILITY)
            this.insert_neuron();
    }

    insert_neuron() {

    }
}