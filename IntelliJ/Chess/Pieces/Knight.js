/**
 * Constructeur
 * @param isWhite
 * @constructor
 */
function Knight(isWhite) {

    Piece.call(this, isWhite);
    this.image.src = "Pieces/Images/knight_" + (isWhite?"w":"b") + ".png";
}
Knight.prototype = new Piece();

Knight.prototype.getPossibleMoves = function () {

    var moves = [];
    var team = this.isWhite;

    testMove(this.x+1, this.y+2);
    testMove(this.x+2, this.y+1);
    testMove(this.x+2, this.y-1);
    testMove(this.x+1, this.y-2);
    testMove(this.x-1, this.y+2);
    testMove(this.x-2, this.y+1);
    testMove(this.x-2, this.y-1);
    testMove(this.x-1, this.y-2);

    function testMove(x, y) {
        if(board.isInBounds(x,y) && !board.hasPieceAtPosition(x,y,team))
            moves.push([x,y]);
    }

    return moves;
};