class Output extends Gate {

    downColor = "#7a7a7a";
    upColor = "#ea120c";

    tickEnd() {
        super.tickEnd();
        this.color = this.output ? this.upColor : this.downColor;
    }
}