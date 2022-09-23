##  Cours d'introduction � R
##  EPITA
##
##  Le pr�sent document est �crit en respectant la convention de R
##  concernant les commentaires.
##  Remarquer la mani�re d'�crire ces commentaires en R
##

##  1. PRISE DE CONTACT
##  1.1 Faire connaissance avec le site du CRAN
##                                         Comprehensive R Archiv Network
##
##    http://cran.r-project.org/
##
##      R est :
##            - un langage
##            - un environnement (particuli�rement adapt� aux statistiques)
##            - open source
##            - multi-plateforme
##
##  T�l�charger et installer l'application

##  TOUTE PREMIERE MANIPULATION
##  Ouvrir et fermer l'application, une premi�re fois,
##  Ouvrir l'application une deuxi�me fois,
##  Jouer de mani�re triviale avec la console
##     on peut entrer les premi�res commandes "intuitives" (1+2, etc.)


##  1.2 Un r�pertoire de travail
##
##  On va �tre amen� � travailler sur des donn�es, stock�es dans des fichiers,
##   eux m�me situ�s dans l'un de vos r�pertoire
##  En R, ce sera un "r�pertoire de travail" que vous allez d�signer d�s maintenant.
##  Cr�ez, si ce n'est d�j� fait, ce r�pertoire,
##   et d�clarez le � partir de l'application
##   � partir du menu : Fichier>Changer le r�pertoire courant
##
##  Ce r�pertoire peut contenir des donn�es ou du code R � lire directement (interpr�ter)
##
##  Remarque : Perso, j'utilise Tinn-R


##  AVANT DE DEMARRER, LES COMMANDES QUE VOUS DEVEZ CONNAITRE POUR LE PARTIEL DE 2022

## pour le chi2
qchisq(3, 0.95)  ## dans cet exemple avec 3 degr�s de libert�

## construction d'un vecteur, exemple
V<-c(2,5,7)

## moyenne
mean(V)

## coefficient de corr�lation
W<-c(9,-2,8)
cor(V,W)

## pour lire un fichier contenant un data.frame

W<-read.csv2("le nom du fichier")
W<-read.csv2("downloads/le_nom_du_fichier")  ##  faites attention aux arborescence, v�rifiez sur place l'emplacement des fichiers

## pour une r�gression lin�aire
Modele<-lm(Y~X1+X2,Tableau_Contenant_Les_Variable) ##  dans le cas courant
Modele<-lm(VecteurY~VecteurX)   ## un exemple o� les deux vecteurs ne sont pas dans un m�me tableau

##  Pour une ACP sous FactoMineR

library(FactoMineR)
Objet.ACP<-PCA(Mon.Data.Frame)   ##  faites attentions aux arguments optionnels s'il y a des variables suppl�mentaires


##  REPRISE DU COURS

##  Nous vous conseillons d'entrer des commandes qui suivent une � une et de "jouer" avec
##  Par exemple, apr�s avoir fait

a<-123456789

##  faites

a

##  et vous verrez la veur de a s'afficher
##  faites :

a+2

##  Dans le r�pertoire de travail, mettre le fichier PremierFichierCode_200225
##  Lire le source qui vient d'�tre �crit : Fichier>Sourcer du code R
##  V�rifier la valeur des variables a, b, c, h, u, v
##
##  Jouer un peu ....


##  2. LES TYPES DE BASE

##  2.1 Les nombres
##      Attention : ce sont en fait des vecteurs � une composante
##                  mais permet de se mettre rapidement en contact !
##  Affectation et premi�res op�rations sur les nombres

print(   floor(3/7) )
print(   ceiling(3/7) )
print(   round(3/7, 4) )

print(paste(   2+3, 5-2, 8*9,  2/7 ))

print(paste(  "modulo : ", 22%%4 , 22%/%7, 3^3, abs(-7), log(3), exp(2) ))

##  aussi : log10,sqrt,cos,tan,sin,...

