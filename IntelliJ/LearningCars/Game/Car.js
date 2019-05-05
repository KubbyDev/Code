class Car {

    controller;
    position = new Vector(0,0);
    rotation = 0;
    width = 5;
    length = 12;
    color = "#FF0000";
    speed = 1;
    turnRate = 1;
    alive = true;

    corners = [new Vector(0,0), new Vector(0,0), new Vector(0,0), new Vector(0,0)];
    hitbox = [new Line(), new Line(), new Line(), new Line()];
    areCornersCorrect = false;

    constructor(isHuman = false) {

        if(isHuman)
            this.controller = humanController;
        else
            this.controller = new NetworkController(this);
    }

    //Recupere les ordres venant du controller et les applique
    update(circuit) {

        let inputs = this.controller.getInputs();

        this.moveForward(inputs[0]);
        this.turn(inputs[1]);

        if(this.isColliding(circuit))
            this.alive = false;
    }

    setColor(color) {
        this.color = color;
        return this;
    }

    setPosition(position) {
        this.position = position;
        return this;
    }

    setPosition(x, y) {
        this.position = new Vector(x,y);
        return this;
    }

    setRotation(r) {
        this.rotation = r;
        return this;
    }

    getCorners() {

        if(!this.areCornersCorrect)
            this.calcCorners();

        return this.corners;
    }

    calcCorners() {

        let degR = this.rotation*Math.PI/180;

        this.corners[0] = new Vector(
            this.position.x + this.length*Math.cos(degR) - this.width*Math.sin(degR),
            this.position.y + this.length*Math.sin(degR) + this.width*Math.cos(degR)
        );

        this.corners[1] = new Vector(
            this.position.x + this.length*Math.cos(degR) + this.width*Math.sin(degR),
            this.position.y + this.length*Math.sin(degR) - this.width*Math.cos(degR)
        );

        this.corners[2] = new Vector(
            this.position.x - this.length*Math.cos(degR) + this.width*Math.sin(degR),
            this.position.y - this.length*Math.sin(degR) - this.width*Math.cos(degR)
        );

        this.corners[3] = new Vector(
            this.position.x - this.length*Math.cos(degR) - this.width*Math.sin(degR),
            this.position.y - this.length*Math.sin(degR) + this.width*Math.cos(degR)
        );

        this.areCornersCorrect = true;
    }

    moveForward(enginePower) {

        this.position.x += Math.cos(this.rotation*Math.PI/180)*enginePower*this.speed;
        this.position.y += Math.sin(this.rotation*Math.PI/180)*enginePower*this.speed;

        this.areCornersCorrect = false;
    }

    turn(input) {

        this.rotation += this.turnRate * -input;

        this.areCornersCorrect = false;
    }

    isColliding(circuit) {

        this.getCorners();
        this.hitbox[0].setStartEnd(this.corners[0], this.corners[1]);
        this.hitbox[1].setStartEnd(this.corners[1], this.corners[2]);
        this.hitbox[2].setStartEnd(this.corners[2], this.corners[3]);
        this.hitbox[3].setStartEnd(this.corners[3], this.corners[0]);

        for(let line of this.hitbox)
            if(line.isColliding(circuit))
                return true;

        return false;
    }

    draw() {

        drawRect(this.getCorners(), this.color);

        /*
        for(let line of this.hitbox)
            line.draw();
         */
    }
}