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

Piece.prototype.drawIndicator = function (rIntensity, gIntensity, bIntensity) {

    String.prototype.padLeft = function (length, character) {
        var str = this;
        while(str.length < length)
            str = character + str;
        return str;
    };

    function sqr(a) { return a*a; }

    for(var y = -50; y < 50; y++) {
        for(var x = -50; x < 50; x++) {
            var intensity = Math.max(0, Math.min(1-(sqr(x)+sqr(y))/2500, 1));
            ctx.globalAlpha = intensity;
            ctx.fillStyle = "#"
                + Math.floor(intensity*255*rIntensity).toString(16).padLeft(2, '0')
                + Math.floor(intensity*255*gIntensity).toString(16).padLeft(2, '0')
                + Math.floor(intensity*255*bIntensity).toString(16).padLeft(2, '0');
            ctx.fillRect(this.x*100+50 + x, this.y*100+50 + y, 1, 1);
        }
    }

    ctx.globalAlpha = 1;
};