class Circuit {

    lines;
    checkpoints;

    constructor(lines, checkpoints) {
        this.lines = lines;
        this.checkpoints = checkpoints;
    }

    draw() {

        for(let line of this.lines)
            line.draw();
    }
}

