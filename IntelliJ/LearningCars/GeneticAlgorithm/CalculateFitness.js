function calculateFitness(agents) {

    let game = new Game();

    for(let i = 0; i < 100; i++)
        game.cars.push(new Car(false).setColor("#FF0000").setPosition(80,40).setRotation(90));

    function tick() {

        game.update(); //Ces fonctions sont dans game
        game.draw();


    }

    function allEnded() {

    }

    while(!allEnded()) {
        tick();

    }

}