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