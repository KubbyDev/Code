/**
 * Constructeur
 * @param isWhite
 * @constructor
 */
function Pawn(isWhite) {

    Piece.call(this, isWhite);
    this.hasMoved = false;
    this.image.src = "Pieces/Images/pawn_" + (isWhite?"w":"b") + ".png";
}
Pawn.prototype = new Piece();

Pawn.prototype.getPossibleMoves = function() {

    var moves = [];

    //Si il n'y a rien une case devant
    if(board.isInBounds(this.x, this.y+(this.isWhite?1:-1)) && !board.tiles[this.x][this.y+(this.isWhite?1:-1)].piece) {

        moves.push([this.x, this.y+(this.isWhite?1:-1)]);

        //Si il n'y a rien 2 cases devant et qu'on a jamais bouge
        if(board.isInBounds(this.x, this.y+2*(this.isWhite?1:-1)) && !board.tiles[this.x][this.y+2*(this.isWhite?1:-1)].piece && !this.hasMoved)
            moves.push([this.x,this.y+2*(this.isWhite?1:-1)]);
    }

    //Si il y a un ennemi en diagonale
    if(board.isInBounds(this.x+1, this.y+(this.isWhite?1:-1)) && board.tiles[this.x+1][this.y+(this.isWhite?1:-1)].piece)
        moves.push([this.x+1,this.y+(this.isWhite?1:-1)]);
    if(board.isInBounds(this.x-1, this.y+(this.isWhite?1:-1)) && board.tiles[this.x-1][this.y+(this.isWhite?1:-1)].piece)
        moves.push([this.x-1,this.y+(this.isWhite?1:-1)]);

    return moves;
};

Pawn.prototype.move = function(x,y) {

    Piece.prototype.move.call(this, x, y);
    this.hasMoved = true;
};