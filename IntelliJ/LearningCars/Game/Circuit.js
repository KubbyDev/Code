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

    static SIMPLE = new Circuit([
        new Line().setStartEnd(new Vector(8, 11),new Vector(8, 11)).setVisible(true),
        new Line().setStartEnd(new Vector(8, 11),new Vector(17, 270)).setVisible(true),
        new Line().setStartEnd(new Vector(17, 270),new Vector(116, 429)).setVisible(true),
        new Line().setStartEnd(new Vector(116, 429),new Vector(230, 554)).setVisible(true),
        new Line().setStartEnd(new Vector(230, 554),new Vector(350, 612)).setVisible(true),
        new Line().setStartEnd(new Vector(350, 612),new Vector(620, 646)).setVisible(true),
        new Line().setStartEnd(new Vector(620, 646),new Vector(784, 657)).setVisible(true),
        new Line().setStartEnd(new Vector(784, 657),new Vector(939, 664)).setVisible(true),
        new Line().setStartEnd(new Vector(939, 664),new Vector(1032, 635)).setVisible(true),
        new Line().setStartEnd(new Vector(1032, 635),new Vector(1119, 584)).setVisible(true),
        new Line().setStartEnd(new Vector(1119, 584),new Vector(1091, 510)).setVisible(true),
        new Line().setStartEnd(new Vector(1091, 510),new Vector(1052, 460)).setVisible(true),
        new Line().setStartEnd(new Vector(1052, 460),new Vector(988, 448)).setVisible(true),
        new Line().setStartEnd(new Vector(988, 448),new Vector(888, 439)).setVisible(true),
        new Line().setStartEnd(new Vector(888, 439),new Vector(770, 422)).setVisible(true),
        new Line().setStartEnd(new Vector(770, 422),new Vector(674, 394)).setVisible(true),
        new Line().setStartEnd(new Vector(674, 394),new Vector(612, 357)).setVisible(true),
        new Line().setStartEnd(new Vector(612, 357),new Vector(608, 326)).setVisible(true),
        new Line().setStartEnd(new Vector(608, 326),new Vector(624, 281)).setVisible(true),
        new Line().setStartEnd(new Vector(624, 281),new Vector(671, 270)).setVisible(true),
        new Line().setStartEnd(new Vector(671, 270),new Vector(758, 266)).setVisible(true),
        new Line().setStartEnd(new Vector(758, 266),new Vector(881, 308)).setVisible(true),
        new Line().setStartEnd(new Vector(881, 308),new Vector(971, 360)).setVisible(true),
        new Line().setStartEnd(new Vector(971, 360),new Vector(1036, 419)).setVisible(true),
        new Line().setStartEnd(new Vector(1036, 419),new Vector(1082, 465)).setVisible(true),
        new Line().setStartEnd(new Vector(1082, 465),new Vector(1144, 484)).setVisible(true),
        new Line().setStartEnd(new Vector(1144, 484),new Vector(1182, 482)).setVisible(true),
        new Line().setStartEnd(new Vector(1182, 482),new Vector(1226, 454)).setVisible(true),
        new Line().setStartEnd(new Vector(1226, 454),new Vector(1228, 432)).setVisible(true),
        new Line().setStartEnd(new Vector(1228, 432),new Vector(1234, 376)).setVisible(true),
        new Line().setStartEnd(new Vector(8, 12),new Vector(8, 12)).setVisible(true),
        new Line().setStartEnd(new Vector(8, 12),new Vector(113, 13)).setVisible(true),
        new Line().setStartEnd(new Vector(113, 13),new Vector(110, 114)).setVisible(true),
        new Line().setStartEnd(new Vector(110, 114),new Vector(148, 234)).setVisible(true),
        new Line().setStartEnd(new Vector(148, 234),new Vector(148, 234)).setVisible(true),
        new Line().setStartEnd(new Vector(148, 234),new Vector(244, 362)).setVisible(true),
        new Line().setStartEnd(new Vector(244, 362),new Vector(377, 477)).setVisible(true),
        new Line().setStartEnd(new Vector(377, 477),new Vector(518, 527)).setVisible(true),
        new Line().setStartEnd(new Vector(518, 527),new Vector(725, 554)).setVisible(true),
        new Line().setStartEnd(new Vector(725, 554),new Vector(904, 576)).setVisible(true),
        new Line().setStartEnd(new Vector(904, 576),new Vector(1010, 543)).setVisible(true),
        new Line().setStartEnd(new Vector(1010, 543),new Vector(998, 521)).setVisible(true),
        new Line().setStartEnd(new Vector(998, 521),new Vector(937, 507)).setVisible(true),
        new Line().setStartEnd(new Vector(937, 507),new Vector(792, 474)).setVisible(true),
        new Line().setStartEnd(new Vector(792, 474),new Vector(554, 390)).setVisible(true),
        new Line().setStartEnd(new Vector(554, 390),new Vector(536, 304)).setVisible(true),
        new Line().setStartEnd(new Vector(536, 304),new Vector(584, 246)).setVisible(true),
        new Line().setStartEnd(new Vector(584, 246),new Vector(681, 236)).setVisible(true),
        new Line().setStartEnd(new Vector(681, 236),new Vector(772, 236)).setVisible(true),
        new Line().setStartEnd(new Vector(772, 236),new Vector(844, 268)).setVisible(true),
        new Line().setStartEnd(new Vector(844, 268),new Vector(931, 292)).setVisible(true),
        new Line().setStartEnd(new Vector(931, 292),new Vector(976, 311)).setVisible(true),
        new Line().setStartEnd(new Vector(976, 311),new Vector(1028, 359)).setVisible(true),
        new Line().setStartEnd(new Vector(1028, 359),new Vector(1084, 389)).setVisible(true),
        new Line().setStartEnd(new Vector(1084, 389),new Vector(1135, 423)).setVisible(true),
        new Line().setStartEnd(new Vector(1135, 423),new Vector(1187, 443)).setVisible(true),
        new Line().setStartEnd(new Vector(1187, 443),new Vector(1203, 370)).setVisible(true),
    ],[
        new Checkpoint(new Vector(84, 262), -10, 170, 0),
        new Checkpoint(new Vector(506, 587), -74, 134, 1),
        new Checkpoint(new Vector(1059, 563), 20, 140, 2),
        new Checkpoint(new Vector(1025, 491), -48, 100, 3),
        new Checkpoint(new Vector(588, 371), -32, 100, 4),
        new Checkpoint(new Vector(605, 258), 38, 100, 5),
        new Checkpoint(new Vector(786, 258), -50, 54, 6),
        new Checkpoint(new Vector(1008, 366), -30, 56, 7),
        new Checkpoint(new Vector(1171, 462), -84, 82, 8),
        new Checkpoint(new Vector(1217, 373), 14, 48, 9),
    ]);
}

