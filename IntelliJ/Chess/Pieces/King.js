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