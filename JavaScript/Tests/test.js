const carre = (x) => x*x;

//Applique une fonction a chaque element d'une liste
function apply(list, func) {
    for(let i = 0; i < list.length; i++)
        list[i] = func(list[i]);
}

//Affiche une liste
function disp(list) {
    for(const e of list)
        console.log(e);
}

let liste = [3, 4, 5];

apply(liste, carre);

disp(liste);