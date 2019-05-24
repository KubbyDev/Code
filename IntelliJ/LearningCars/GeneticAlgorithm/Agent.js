class Agent {

    car;
    fitness;
    time = 0; //Le temps pour arriver a la ligne d'arrivee en ticks

    constructor(car) {

        this.car = car;
        this.fitness = 0;
    }

    calculateFitness(circuit) {

        //Le chiffre des milliers est le numero du checkpoint atteint
        this.fitness = 1000 * this.car.nextCheckpoint;

        //Si la voiture a atteint le dernier checkpoint
        if(this.car.nextCheckpoint === circuit.checkpoints.length)
            //On ajoute un nombre qui depend du temps de parcours du circuit (plus grand si le temps est plus petit)
            this.fitness += GeneticAlgorithm.MAX_TICKS - this.time;
        //Si la voiture n'a pas atteint le dernier checkpoint
        else
            //On ajoute 1000 - la distance entre la voiture et le checkpoint suivant
            this.fitness += 1000 - Vector.distance(circuit.checkpoints[this.car.nextCheckpoint].center, this.car.position);
    }

    reset() {

        this.car.position = new Vector(0,0);
        this.car.rotation = 0;
        this.car.alive = true;
        this.car.nextCheckpoint = 0;
        this.car.areCornersCorrect = false;

        this.time = 0;
    }

    copy() {

        let agent = Agent.fromNetwork(this.car.controller.network.copy());
        agent.car.color = this.car.color;
        return agent;
    }

    static fromNetwork(network) {

        let car = new Car();

        car.controller = new NetworkController(car);
        car.controller.network = network;

        return new Agent(car);
    }

    mutate() {
        this.car.controller.network.mutate();
        return this;
    }
}