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