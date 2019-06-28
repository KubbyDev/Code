//Constructeur
function Board() {

    this.tiles = [];
    for(var x = 0; x < 8; x++) {
        this.tiles[x] = [];
        for(var y = 0; y < 8; y++)
            this.tiles[x][y] = new Tile(x,y);
    }

    return this;
}

Board.prototype.drawBackground = function () {

    //Couleur de la case (echange a chaque appel de getColor)
    var color = "";
    function getColor() {

        if(color[1] === '6')
            color = "#9e9e9e";
        else
            color = "#626262";

        return color;
    }

    //Dessin des cases
    for(var i = 0; i < 8; i++) {

        for(var j = 0; j < 8; j++) {

            ctx.fillStyle = getColor();
            ctx.fillRect(j*100, i*100, 100, 100);
        }

        //On inverse une deuxieme foix la couleur a chaque changement de ligne
        //Sinon ca marche pas
        ctx.fillStyle = getColor();
    }
};

Board.prototype.drawPieces = function() {

    for(var y = 0; y < 8; y++)
        for(var x = 0; x < 8; x++)
            if(board.tiles[x][y].piece)
                board.tiles[x][y].piece.draw();
};

Board.prototype.drawPossible = function (possible) {

    //Possible contient des tableaux de 2 valeurs: 0 = x, 1 = y

    for(var i = 0; i < possible.length; i++) {
        ctx.fillStyle = "#20ff48";
        ctx.fillRect(possible[i][0]*100-30+50, possible[i][1]*100-30+50, 60, 60);
    }

};

Board.prototype.isInBounds = function(x, y) {
    return x >= 0 && y >= 0 && x <= 7 && y <= 7;
};

Board.prototype.addPiece = function (x,y,piece) {

    piece.x = x;
    piece.y = y;
    board.tiles[x][y].piece = piece;
};

/**
 * Regarde si il y a une piece de la team donnee a la position donnee
 * Si aucune team n'est donnee on regarde n'importe quelle team
 * @param x
 * @param y
 * @param isWhite
 */
Board.prototype.hasPieceAtPosition = function (x,y,isWhite) {

    if(isWhite === undefined)
        return board.tiles[x][y].piece;

    return board.tiles[x][y].piece && board.tiles[x][y].piece.isWhite === isWhite;
};