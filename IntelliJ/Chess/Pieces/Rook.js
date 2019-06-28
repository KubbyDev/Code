/**
 * Constructeur
 * @param isWhite
 * @constructor
 */
function Rook(isWhite) {

    Piece.call(this, isWhite);
    this.image.src = "Pieces/Images/rook_" + (isWhite?"w":"b") + ".png";
}
Rook.prototype = new Piece();

Rook.prototype.getPossibleMoves = function () {

    var moves = [];

    var startX = this.x;
    var startY = this.y;
    var enemyTeam = !this.isWhite;
    var dirX = 0;
    var dirY = -1;
    calcLine();

    dirX = 0;
    dirY = 1;
    calcLine();

    dirX = -1;
    dirY = 0;
    calcLine();

    dirX = 1;
    dirY = 0;
    calcLine();

    function calcLine() {

        var i = 1;
        var x = startX+i*dirX;
        var y = startY+i*dirY;

        while(board.isInBounds(x,y) && !board.hasPieceAtPosition(x,y)) {
            moves.push([x,y]);
            i++;
            x = startX+i*dirX;
            y = startY+i*dirY;
        }

        if(board.isInBounds(x,y) && board.hasPieceAtPosition(x,y,enemyTeam))
            moves.push([x,y]);
    }

    return moves;
};