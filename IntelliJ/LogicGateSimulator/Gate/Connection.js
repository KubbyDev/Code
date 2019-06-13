class Connection {

    constructor(origin, destination) {
        this.destination = destination;
        this.origin = origin;
    }

    //Proprietes fonctionnelles ----------------------------------------------------------------------------------------

    destination;   //La gate de laquelle cette connection part (output d'une porte)
    origin;        //La gate qui contient cette connection (cette connection est donc son input)

    //Proprietes graphiques --------------------------------------------------------------------------------------------

    downColor = "#d3d3d3";
    upColor = "#ea120c";
    intermediates = [];

    draw() {

        //Calcule les points intermediaires par lesquels cette connexion passe avant d'arriver a sa cible
        this.calculateIntermediates();

        ctx.beginPath();
        ctx.strokeStyle = this.destination.output ? this.upColor : this.downColor;
        ctx.moveTo(this.origin.x, this.origin.y);

        for(let point of this.intermediates)
            ctx.lineTo(point[0], point[1]);

        ctx.lineTo(this.destination.x, this.destination.y);
        ctx.stroke();
        ctx.closePath();
    }

    /***
     * Calcule les points intermediaires par lesquels cette connexion passe avant d'arriver a sa cible
     */
    calculateIntermediates() {

        let averageX = (this.origin.x + this.destination.x) /2;
        let averageY = (this.origin.y + this.destination.y) /2;

        if(this.origin.x < this.destination.x + 20) //Cas ou la porte d'arrivee est derriere la porte de depart
            this.intermediates = [
                [this.origin.x - 30, this.origin.y],
                [this.origin.x - 30, averageY],
                [this.destination.x + 30, averageY],
                [this.destination.x + 30, this.destination.y],
            ];
        else
            this.intermediates = [
                [averageX, this.origin.y],
                [averageX, this.destination.y]
            ];
    }
}