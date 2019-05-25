
class NetworkDrawer {

    /***
     * Dessine le reseau. topLeftCorner est le coin haut gauche du dessin
     * @param network
     * @param width
     * @param height
     * @param topLeftCorner
     */
    static draw(network, width, height, topLeftCorner) {

        if(!topLeftCorner)
            topLeftCorner = Vector.zero;

        //x: entre -1 et 1: -1 => rouge, 1 => bleu
        function redBlueGradient(x) {

            //Clamp x entre -1 et 1
            x = Math.max(-1, Math.min(x, 1));

            let r = (x+1)*127.5;
            let g = (1-Math.abs(x))*127;
            let b = (-x+1)*127.5;

            let str_r = Math.round(r).toString(16).padStart(2, '0');
            let str_g = Math.round(g).toString(16).padStart(2, '0');
            let str_b = Math.round(b).toString(16).padStart(2, '0');

            return '#' + str_r + str_g + str_b;
        }

        //Dessine une connection
        function drawConnection(from, to, weight) {
            ctx.beginPath();
            ctx.strokeStyle = redBlueGradient(weight);
            ctx.moveTo(from.x, from.y);
            ctx.lineTo(to.x, to.y);
            ctx.stroke();
            ctx.closePath();
        }

        function drawNeuron(center, biais) {

        }

        //Calcul de la longueur de la plus longue couche
        let maxNeurons = network.inputs.length;
        for(let layer of network.layers)
            maxNeurons = Math.max(layer.length, maxNeurons);

        //Calcul des positions de chaque neurone (inclus aussi les inputs)
        let positions = Array(network.layers.length+1);
        for(let layerID = 0; layerID < network.layers.length+1; layerID++) {

            let neuronsNumber = layerID === 0 ? network.inputs.length : network.layers[layerID-1].length;
            positions[layerID] = Array(neuronsNumber);
            for(let neuronID = 0; neuronID < neuronsNumber; neuronID++)
                positions[layerID][neuronID] = new Vector(
                    ( (layerID+1)/(network.layers.length+2) )*width,
                    ( (neuronID+1)/(maxNeurons+2)           )*height,
                );
        }

        //Dessine toutes les connections
        for(let layer of network.layers)
            for(let neuron of layer)
                for(let connection of neuron.connections)
                    drawConnection(
                        positions[neuron.layer][neuron.id],
                        positions[connection.target.layer][connection.target.id],
                        connection.weight
                    )
    }
}