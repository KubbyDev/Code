class Network {

    static localUser = User.default;
    static #ignoreResponses = false; //Passe a true juste apres envoi d'un message

    static async send(text) {

        Network.#ignoreResponses = true;

        Displayer.addMessage(new Message(text, Network.localUser));

        let options = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                    text: text,
                    nickname: Network.localUser.nickname,
                    picture: Network.localUser.picture64,
                })
        };
        console.log(options);
        inputField.value = "";

        const rawResponse = await fetch('/messages', options);
        const response = await rawResponse.json();

        console.log(response);
        Network.#ignoreResponses = false;
    }

    static async getMessages() {

        const rawResponse = await fetch('/messages');
        const messages = await rawResponse.json();

        console.log(messages);

        Displayer.messages = messages.reverse().map(message =>
            new Message(
                message.text,
                User.fromBase64(
                    message.nickname,
                    message.picture
            )));
    }

    static changePicture() {

        if(imageField.files === undefined)
            return;

        Network.localUser.picture.src = URL.createObjectURL(imageField.files[0]);
        Network.localUser.calculateBase64();
    }

    static changeNickname() {

        if(nicknameField.value === "")
            return;

        Network.localUser.nickname = nicknameField.value;
    }
}

//On recupere les images que l'utilisateur upload comme image de profil
imageField.addEventListener('change', Network.changePicture);
nicknameField.addEventListener('change', Network.changeNickname);

//On recupere les nouveaux messages toutes les secondes
setInterval(Network.getMessages, 1000);