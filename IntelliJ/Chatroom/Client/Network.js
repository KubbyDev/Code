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
                    nickname: Network.localUser.nickname
                    //picture: btoa(JSON.stringify(Network.localUser.picture))
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
                new User(
                    message.nickname
                    //atob(message.picture))
            )));
    }
}

//On recupere les nouveaux messages toutes les secondes
setInterval(Network.getMessages, 1000);