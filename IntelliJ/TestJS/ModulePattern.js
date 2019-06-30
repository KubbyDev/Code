var Animal = (function () {

    var health = 20;

    function eat(pts) {
        health += pts
    }

    function giveToEat(thing) {
        eat(thing);
        say("eating " + thing);
    }

    function say(thing) {
        console.log(thing);
    }

    return {
        giveToEat: giveToEat,
        say: say
    };
})();

Animal.giveToEat(20);
Animal.say("yay");
