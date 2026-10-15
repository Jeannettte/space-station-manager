# Space-station-manager

Jeu sur une station spatiale avec un équipage limité et des ressources critiques (oxygène, énergie, eau, nourriture). Chaque module consomme ou produit des ressources et chaque membre d'équipage a un role. 

* Plan de travail, dans l'ordre : 

### Étape 0 : Mise en place 
1. Créer le dépôt GitHub et le cloner.
2. Créer un projet Maven dans le `pom.xml`.
J'ai choisi Maven, car c'est un outil de gestion de projet et de construction. Il offre une gestion centralisée de la configuration et des dépendence.
3. Créer les packages vides : `model`, `service`, `loader`, `exception`, `ui`.
4. Ajouter un `.gitignore` 
5. Faire un premier commit.

### Étape 1 : Les fondations sans dépendances
1. Enums : moyen efficace de structurer et de gérer les constantes : `ResourceType`, `ModuleStatus`, `Role`, `Severity`
2. Exceptions : `InsufficientResourceException`, `CrewAssignmentException`, `ModuleOfflineException`, `InvalidDataException`, `StationLostException`
3. `ResourceStock` + `ResourceStockTest` : c'est la première vraie classe métier. Teste tout de suite l'ajout, la consommation, le dépassement de capacité et le stock insuffisant.

### Étape 2 : Les modules
1. Interfaces `Repairable`, `ResourceProducer`, `ResourceConsumer`
2. `Module` (abstraite) avec intégrité, statut, `applyDamage()` et `repair()`
3. **Une seule** sous-classe d'abord : `SolarPanelArray`
4. `ModuleTest` : intégrité bornée entre 0 et 100, passage en statut `OFFLINE` à 0, production selon l'état
5. Une fois le modèle validé, ajouter `OxygenGenerator`, `WaterRecycler`, `Greenhouse`, `Laboratory`, chacun avec son test.

## Étape 3 : L'équipage (jours 7-9)
1. Interface `Treatable`
2. `CrewMember` (abstraite) avec santé, moral et module affecté
3. `Engineer` d'abord, puis `Doctor`, `Scientist`, `Commander`
4. Tests : la santé reste bornée, un membre mort ne peut plus travailler, l'ingénieur répare la bonne quantité.

## Étape 4 : La station (jour 10)
1. `Station` : agrège la liste de modules, l'équipage, le `ResourceStock` et le jour courant
2. Garde-la **simple** : pas de logique complexe, juste des getters utiles et des méthodes comme `getAliveCrew()` ou `getActiveModules()`.

## Étape 5 : Les événements (jours 11-12)
1. Interface `StationEvent` avec `apply(Station)`
2. `MeteorShower` en premier, puis `SolarStorm`, `EquipmentFailure`, `Epidemic`
3. `EventGenerator` qui reçoit un `Random` en paramètre
4. Tests avec un `Random` à seed fixe pour avoir des résultats prévisibles.

## Étape 6 : Les services métier (jours 13-17)
C'est le cœur du projet, prends ton temps.
1. `CrewAssignmentService` : affecter un membre, détecter les conflits. Tests sur chaque cas d'erreur.
2. `ResourceManager` : bilan de production et consommation d'un tour, avec coupure des modules par priorité si l'énergie manque. C'est la partie la plus délicate, teste-la bien.
3. `ScoreCalculator`
4. `TurnSimulator` : orchestre un jour complet (événement, production, consommation, conditions de défaite).

## Étape 7 : Le chargement des données (jours 18-20)
1. Écrire les fichiers `modules.csv`, `crew.csv`, `events.csv` dans `resources/`
2. Interface générique `DataLoader<T>`
3. `ModuleFactory` : convertit un type en chaîne (`"SOLAR"`) vers la bonne sous-classe
4. `CsvModuleLoader`, `CsvCrewLoader`, `CsvEventLoader`
5. Tests : fichier valide, fichier vide, ligne malformée, type inconnu (doit lever `InvalidDataException`).

## Étape 8 : L'interface console et le démarrage (jours 21-24)
1. `ConsoleView` : uniquement de l'affichage (état de la station, menus). Aucune logique métier.
2. `GameController` : lit les choix du joueur et appelle les services.
3. `Application.java` : quelques lignes seulement (charger, construire la station, lancer le contrôleur).
4. Jouer plusieurs parties complètes pour repérer les bugs et les déséquilibres.

## Étape 9 : Finition (jours 25-31)
1. **Relecture du code** : doublons, noms peu clairs, commentaires qui expliquent le *pourquoi*, catch vides.
2. **Compléter les tests** : vérifier que chaque catégorie est couverte (normal, limites, erreurs).
3. **README** : suis la liste du sujet point par point. Écris-le au fur et à mesure plutôt que le dernier soir.
4. **Vérification finale** : clone le dépôt dans un dossier neuf, compile, lance et exécute les tests, pour confirmer que tout fonctionne hors de ta machine.
5. Prévois 2 ou 3 jours de marge avant le 15/11.)