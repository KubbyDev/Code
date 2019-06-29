function Piece(isWhite) {

    this.x = undefined;
    this.y = undefined;
    this.isWhite = isWhite;
    this.image = new Image();
}

Piece.prototype.move = function (x, y) {

    board.tiles[this.x][this.y].piece = undefined;
    this.x = x;
    this.y = y;

    if(board.tiles[x][y].piece)
        board.killPiece(board.tiles[x][y].piece);

    board.tiles[x][y].piece = this;
};

Piece.prototype.draw = function () {

    if(!this.image.src) {
        ctx.fillStyle = "#8c8c8c";
        ctx.fillRect(this.x*100-30+50, this.y*100-30+50, 60, 60);
        return;
    }

    ctx.drawImage(this.image, this.x*100, this.y*100, 100, 100);
};

Piece.prototype.getAllowedMoves = function () {

    var moves = [];

    //On recupere tous les mouvements que la piece peut faire
    var possibleMoves = this.getPossibleMoves(); //Cette fonction est definie sur chaque type de piece

    //On enregistre la position actuelle de la piece
    var x = this.x;
    var y = this.y;

    //Pour chaque position possible
    for(var i = 0; i < possibleMoves.length; i++) {

        var moveX = possibleMoves[i][0];
        var moveY = possibleMoves[i][1];

        //On enregistre la piece qui etait a la position
        var previousPiece = board.tiles[moveX][moveY].piece;

        //On fait bouger la piece a la position
        board.tiles[this.x][this.y].piece = undefined;
        this.x = moveX;
        this.y = moveY;
        board.tiles[moveX][moveY].piece = this;

        //Si le roi n'est pas en echec, le mouvement est autorise
        if(!board.isKingCheck(this.isWhite, previousPiece))
            moves.push(possibleMoves[i]);

        //On remet la piece a sa position d'origine
        //Et on remet la piece qui etait a la position avant le mouvement
        board.tiles[moveX][moveY].piece = previousPiece;
        this.x = x;
        this.y = y;
        board.tiles[x][y].piece = this;
    }

    return moves;
};