class GeneticAlgorithm {

    static MAX_TICKS = 30*100;
    static AGENTS_NUMBER = 50;

    static launch() {

        let game;
        let ticks = 0;
        let agents = [];
        for (let i = 0; i < GeneticAlgorithm.AGENTS_NUMBER; i++)
            agents.push(new Agent(new Car(false)));

        function getNewAgents() {

            //Selectionne les 10 meilleurs
            agents.forEach(agent => agent.calculateFitness(game.circuit));
            agents.sort((a, b) => b.fitness - a.fitness);
            agents = agents.slice(0,10);

            //Remet tout le monde sur la ligne de depart
            agents.forEach(a => a.reset());

            //Cree des copies mutees des 10 meilleurs
            for (let i = 0; i < 90; i++)
                agents.push(agents[i%10].copy().mutate());
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