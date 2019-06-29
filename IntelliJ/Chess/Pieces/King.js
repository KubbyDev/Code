/**
 * Constructeur
 * @param isWhite
 * @constructor
 */
function King(isWhite) {

    Piece.call(this, isWhite);
    this.image.src = "Pieces/Images/king_" + (isWhite?"w":"b") + ".png";
}
King.prototype = new Piece();

King.prototype.getPossibleMoves = function () {

    var moves = [];
    var team = this.isWhite;

    testMove(this.x+1, this.y+1);
    testMove(this.x+1, this.y);
    testMove(this.x+1, this.y-1);
    testMove(this.x-1, this.y+1);
    testMove(this.x-1, this.y);
    testMove(this.x-1, this.y-1);
    testMove(this.x, this.y+1);
    testMove(this.x, this.y-1);

    function testMove(x, y) {
        if(board.isInBounds(x,y) && !board.hasPieceAtPosition(x,y,team))
            moves.push([x,y]);
    }

    return moves;
};

King.prototype.drawCheckIndicator = function () {

    function sqr(x) { return x*x; }

    for(var y = -50; y < 50; y++) {
        for(var x = -50; x < 50; x++) {
            var intensity = Math.max(0, Math.min(1-(sqr(x)+sqr(y))/2500, 1));
            ctx.globalAlpha = intensity;
            ctx.fillStyle = "#" + Math.floor(intensity*255).toString(16) + "0000";
            ctx.fillRect(this.x*100+50 + x, this.y*100+50 + y, 1, 1);
        }
    }

    ctx.globalAlpha = 1;
};