##  UTILISATION DE L'AIDE DANS R (culture Unix)
##   jouer un peu avec l'aide, commencer par des fonctions faciles comme sin, log, ...

##  2.2 Op�rations sur les vecteurs
##      Num�rique, Caract�res, Logique (T,F)     T pour TRUE, F pour FALSE
##
##      Cr�ation :

av<-1:10
bv<-c(1,7,5,8,9)
cv<-t(bv)
dv<-seq(1,10,by=3)
print (dv[2])

ev<- "Bonjour"

fv<-c(T,T,F,T,F)     ##  Ceci est un vecteur de bool�ens

##     Valeurs r�serv�es
##     NA   NaN   Inf   -Inf   NULL

##     Op�rations usuelles
##     REMARQUE : En R, pour fabriquer un vecteur, on utilise une fonction
##       nomm�e c, de syntaxe c(a1,a2,...)
##       c signifie concatenate
##       on peut fabriquer des vecteurs de nombres, de bool�ens
##       et de cha�nes de caract�res

u1<-c(1,2,3,-1)
u2<-c(20,10,50,40)

u1+u2
u1-u2
u1*u2
u1/u2


##  Fonctions suppl�mentaires sur les vecteurs
##
##    prod, sum, max, min, range, which.min, which.max, length
##    cumsum, cumprod, cummin, cummax

##  Op�rations logiques
##   &   |   !  xor

##  2.3  Op�rations sur les matrices
##       ( Peuvent �tre � plus de 2 dimensions)
##
##     Commande : matrix(data,nrow=,ncol=,byrow)

MM<-matrix(c(1,2,3,4),nrow=2,ncol=2,byrow=T)

##    Op�rations  %*%   crossprod()   t()   diag()  det()
##    empilage : cbind, rbind

##     inverse d'une matrice : solve()    - existe aussi � deux arguments


NN<-solve(MM)

##  v�rification

MM %*% NN

##  2.4 Les dataframes
##      Structure de donn�es importantes pour traiter des donn�es statistiques

IMC<-data.frame(Sexe=c("H","F","H","F","H","F"),
                Taille=c(1.82,1.76,1.82,1.60,1.90,1.66),
                Poids=c(67,58,66,48,75,55),
            row.names=c("R�my","Laurence","Pierre","Dominique","Ben","C�cile"))
##  Afficher IMC

summary(IMC)

IMC$Poids/IMC$Taille^2

attach(IMC)   ##  D�sormais, les attributs de IMC sont devenus des variables visibles dans l'environnement R.

Poids/Taille^2

##  3. LECTURE DE DONNEES A PARTIR D'EXCEL
##     Le mieux est de lire un fichier .csv
##     Attention aux s�parateurs, nous prenons les conventions fran�aises


##  3.1 Lecture d'une matrice

Table<-read.csv2("Table_200225.csv",header=F)

##  A ce stade, Table est un data.frame dont nous n'avons pas nomm� les colonnes.

MTable<-as.matrix(Table)

print(MTable %*% t(MTable))

##  3.2 Lecture de donn�es o� les colonnes ont un nom

TCols<-read.csv2("TableCols_200225.csv")

##  3.3 Lecture de data frame avec un exercice de base

## Faire

TCols$AAA
TCols$AAA[3]<-747
TCols[2,2]<-314
TCols

TCols$CCC<-TCols$CCC+TCols$AAA
TCols$DDD<-TCols$DDD * 0.1*(1:6)
TCols

##  4. PROGRAMMATION
##     En R, il est possible d'�crire des fonctions,
##     et de les mettre dans des scripts
##     Les structures de contr�les existent,
##     nous r�f�rons � la documentation CRAN

##   Une formule simple

Poly <- function(x) x^2+1
##   Attention, pour un vrai usage de plot, consulter l'aide !

plot(Poly)

