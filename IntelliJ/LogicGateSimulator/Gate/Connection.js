class Connection {

    constructor(origin, destination) {
        this.destination = destination;
        this.origin = origin;
    }

    //Proprietes fonctionnelles ----------------------------------------------------------------------------------------

    destination;   //La gate de laquelle cette connection part (output d'une porte)
    origin;        //La gate qui contient cette connection (cette connection est donc son input)

    //Proprietes graphiques --------------------------------------------------------------------------------------------

    downColor = "#353535";
    upColor = "#ae110b";

    draw() {

        ctx.beginPath();
        ctx.strokeStyle = this.destination.output ? this.upColor : this.downColor;
        ctx.moveTo(this.destination.x, this.destination.y);
        ctx.lineTo(this.origin.x, this.origin.y);
        ctx.stroke();
        ctx.closePath();
    }
}