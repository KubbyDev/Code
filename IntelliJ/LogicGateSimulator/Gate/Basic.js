class Basic {

    static UP(x, y) {
        return new Gate()
            .setFonctionnalProperties(() => true, [])
            .setGraphicProperties(x,y,40,40,"#ae110b","1");
    }

    static DOWN(x, y) {
        return new Gate()
            .setFonctionnalProperties(() => false, [])
            .setGraphicProperties(x,y,40,40,"#5f5f5f","0");
    }

    static NOT(x, y, input) {

        let g = new Gate()
            .setFonctionnalProperties((i) => !i[0], input)
            .setGraphicProperties(x,y,30,20,"#379f1f","NOT");

        g.drawBody = function() {
            ctx.beginPath();
            ctx.moveTo(this.x - this.width/2, this.y - this.height/2);
            ctx.lineTo(this.x + this.width/2, this.y);
            ctx.lineTo(this.x - this.width/2, this.y + this.height/2);
            ctx.fillStyle = this.color;
            ctx.fill();
            ctx.closePath();
        };
        g.hideName = true;

        return g;
    }

    static AND(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties((i) => i[0] && i[1], inputs)
            .setGraphicProperties(x,y,40,40,"#379f1f","AND");
    }

    static OR(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties((i) => i[0] || i[1], inputs)
            .setGraphicProperties(x,y,40,40,"#379f1f","OR");
    }

    static XOR(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties((i) => i[0] !== i[1], inputs)
            .setGraphicProperties(x,y,40,40,"#379f1f","XOR");
    }

    static NAND(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties((i) => !(i[0] && i[1]), inputs)
            .setGraphicProperties(x,y,40,40,"#379f1f","NAND");
    }

    static NOR(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties((i) => !(i[0] || i[1]), inputs)
            .setGraphicProperties(x,y,40,40,"#379f1f","NOR");
    }

    static XNOR(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties((i) => i[0] === i[1], inputs)
            .setGraphicProperties(x,y,40,40,"#379f1f","XNOR");
    }

    static CLOCK(x, y, period) {
        return new Clock()
            .setPeriod(period)
            .setGraphicProperties(x,y,40,40,"#3d79e7", "CLOCK");
    }

    static OUTPUT(x,y, input) {
        return new Output()
            .setFonctionnalProperties((i) => i[0], input)
            .setGraphicProperties(x,y,40,40,"#000000", "")
    }

    static SWITCH(x,y,input) {
        return new Switch()
            .setFonctionnalProperties((i) => i[0], input)
            .setGraphicProperties(x,y,30,20,"#ffbb25","SWITCH")
    }
}