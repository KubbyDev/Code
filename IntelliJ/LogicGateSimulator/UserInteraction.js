
//Traque la position de la souris
let mouseX = 0;
let mouseY = 0;
document.addEventListener('mousemove', function(event) {
    mouseX = event.clientX - canvas.offsetLeft;
    mouseY = event.clientY - canvas.offsetTop;
});

let mode = 0;
//0 = BUILD: cliquer une fois pour selectionner une porte, puis une autre fois pour la placer
//Cliquer sur les portes a droite pour les ajouter, ou drag une porte dans la poubelle pour la supprimer
let selectedGate = null;
//1 = CONNECTION: Cliquer sur une porte pour la selectionner, puis sur une autre porte pour la connecter
//2 = INTERACTION: Cliquer sur un interrupteur pour changer son etat

document.addEventListener('click', function(event) {

    if(mode === 0)
        if(selectedGate === null)
            selectedGate = getGateAtPosition(mouseX, mouseY, gates);
        else
            selectedGate = null;

});

document.onkeydown = function(event) {



};

/***
 * Renvoie la porte a la position demandee parmis les portes en parametre
 * @param x
 * @param y
 * @param gates
 */
function getGateAtPosition(x, y, gates) {

    for (let gate of gates)
        if (Math.abs(x - gate.x) < gate.width / 2 && Math.abs(y - gate.y) < gate.height / 2)
            return gate;

    return null;
}

function userInteractions() {

    if(mode === 0 && selectedGate !== null) {
        selectedGate.x = mouseX;
        selectedGate.y = mouseY;
    }

}