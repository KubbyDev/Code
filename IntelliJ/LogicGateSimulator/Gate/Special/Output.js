class Output extends Gate {

    /*
        Ampoule
        Sert aussi a designer les sorties des CustomGates
     */

    //Proprietes fonctionnelles ----------------------------------------------------------------------------------------

    tickEnd() {
        super.tickEnd();
        this.color = this.output ? this.upColor : this.downColor;
    }

    //Proprietes graphiques --------------------------------------------------------------------------------------------

    upColor = "#ea120c";
    downColor = "#7a7a7a";
}