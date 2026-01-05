# Repertoire UICI

Application Android de gestion de contacts développée en Kotlin.

## Fonctionnalités

- Écran de splash avec logo au démarrage
- Enregistrement de contacts avec :
  - Nom et prénoms
  - Numéro de téléphone
  - Adresse email
  - Lieu d'habitation
  - Coordonnées GPS (latitude/longitude)
- Affichage de la liste des contacts
- Actions disponibles pour chaque contact :
  - Appeler le contact
  - Envoyer un SMS
  - Voir la localisation sur une carte
  - Supprimer le contact
- Interface Material Design moderne et intuitive
- Persistance des données avec SharedPreferences

## Technologies utilisées

- Kotlin
- Android SDK (API 24+)
- Material Components
- RecyclerView
- ViewBinding
- Gson pour la sérialisation JSON

## Structure du projet

```
app/
├── src/
│   └── main/
│       ├── java/com/uici/repertoire/
│       │   ├── Contact.kt                  # Modèle de données
│       │   ├── ContactManager.kt           # Gestionnaire de persistance
│       │   ├── ContactAdapter.kt           # Adaptateur RecyclerView
│       │   ├── SplashActivity.kt           # Écran de démarrage
│       │   ├── MainActivity.kt             # Liste des contacts
│       │   └── AddContactActivity.kt       # Ajout de contact
│       └── res/
│           ├── layout/
│           │   ├── activity_splash.xml
│           │   ├── activity_main.xml
│           │   ├── activity_add_contact.xml
│           │   └── item_contact.xml
│           └── values/
│               ├── colors.xml
│               ├── strings.xml
│               └── themes.xml
```

## Permissions requises

- `CALL_PHONE` : Pour passer des appels
- `SEND_SMS` : Pour envoyer des SMS
- `INTERNET` : Pour accéder aux cartes
- `ACCESS_FINE_LOCATION` et `ACCESS_COARSE_LOCATION` : Pour la localisation

## Installation

1. Cloner le projet
2. Ouvrir dans Android Studio
3. Synchroniser Gradle
4. Lancer l'application sur un émulateur ou appareil physique

## Utilisation

1. Au lancement, l'écran de splash s'affiche pendant 3 secondes
2. La liste des contacts s'affiche (vide au premier lancement)
3. Appuyer sur le bouton flottant "+" pour ajouter un contact
4. Remplir les informations du contact (nom, prénoms et téléphone obligatoires)
5. Les contacts apparaissent dans la liste avec des boutons d'action
6. Utiliser les boutons pour appeler, envoyer un SMS, voir la localisation ou supprimer un contact

## Auteur

Développé pour l'UICI
