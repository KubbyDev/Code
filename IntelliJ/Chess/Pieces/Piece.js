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