##   Une fonction qui calcule les n premiers termes de la suite de Fibonacci,
##     pour n >= 3
##   Remarque : en R les indices de vecteurs commencent � 1.

Fibo <- function(n)
{
  Dernier<-max(3,n+1)
  FF<-vector(mode = "integer", length = Dernier)
  FF[1]<-1
  FF[2]<-1

  for (k in 3:Dernier)
    FF[k]<-FF[k-1] + FF[k-2]

  return(FF)
}

## Essayer

Fibo(20)
##  La suite 3n+1
##   le nombre n doit imp�rativement �tre entier

Suite_3n_plus1 <- function (n)
{
  if ((n<=0) | floor(n) != n)  return(0)

  ##  le nombre qui progresse dans la suite 3n+1
  m<-n
  ##  le vecteur des r�sultats
  resultat<-n

  while(m !=1)
  {
   if (m %%2 ==0)
      m <- m/2
   else
      m <- 3*m+1
   resultat<-c(resultat,m)   ## remarquer ce qui se passe ici !!!!
  }
  return(resultat)
}


##  5. LES PACKAGES
##    Il sont t�l�chargeables directement sous R
##    Ils sont accessibles � partir du site du CRAN
##    Ils poss�dent une documentation en pdf !

##    ATTENTION :  DEUX ETAPES
##       Installer le package - � partir d'une ressource externe,
##     puis :
##       Charger le package pr�alablement install�,
##
##       Les packages ont syst�matiquement une doc en pdf, sur le net

##    Nous le faisons pour HistData

help(HistData)

summary(Galton)
attach(Galton)
N<-nrow(Galton)

plot(parent,child)

Zp<-0.08*rnorm(N)
Zc<-0.08*rnorm(N)
Pars<-parent+Zp
Childs<-child+Zc
plot(Pars,Childs)   ##  permet de visualiser la concentration des observations

##     ATTENTION : pour plus bas, charger aussi le package moments


##  6. EXEMPLE DE REGRESSION LINEAIRE - pour plus tard

modele<-lm(child~parent)

summary(modele)


##  Une repr�sentation graphique

boxplot(parent,child)

plot(parent,child)
abline(modele)

##  7. LES EXEMPLES DE LA PREMIERE SEANCE


##  Exemple 1 :
##  Avant de pr�senter le premier exemple, noter les diff�rents types de variables rencontr�es dans un tableau
##   variables qualitatives
##   variables quantitatives
##         ordinales
##         enti�res
##         continues

## On lit des donn�es sur des vins de la Loire

DataVins<-read.csv2("vins.csv")

## Essayer cette commande, sans modifier les valeurs des donn�es !!!

fix(DataVins)

##  Exemple 2 :
##    Tableau statistique pour des variables discr�tes ou qualitatives
##    Par exemple, on va regarder combien de vin de chaque "label" sont dans la base

Labels_Aux<-DataVins$Label
ListeLabels<-unique(Labels_Aux)
NombresCherches<-rep(0,length(ListeLabels))

for (Indice in (1:length(ListeLabels)))  NombresCherches[Indice]<-length(which(Labels_Aux==ListeLabels[Indice]))
TableauStatistiqueVins<-data.frame(Label=ListeLabels,Nombre=NombresCherches)

##  Exemple 3 :
##    Tableau statistique pour des variables quantitatives
##    Cette fois, on d�nombre le nombre d'individu dont la valeur est dans un intervalle
##    Faisons le pour la variable  Spice.before.shaking par pas de 0.25
##    A l'oeil je vois qu'on part de 1.5 jusqu'� 2.75

NotreVecteur<-DataVins$Spice.before.shaking
ValLimites<- 1.5 + 0.25*(0:5)
ValsMedianes<- ValLimites[-6]+0.125   ## je ne fais pas de d�tail
Nombres2<-rep(0,length(ValsMedianes))

