class Displayer {

    static MESSAGE_SPACING = 15;

    static x; //Coin haut gauche position x
    static y; //Coin haut gauche position y
    static width; //Largeur
    static height; //Hauteur
    static messages = [];
    static #initialised = false;

    /**
     * Cree un displayer vide et pret a recevoir des messages
     * @param x
     * @param y
     * @param width
     * @param height
     */
    static init(x, y, width, height) {

        //Si l'init s'est deja faite on annule
        if(Displayer.#initialised) {
            console.error("Init: The initialisation has already been done !");
            return;
        }

        //Si les parametres sont pas bons on annule
        if(!x || !y || !width || !height || width < 1 || height < 1) {
            console.error("Init: You must choose a position and a size for your Displayer !");
            return;
        }

        Displayer.x = x;
        Displayer.y = y;
        Displayer.width = width;
        Displayer.height = height;
        Displayer.#initialised = true;
    }

    /**
     * Ajoute un message au displayer
     * @param message
     */
    static addMessage(message) {

        if( ! Displayer.#initialised) {
            console.error("addMessage: You must initialise the Displayer !");
            return;
        }

        if( ! (message instanceof Message)) {
            console.error("addMessage: You must pass in a Message object !");
            return;
        }

        Displayer.messages.unshift(message);
    }

    /**
     * Met a jour l'affichage en fonction des messages presents a ce moment
     * @param ctx: canvas 2D context
     */
    static update(ctx) {  //TODO: Et de la position de la scrollbar.

        //Fond de l'ecran des messages
        ctx.fillStyle = "#e0e0e0";
        ctx.fillRect(Displayer.x, Displayer.y, Displayer.width, Displayer.height);

        //Messages
        let writeHeight = Displayer.y + Displayer.height;
        for(let message of Displayer.messages) {

            //Coupe le message en revenant a la ligne quand il le faut
            let lines = message.cutInLines(Displayer.width - Message.TEXT_LEFT_OFFSET);

            //Calcule la place que va prendre le message en hauteur
            writeHeight -= Displayer.MESSAGE_SPACING + message.getHeight(lines);

            if(writeHeight < 0)
                break;

            //Affiche le message
            message.draw(lines, ctx, Displayer.x, writeHeight);
        }
    }
}