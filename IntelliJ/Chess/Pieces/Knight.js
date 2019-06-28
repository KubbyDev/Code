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