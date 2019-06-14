class Gate {

    //Proprietes fonctionnelles ----------------------------------------------------------------------------------------

    activation;            //Fonction d'activation de la porte (and => a && b)
    inputs = [];           //References aux connections vers les portes auquelles cette portes est connectee
    tempOutput = false;    //La valeur de l'output pendant le tick. Il passe dans output a la fin du tick
    output = false;        //Valeur de la sortie de la porte
    maxInputs = 0;         //Le nombre d'inputs que cette porte accepte

    /***
     * Modifie les proprietes fonctionnelles de la gate
     * @param activation
     * @param inputs
     * @param maxInputs
     */
    setFonctionnalProperties(activation, inputs, maxInputs) {

        this.activation = activation;
        this.maxInputs = maxInputs !== undefined ? maxInputs : 0;
        this.setInputs(inputs !== undefined ? inputs : []);
        return this;
    }

    /***
     * Modifie les inputs de la gate
     * @param inputGates
     */
    setInputs(inputGates) {
        this.inputs = inputGates.map(inputGate => new Connection(this, inputGate)).slice(0,this.maxInputs);
        return this;
    }

    /***
     * Ajoute une entree a cette porte (ne fait rien si elle a deja tous ses inputs)
     * @param gate
     */
    addInput(gate) {
        if (this.inputs.length < this.maxInputs)
            this.inputs.push(new Connection(this, gate));
    }

    /***
     * Met a jour la sortie de la porte en fonction des entrees
     */
    update() {
        let inputs = this.inputs.map(connection => connection.destination.output);
        this.tempOutput = this.activation(inputs);
    }

    /***
     * Appeller cette fonction a la fin de chaque tick pour mettre a jour sa sortie
     */
    tickEnd() {
        this.output = this.tempOutput;
    }

    /***
     * Met a jour les sorties de toutes les portes passees en parametre
     * @param gates
     */
    static updateAll(gates) {

        for(let gate of gates)
            gate.update();

        for(let gate of gates)
            gate.tickEnd();
    }

    /***
     * Supprime toutes les connections a la porte gate parmis les portes allGates
     * @param gate
     * @param allGates
     */
    static removeAllConnectionsTo(gate, allGates) {

        for(let g of allGates)
            for(let i = 0; i < g.inputs.length; i++)
                if(g.inputs[i].destination === gate) {
                    g.inputs.splice(i, 1);
                    i--;
                }
    }

    //Proprietes graphiques --------------------------------------------------------------------------------------------

    x = 10;
    y = 10;
    width = 20;
    height = 20;
    color = "#379f1f";
    name = "Gate";
    fontSize = 10;
    hideName = false;

    /***
     * Modifie les proprietes graphiques de la gate
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color
     * @param name
     */
    setGraphicProperties(x, y, width, height, color, name) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.name = name;
        return this;
    }

    /***
     * Modifie la position du centre de la porte
     * @param x
     * @param y
     */
    setPosition(x, y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /***
     * Dessine la gate
     */
    draw() {

        this.drawBody();

        if(!this.hideName) {
            ctx.fillStyle = "#ffffff";
            ctx.textAlign = "center";
            ctx.textBaseline = "middle";
            ctx.font = this.fontSize + "px Arial";
            ctx.fillText(this.name, this.x, this.y);
        }
    }

    /***
     * Dessine le corps de la porte (cette methode peut etre redefinie pour une forme custom)
     */
    drawBody() {

        ctx.fillStyle = this.color;
        ctx.fillRect(this.x - this.width/2, this.y - this.height/2, this.width, this.height);
    }

    /***
     * Dessine toutes les portes passees en parametre
     * @param gates
     */
    static drawAll(gates) {

        //Dessine toutes les connections de chaque gate
        for(let gate of gates)
            for(let i = 0; i < gate.inputs.length; i++)
                gate.inputs[i].draw((i+1)/(gate.inputs.length+1));

        //Dessine toutes les gates
        for(let gate of gates)
            gate.draw();
    }
}