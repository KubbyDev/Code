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

    /***
     * Dessine la connexion. position definit la position de la connection sur le cote input de l'origine
     * @param position
     */
    draw(position) {

        //Position de la connection sur le cote input de l'origine
        let inputY = this.origin.y - this.origin.height/2 + position*this.origin.height;

        //Dessine la connection
        ctx.beginPath();
        ctx.strokeStyle = this.destination.output ? this.upColor : this.downColor;
        ctx.moveTo(this.origin.x - this.origin.width/2, inputY);
        for(let point of this.calculateIntermediates(this.destination.x, this.destination.y, this.origin.x, inputY))
            ctx.lineTo(point[0], point[1]);
        ctx.lineTo(this.destination.x, this.destination.y);
        ctx.stroke();
        ctx.closePath();
    }

    /***
     * Calcule les points intermediaires par lesquels cette connexion passe avant d'arriver a sa cible
     * From est la porte qui a la connection comme output, To est celle qui a la connection comme input
     */
    calculateIntermediates(fromX, fromY, toX, toY) {

        let averageX = (toX + fromX) /2;
        let averageY = (toY + fromY) /2;

        if(toX - this.origin.width/2 - 5 < fromX + this.destination.width/2 + 5) //Cas ou la porte d'arrivee est derriere la porte de depart
            return [
                [toX - this.origin.width/2 - 5, toY],
                [toX - this.origin.width/2 - 5, averageY],
                [fromX + this.destination.width/2 + 5, averageY],
                [fromX + this.destination.width/2 + 5, fromY],
            ];
        else
            return [
                [averageX, toY],
                [averageX, fromY]
            ];
    }
}