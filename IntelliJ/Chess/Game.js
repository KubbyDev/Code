
var board = new Board();
Init();
var allowedMoves = [];
var selectedPiece;
var whiteTurn = true;
setTimeout(updateDisplay, 500);

//Traque la position de la souris sur le canvas
var mouseX = 0;
var mouseY = 0;
document.addEventListener('mousemove', function(event) {
    mouseX = event.clientX - canvas.getBoundingClientRect().left;
    mouseY = event.clientY - canvas.getBoundingClientRect().top;
});

//On Click
document.addEventListener('click', clickEvent);
function clickEvent() {

    //Recuperation de la tile sur laquelle l'utilisateur a clique
    var selectedTile = board.tiles[Math.floor(mouseX/100)][Math.floor(mouseY/100)];

    //Si on a clique sur un endroit ou on peut bouger et qu'on a une piece selectionnee on bouge
    if(selectedPiece && allowedMoves.filter(function (p) { return p[0] === selectedTile.x && p[1] === selectedTile.y }).length > 0) {

        selectedPiece.move(selectedTile.x, selectedTile.y);
        allowedMoves = [];
        selectedPiece = undefined;
        whiteTurn = !whiteTurn;

        if(board.isKingCheck(whiteTurn) && (whiteTurn ? board.whiteKing : board.blackKing).getAllowedMoves().length === 0) {
            checkMate(whiteTurn);
            return;
        }
    }
    //Si on clique sur une autre piece alliee on la selectionne
    else if(selectedTile.piece && selectedTile.piece.isWhite === whiteTurn && selectedTile.piece !== selectedPiece) {

        selectedPiece = selectedTile.piece;
        allowedMoves = selectedPiece.getAllowedMoves();
    }
    //Sinon on annule la selection
    else {

        allowedMoves = [];
        selectedPiece = undefined;
    }

    updateDisplay();

}

function updateDisplay() {

    board.drawBackground();
    board.drawPossible(allowedMoves);

    if(board.isKingCheck(whiteTurn))
        (whiteTurn ? board.whiteKing : board.blackKing).drawCheckIndicator();

    if(selectedPiece)
        selectedPiece.drawIndicator(0, 1, 0);

    board.drawPieces();
}

function checkMate(winnerIsWhite) {

    document.removeEventListener('click', clickEvent);

    board.drawBackground();
    board.drawPossible(allowedMoves);
    (winnerIsWhite ? board.whiteKing : board.blackKing).drawIndicator(1,0,0);
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