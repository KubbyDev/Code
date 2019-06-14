class UserInteraction {

    static update() {
        Interface.getCurrentMode().update();
    }
}

//Traque la position de la souris
let mouseX = 0;
let mouseY = 0;
document.addEventListener('mousemove', function(event) {
    mouseX = event.clientX - canvas.offsetLeft;
    mouseY = event.clientY - canvas.offsetTop;
});

//Traque les clics souris
document.addEventListener('click', function() {

    //Si on a clique sur un bouton on appelle son onClick
    let clickedButton = Interface.getButtonAtPosition(mouseX, mouseY);
    if(clickedButton) {
        clickedButton.onClick();
        return;
    }

    //Sinon on appelle le onClick du mode dans lequel on est
    Interface.getCurrentMode().onClick();
});

//Traque les entrees clavier
document.onkeydown = function(event) {

    switch(event.key) {
        case 'b':
            Interface.mode = 0;
            BuildMode.onEnable();
            break;
        case 'c':
            Interface.mode = 1;
            WiringMode.onEnable();
            break;
        case 'i':
            Interface.mode = 2;
            InteractionMode.onEnable();
            break;
    }
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
