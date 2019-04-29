class HumanController {

    forward = false;
    right = false;
    left = false;

    constructor() {

        document.onkeydown = function(event) {

            console.log(event.keyCode);

            switch(event.keyCode) {
                case 38:
                    this.forward = true;
                    break;
                case 37:
                    this.left = true;
                    break;
                case 39:
                    this.right = true;
                    break;
            }
        };
        document.onkeyup = function(event){

            switch(event.keyCode) {
                case 38:
                    this.forward = false;
                    break;
                case 37:
                    this.left = false;
                    break;
                case 39:
                    this.right = false;
                    break;
            }

        };

    }


}


