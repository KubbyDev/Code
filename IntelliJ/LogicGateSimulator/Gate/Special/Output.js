class Output extends Gate {

    //Proprietes fonctionnelles ----------------------------------------------------------------------------------------

    tickEnd() {
        super.tickEnd();
        this.color = this.output ? this.upColor : this.downColor;
    }

    //Proprietes graphiques --------------------------------------------------------------------------------------------

    upColor = "#ea120c";
    downColor = "#7a7a7a";
}