
var board = new Board();
Init();
var possibleMoves = [];
var whiteTurn = true;
setTimeout(updateDisplay, 0);

//Traque la position de la souris sur le canvas
var mouseX = 0;
var mouseY = 0;
document.addEventListener('mousemove', function(event) {
    mouseX = event.clientX - canvas.offsetLeft;
    mouseY = event.clientY - canvas.offsetTop;
});

//On Click
document.addEventListener('click', function () {
    possibleMoves = board.tiles[Math.floor(mouseX/100)][Math.floor(mouseY/100)].piece.getPossibleMoves();
    updateDisplay();
});

function updateDisplay() {

    board.drawBackground();
    board.drawPossible(possibleMoves);
    board.drawPieces();
}

function Init() {

    board.addPiece(0,0,new Rook(true));
    board.addPiece(1,0,new Knight(true));
    board.addPiece(2,0,new Bishop(true));
    board.addPiece(3,0,new Queen(true));
    board.addPiece(4,0,new King(true));
    board.addPiece(5,0,new Bishop(true));
    board.addPiece(6,0,new Knight(true));
    board.addPiece(7,0,new Rook(true));

    for(var i = 0; i < 8; i++)
        board.addPiece(i, 1, new Pawn(true));

    board.addPiece(0,7,new Rook(false));
    board.addPiece(1,7,new Knight(false));
    board.addPiece(2,7,new Bishop(false));
    board.addPiece(3,7,new Queen(false));
    board.addPiece(4,7,new King(false));
    board.addPiece(5,7,new Bishop(false));
    board.addPiece(6,7,new Knight(false));
    board.addPiece(7,7,new Rook(false));

    for(var y = 0; y < 8; y++)
        board.addPiece(y, 6, new Pawn(false));
}