for (Indice in (1:length(ValsMedianes)))  Nombres2[Indice]<-length(intersect(which(NotreVecteur>=ValLimites[Indice]),which(NotreVecteur<ValLimites[Indice+1])))
Tableau2<-data.frame(ValeursMedianes=ValsMedianes,Nombre=Nombres2)

##  Exemple 4 :
##   Deux repr�sentations graphiques sur des tableaux statistiques de variables qualitatives

##  L'irrempla�able camembert, que les anglais appellent tarte (pie) car ils ont peurs de nos germes

pie(NombresCherches,labels=ListeLabels,col=c("lightblue","pink","green"),main="VINS DE LA LOIRE")

##  Sinon, il y a aussi la repr�sentation sous forme de barres

barplot(NombresCherches,names.arg=ListeLabels,col=c("lightblue","pink","green"), legend.text="VINS DE LA LOIRE")

##  Exemple 5 :
##    Histogramme

hist(DataVins$Spice.before.shaking,breaks=5)

##  Il peut �tre utile d'avoir l'habitude de voir les histogrammes de distributions classiques

##  distribution uniforme

hist(runif(1000),breaks=50)    ## En regardant bien, cet exemple est instructif, n'est ce pas ?

hist(runif(100000),breaks=100)

##  une distribution normale

hist(rnorm(1000),breaks=50)

hist(rnorm(100000),breaks=100)   ##  Ben voyons !

##  une distribution exponentielle

hist(rexp(1000),breaks=50)

hist(rexp(100000),breaks=100)   ##  No comment !

##  Exemples 6 (au pluriel) :
##    Bo�tes � moustaches
##    Dans cet exemple, il important de voir
##      - un cas "standard"
##      - un cas avec des valeurs "sortant de l'ordinaire"
##      - le cas de donn�es provenant d'une loi exponentielle simul�e
##      - une loi � valeurs extr�mes - �ventuellement simul�e

boxplot(DataVins$Bitterness)

boxplot(DataVins[,c(-1,-2,-3)])  ## R est fait pour les statisticiens

## Boxplot d'une distribution normale

boxplot(rnorm(250))

boxplot(5+0.3*rnorm(250))

##  Boxplot d'une distribution lognormale

boxplot(exp(0.3*rnorm(250)))

## Boxplot d'une distribution exponentielle

boxplot(rexp(250))

## Cas de valeurs extr�mes avec une distribution de Cauchy

boxplot(rcauchy(250))

##  Exemple 7 : R�sum� num�rique, notamment avec la commande summary
##     A cet occasion, on rappelle, si n�cesssaire, ce qu'est une moyenne, un �cart type,
##       un quantile,  le min, le max (!)

summary(DataVins)

## En R, l'�cart type est la fonction sd

sd(DataVins$Bitterness)

##  On voit les �cart types de chaque variable num�rique

EcartsTypes<-apply(DataVins[,c(-1,-2,-3)],2,sd)

##  En compl�ment � cet exemple, asym�trie et kurtosis, sur des lois �ventuellement simul�es
##   n�cessite le package moments

Applatissements<-apply(DataVins[,c(-1,-2,-3)],2,kurtosis)
Asymetries<-apply(DataVins[,c(-1,-2,-3)],2,skewness)

##  Tant qu'� faire

Moyennes<-apply(DataVins[,c(-1,-2,-3)],2,mean)

##  On va fabriquer une synth�se

Synthese<-data.frame(colnames(DataVins[,c(-1,-2,-3)]),MOY=Moyennes,ECTYPES=EcartsTypes,ASYM=Asymetries,APPLAT=Applatissements)
write.csv2(Synthese,"SyntheseVins.csv")

##  Exemple 8 : un peu � part, comment reconna�tre � vue qu'un �chantillon suit une loi normale avec le qqplot

qqnorm(DataVins$Bitterness)
qqnorm(rnorm(250))
qqnorm(rexp(250))
qqnorm(exp(0.5*rnorm(250)))
qqnorm(rcauchy(250))



