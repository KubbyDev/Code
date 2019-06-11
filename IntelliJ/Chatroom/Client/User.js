class User {

    nickname;
    picture;               //Une ImageHTMLElement
    picture64 = "";        //Encodage base64 de l'image

    constructor(nickname, picture) {

        this.picture = new Image();

        //Pseudo
        if(!nickname)
            return;
        this.nickname = nickname;

        //Image de profil
        if(!picture)
            return;
        this.picture.src = picture;

        //Si l'image est definie on calcule directement l'encodage base64
        this.calculateBase64();
    }

    /**
     * Returns a User with "Anonymous" as nickname and the default picture
     * @returns {User}
     */
    static get default() {

        let user = new User("Anonymous", User.defaultPicture);
        user.calculateBase64();

        return user;
    }

    static get defaultPicture() {
        return "Anonymous.png";
    }

    calculateBase64() {

        (callback => {

            this.picture.onload = function () {

                //Cree un canvas temporaire pour l'encodage
                let tempCanvas = document.createElement('CANVAS');
                tempCanvas.height = this.naturalHeight;
                tempCanvas.width = this.naturalWidth;
                tempCanvas.getContext('2d').drawImage(this, 0, 0);

                callback(tempCanvas.toDataURL());

                tempCanvas = null; //Evite d'avoir des residus
            }
        })
        //Execution de la fonction qu'on vient de definir
        ( image64 => { this.picture64 = image64; } );

    }

    static fromBase64(nickname, imageEncoding) {

        let user = new User(nickname);
        user.picture.src = imageEncoding;
        user.picture64 = imageEncoding;
        return user;
    }
}