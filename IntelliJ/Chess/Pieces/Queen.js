/**
 * Constructeur
 * @param isWhite
 * @constructor
 */
function Queen(isWhite) {

    Piece.call(this, isWhite);
    this.image.src = "Pieces/Images/queen_" + (isWhite?"w":"b") + ".png";
}
Queen.prototype = new Piece();

Queen.prototype.getPossibleMoves = function () {

    var moves = [];

    var startX = this.x;
    var startY = this.y;
    var enemyTeam = !this.isWhite;

    var dirX = 1;
    var dirY = 1;
    calcLine();

    dirY = 0;
    calcLine();

    dirY = -1;
    calcLine();

    dirX = 0;
    calcLine();

    dirX = -1;
    calcLine();

    dirY = 0;
    calcLine();

    dirY = 1;
    calcLine();

    dirX = 0;
    calcLine();

    dirX = 1;
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