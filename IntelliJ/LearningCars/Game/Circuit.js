class Circuit {

    lines;

    constructor(lines) {
        this.lines = lines;
    }

    draw() {

        for(let line of this.lines)
            line.draw();
    }
}

const circuit1 = new Circuit([

    new Line().setStartEnd(new Vector(2, 2),new Vector(2, 150)).setVisible(true),
    new Line().setStartEnd(new Vector(2, 150),new Vector(100, 300)).setVisible(true),
    new Line().setStartEnd(new Vector(100, 300),new Vector(300, 320)).setVisible(true),
    new Line().setStartEnd(new Vector(300, 320),new Vector(320, 320)).setVisible(true),
    new Line().setStartEnd(new Vector(320, 320),new Vector(350, 120)).setVisible(true),

    new Line().setStartEnd(new Vector(100, 2),new Vector(120, 200)).setVisible(true),
    new Line().setStartEnd(new Vector(120, 200),new Vector(250, 210)).setVisible(true),
    new Line().setStartEnd(new Vector(250, 210),new Vector(320, 100)).setVisible(true)

]);