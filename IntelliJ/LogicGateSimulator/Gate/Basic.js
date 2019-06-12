class Basic {

    static get UP_FUNC() { return () => true; }
    static get DOWN_FUNC() { return () => false; }
    static get NOT_FUNC() { return (i) => !i[0]; }
    static get AND_FUNC() { return (i) => i[0] && i[1]; }
    static get OR_FUNC() { return (i) => i[0] || i[1]; }
    static get XOR_FUNC() { return (i) => i[0] !== i[1]; }
    static get NAND_FUNC() { return (i) => !(i[0] && i[1]); }
    static get NOR_FUNC() { return (i) => !(i[0] || i[1]); }
    static get XNOR_FUNC() { return (i) => i[0] === i[1]; }

    static UP(x, y) {
        return new Gate()
            .setFonctionnalProperties(Basic.UP_FUNC, [])
            .setGraphicProperties(x,y,20,20,"#ae110b","1");
    }

    static DOWN(x, y) {
        return new Gate()
            .setFonctionnalProperties(Basic.DOWN_FUNC, [])
            .setGraphicProperties(x,y,20,20,"#5f5f5f","0");
    }

    static NOT(x, y, input) {
        return new Gate()
            .setFonctionnalProperties(Basic.NOT_FUNC, input)
            .setGraphicProperties(x,y,20,20,"#379f1f","NOT");
    }

    static AND(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties(Basic.AND_FUNC, inputs)
            .setGraphicProperties(x,y,20,20,"#379f1f","AND");
    }

    static OR(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties(Basic.OR_FUNC, inputs)
            .setGraphicProperties(x,y,20,20,"#379f1f","OR");
    }

    static XOR(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties(Basic.XOR_FUNC, inputs)
            .setGraphicProperties(x,y,20,20,"#379f1f","XOR");
    }

    static NAND(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties(Basic.NAND_FUNC, inputs)
            .setGraphicProperties(x,y,20,20,"#379f1f","NAND");
    }

    static NOR(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties(Basic.NOR_FUNC, inputs)
            .setGraphicProperties(x,y,20,20,"#379f1f","NOR");
    }

    static XNOR(x, y, inputs) {
        return new Gate()
            .setFonctionnalProperties(Basic.XNOR_FUNC, inputs)
            .setGraphicProperties(x,y,20,20,"#379f1f","XNOR");
    }
}