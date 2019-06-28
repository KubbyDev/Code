/**
 * Constructeur
 * @param isWhite
 * @constructor
 */
function Bishop(isWhite) {

    Piece.call(this, isWhite);
    this.image.src = "Pieces/Images/bishop_" + (isWhite?"w":"b") + ".png";
}
Bishop.prototype = new Piece();