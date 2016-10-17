# MoteurInferenceZeroPlus

Lundi 24/10 
version finie qui tourne avec un readme qui explique la syntaxe 
+ commande pour lancer le prg ofc 




les algos de parcours retournent une chaine de caracter contenant les états de la base de fait à chaque tour de boucle ; possible de lancer l'algo meme sans recup le resultat ?

le but n'est pas une proposition mais une chaine de caracteres représentant la variable à rechercher
du coup changer l'algo (condition d'arret lorsque la bf contient le type de la variable a rechercher, et y'a des regles qui merdent du coup)

tester un autre parcours de map



http://www-igm.univ-mlv.fr/~dr/XPOSE2004/jvaldes/fonctionnement.html

chainage avant :    - satisfaction d'un but donné               variable
                    - epuisement des regles
                    - saturation de la base de faits ?
chainage arriere :  - remonte recursivement vers les faits initiaux à partir d'un but donné

Dans les deux cas le system peut poser des questions à l'utilsiateur pour tente de compléter la base de faits initiale

https://www.u-picardie.fr/~furst/docs/Fonctionnement_MI.pdf P 17
Gérer les conflits
    Stratégie fixe
        La premiere
        La plus « précise »
        La plus « récente »



Chainage avant en largeur :

BF = [...] // la base de faits contient des propositions (ex. : action = courir)
Boucle
    les regles qui ont leur prémisses en commun avec la BF -> mettre de coté la conclusion de la regle
    quand toutes les regles sont parcourues, les nouvelles propositions sont ajoutées à la BF
Repeter tant qu'il existe des règles ou si un but a été atteint
