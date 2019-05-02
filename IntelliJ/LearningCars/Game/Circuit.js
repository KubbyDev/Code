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

    new Line().setStartEnd([2, 2],[2, 150]).setVisible(true),
    new Line().setStartEnd([2, 150],[100, 300]).setVisible(true),
    new Line().setStartEnd([100, 300],[300, 320]).setVisible(true),
    new Line().setStartEnd([300, 320],[320, 320]).setVisible(true),
    new Line().setStartEnd([320, 320],[350, 120]).setVisible(true),

    new Line().setStartEnd([100, 2],[120, 200]).setVisible(true),
    new Line().setStartEnd([120, 200],[250, 210]).setVisible(true),
    new Line().setStartEnd([250, 210],[320, 100]).setVisible(true)

]);