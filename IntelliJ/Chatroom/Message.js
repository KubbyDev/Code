class Message {

    static LINE_SPACING = 0;
    static FONT_SIZE = 15;

    text = "";
    user = User.default;

    constructor(text, user) {

        if(!text)
            return;

        this.text = text;

        if(!user)
            return;

        this.user = user;
    }

    /**
     * Calcule le nombre de pixels que le message prend en hauteur en fonction de la largeur d'affichage
     * @param width
     */
    getHeight(width) { //TODO: Faire un math.max avec la hauteur de l'image
        return Math.ceil(Message.FONT_SIZE * 0.55 * this.text.length / width) * (Message.LINE_SPACING + Message.FONT_SIZE);
    }

    /**
     * Affiche le message en revenant a la ligne si besoin (la position x y est le coin haut gauche, w est le nombre de pixels de large)
     * @param ctx
     * @param x
     * @param y
     * @param w
     */
    draw(ctx, x, y, w) {

        ctx.fillStyle = "#000000";
        ctx.font = Message.FONT_SIZE + "px monospace";

        let words = this.text.split(' ');

        let charsNumber = w/(0.55*Message.FONT_SIZE)-1; //Nombre de caracteres affichables sur une ligne
        let totalWidth = 0;
        let wordIndex = 0;
        let lineIndex = 0;

        //Pour chaque mot
        while(wordIndex < words.length) {

            //Si on a pas depasse la longueur max
            if(totalWidth + words[wordIndex].length <= charsNumber) {

                //On affiche le mot et on passe au suivant
                ctx.fillText(
                    words[wordIndex] + " ",
                    totalWidth * Message.FONT_SIZE * 0.55 + x,
                    lineIndex * (Message.LINE_SPACING + Message.FONT_SIZE) + y
                );
                totalWidth += words[wordIndex].length +1;
                wordIndex++;
            }
            //Si on a depasse la longueur max
            else {

                //Si on a un mot trop long pour la ligne
                if(totalWidth === 0) {

                    //On le casse
                    let longWord = words[wordIndex];
                    words[wordIndex] = longWord.substr(0, charsNumber);
                    words.splice(wordIndex+1, 0, longWord.substr(charsNumber));
                }
                else {

                    //On passe a la ligne suivante
                    lineIndex++;
                    totalWidth = 0;
                }
            }
        }
    }
}