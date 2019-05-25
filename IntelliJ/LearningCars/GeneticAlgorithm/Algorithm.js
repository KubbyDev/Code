class GeneticAlgorithm {

    static MAX_TICKS = 30*100;
    static AGENTS_NUMBER = 200;
    static SELECTION_SIZE = 50;

    static launch() {

        let game;
        let ticks = 0;
        let agents = [];
        for (let i = 0; i < GeneticAlgorithm.AGENTS_NUMBER; i++)
            agents.push(new Agent(new Car(false)));

        function getNewAgents() {

            //Selectionne les meilleurs
            agents.forEach(agent => agent.calculateFitness(game.circuit));
            agents.sort((a, b) => b.fitness - a.fitness);
            agents = agents.slice(0, GeneticAlgorithm.SELECTION_SIZE);

            //Remet tout le monde sur la ligne de depart
            agents.forEach(a => a.reset());

            //Cree des copies mutees des meilleurs
            for (let i = 0; i < GeneticAlgorithm.AGENTS_NUMBER-GeneticAlgorithm.SELECTION_SIZE; i++)
                agents.push(agents[i%GeneticAlgorithm.SELECTION_SIZE].copy().mutate());
        }

        function simulateGeneration() {

            game = new Game();
            game.cars = agents.map(agent => agent.car);
            game.initCars();
            ticks = 0;
        }

        function tick() {

            game.update();
            game.draw();
            game.drawNetwork(game.cars[0].controller.network);

            //Detection du passage de la ligne d'arrivee (agent.time === 0 permet de ne faire ca qu'une seule fois)
            for(let agent of agents)
                if(agent.time === 0 && agent.car.nextCheckpoint === game.circuit.checkpoints.length) {
                    agent.time = ticks;
                    agent.car.alive = false;
                }

            ticks++;

            if(ticks > GeneticAlgorithm.MAX_TICKS || game.cars.every(car => !car.alive)) {
                getNewAgents();
                simulateGeneration();
            }
        }

        simulateGeneration();
        setInterval(tick, 10);
    }
}