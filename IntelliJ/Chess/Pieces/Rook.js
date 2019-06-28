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