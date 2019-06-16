class CustomGate extends Gate {

    //Proprietes fonctionnelles ----------------------------------------------------------------------------------------

    /*
        Fonctionnement des outputs:
        Il n'y a pas d'output reel mais seulement certaines portes du circuit interne
        marquees comme output (contenues dans outputGates). Au moment de connecter une
        autre porte a un output de cette CustomGate, on connecte en fait la porte directement
        a la porte interne correspondant a l'output selectionne grace a getGateForOutput.
        A la construction on cherche les portes Output, et on marque comme OutputGate les
        inputs de ces portes.
     */

    /*
        Fonctionnement des inputs:
        A la construction on cherche les portes Input, et on les remplace par des CustomGateInput
        qui serviront d'inputs
     */

    internGates = []; //Liste des portes contenues dans cette CustomGate
    customGateInputs = [];  //Liste des CustomGateInput. Ils ne sont pas presents dans internGates
    outputGates = []; //Liste des Nodes qui servent d'output

    update() {

        //Recupere les valeurs d'entrees et les met dans les CustomGateInput
        for(let i = 0; i < this.customGateInputs.length; i++)
            this.customGateInputs[i].output = this.inputs[i] && this.inputs[i].getInput().output;

        for(let gate of this.internGates)
            gate.update();
    }

    tickEnd() {
        for(let gate of this.internGates)
            gate.tickEnd();
    }

    /***
     * Renvoie la porte correspondante pour un numero d'output donne
     */
    getGateForOutput(index) {
        return this.outputGates[index];
    }

    /***
     * Renvoie l'index du connecteur le plus proche.
     * Si c'est un input l'index sera positif (1 et +), negatif si c'est un output (-1 et -)
     * @param x
     * @param y
     */
    getConnector(x,y) {

        if(x > this.x || this.maxInputs < 1) { //Si on est sur la droite de la porte ou qu'elle n'a pas d'inputs

            let minDistY = Infinity;
            let minIndex = 0;
            for(let i = 0; i < this.outputGates.length; i++) {
                let dist = Math.abs(this.y - this.height/2 + this.height*(i+1)/(this.outputGates.length+1) - y);
                if(dist < minDistY) {
                    minIndex = i+1;
                    minDistY = dist;
                }
            }

            return -minIndex;
        }

        let minDistY = Infinity;
        let minIndex = 0;
        for(let i = 0; i < this.maxInputs; i++) {
            let dist = Math.abs(this.getInputPosition(i)[1] - y);
            if(dist < minDistY) {
                minIndex = i+1;
                minDistY = dist;
            }
        }

        return minIndex;
    }

    static construct(fromGates) {

        let customGate = new CustomGate();

        //Parcours toutes les portes de haut en bas (pour avoir les inputs et les outputs dans le bon ordre)
        for(let gate of fromGates.sort((a,b) => a.y - b.y)) {

            if(gate instanceof Output && gate.inputs.length > 0) {

                //Si c'est un output on le remplace par une ConnectionNode
                customGate.outputGates.push(Basic.NODE(0,0,gate.inputs.map(connection => connection.destination)));
            }
            else if(gate instanceof Input) {

                //Si c'est un input on le remplace par un CustomGateInput
                let input = CustomGateInput.create();
                customGate.customGateInputs.push(input);
                customGate.maxInputs++;

                for(let g of fromGates)
                    for(let connection of g.inputs)
                        if(connection.destination === gate)
                            connection.destination = input;
            }
            else {

                //Sinon on le place juste dans les internGates
                customGate.internGates.push(gate);
            }
        }

        return customGate
            .setGraphicProperties(30,30,60,30+Math.max(customGate.maxInputs,customGate.outputGates.length)*20,"#379f1f","CustomGate");
    }

    //Proprietes graphiques --------------------------------------------------------------------------------------------

    draw() {

        //Met a jour la position des outputs pour que les connexions s'affichent correctement
        //TODO
        for(let i = 0; i < this.outputGates.length; i++) {

            let position = this.getOutputPosition(i);
            this.outputGates[i].x = position[0];
            this.outputGates[i].y = position[1];
        }

        super.draw();
    }

    /***
     * Renvoie la position de l'output index sur la porte
     * @param index
     */
    getOutputPosition(index) {
        return [
            this.x + this.width/2 - Connection.WIDTH,
            this.y - this.height/2 + this.height*(index+1)/(this.outputGates.length+1)
        ]
    }
}

class CustomGateInput extends Gate {

    static create() {

        return new CustomGateInput();
    }

}