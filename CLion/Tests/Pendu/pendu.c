#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <ctype.h>
#include "pendu.h"

#define NB_TRIES 10

int main() {

    //Generation du mot mystere
    char* word = generateWord();
    if (word == NULL)
        return 1;

    //Generation du mot a afficher a l'utilisateur
    unsigned int len = strlen(word);
    char* info = malloc((len+1)*sizeof(char));
    for(unsigned int i = 0; i < len; i++)
        info[i] = '*';
    info[len] = '\0';

    int triesLeft = NB_TRIES;
    char guess = 0;
    do {

        printf("%s\nIl vous reste %d essais.\nLettre: ", info, triesLeft);
        guess = readChar();

        //Remplace les * par la lettre trouvee dans info. Renvoie 0 si aucune * n'est remplacee
        if(replaceChars(info, word, guess, len) == 0)
            triesLeft--;

    } while(triesLeft > 0 && strchr(info, '*') != NULL); //Continue tant qu'il reste des essais et des lettres inconnues

    //Fin de partie
    if(triesLeft > 0)
        printf("Bravo! Vous avez trouvé le mot mystère! %s", word);
    else
        printf("Perdu ! Le mot mystère était %s", word);

    return 0;
}

int replaceChars(char* info, const char* word, char guess, unsigned int length) {

    int success = 0;

    for(unsigned int i = 0; i < length; i++) {

        //Si le charactere est celui que l'utilisateur a entre on le remplace
        if(word[i] == guess) {
            info[i] = guess;
            success = 1;
        }
    }

    return success;
}

char readChar()
{
    char caractere = 0;
    caractere = getchar();
    caractere = toupper(caractere);

    // On lit les autres caractères memorises un a un jusqu'au \n (pour les effacer)
    while (getchar() != '\n') ;

    return caractere;
}

int rnd(int min, int max) {
    srand(time(NULL));
    return (rand() % (max - min + 1)) + min;
}

char* generateWord() {

    FILE* file = fopen("Pendu/words.txt", "r");
    if(file == NULL) {
        printf("Could not find words.txt in folder \"Pendu\"");
        return NULL;
    }

    //Compte le nombre de lignes
    int nbLines = -1;
    char *trashBuffer = malloc(21*sizeof(char));
    while(fgets(trashBuffer, 20, file) != NULL)
        nbLines++;
    rewind(file);

    //Choisit un mot aleatoire
    int index = rnd(0,nbLines-1);

    //Deplace le curseur sur le mot choisi
    while(index > 0) {
        int current = fgetc(file); //Lis le caractere suivant et avance de 1
        if(current == 10) //retour a la ligne
            index--;
    }

    //Lit le mot choisi
    char* selected = malloc(21*sizeof(char));
    fgets(selected, 20, file);
    //Supprime le retour a la ligne
    selected[strlen(selected)-1] = '\0';
    //Passe en uppercase
    unsigned int len = strlen(selected);
    for(unsigned int i = 0; i < len; i++)
        selected[i] = toupper(selected[i]);

    fclose(file);

    return strupr(selected);
}