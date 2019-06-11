const messages = [];

const express = require('express');
const app = express();
app.listen(3000, () => console.log("listening"));
app.use(express.static('Client'));  //Le serveur envoie le fichier Client/index.html
app.use(express.json({}));

//Reception des messages
app.post('/messages', (request, response) => {

    messages.push(request.body);
    console.log("received message: " + JSON.stringify(request.body));
    response.json({
        errors: 'None'
    });

});

//TODO Optimiser les transfert (eviter d'envoyer 200 fois les images)

//Envoi des messages
app.get('/messages', (resquest, response) => {

    response.json(messages);

});
