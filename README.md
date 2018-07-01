# Journal App

Hi! I'm Obella Isaac **@Isaac (ad-team-108)**. This is a brief preview of my code base and project for the Journal App.
Apk can be got from [here](https://github.com/wizlif/Journal-App/blob/master/screenshots/app-debug.apk?raw=true)


# Files
I used Dagger 2 to manage my dependencies hence  a file structure as
```
+-- data
+-- di
+-- ui
+-- utils
+-- JournalApp.java
+-- ViewModelProvideFactory.java
```

 - **data**: contains models,enums and shared preference managers for the app. 
 - **di**: contains dependency injection setup for the app including activity and fragment generators and dependency providers.
 - **ui**: This contains the presenters, activities, modules, components and fragments distributed into separate packages.
 - **utils**: This consists of commonly used packages and binding adapters.
 - **JournalApp.java**: This works as my application base, initializing different dependencies.

## UI, UX and flow
The entire app consists of about 5 Activities and 6 Fragments.
> Authentication: Consists of Login and Signup pages that can either use email and password(After creating account) or google signin

| Login  | Signup |
| ------------- | ------------- |
|![alt text](https://raw.githubusercontent.com/wizlif/Journal-App/master/screenshots/Screenshot_20180702-011938.png)  | ![alt text](https://raw.githubusercontent.com/wizlif/Journal-App/master/screenshots/Screenshot_20180702-011948.png)  |

> Main Page consists of a single page that has cards containing months for which you can select date for your journal.

|Month   | Calendar |
| ----------| --------|
|![alt text](https://raw.githubusercontent.com/wizlif/Journal-App/master/screenshots/Screenshot_20180702-011253.png) | ![alt text](https://raw.githubusercontent.com/wizlif/Journal-App/master/screenshots/Screenshot_20180702-011259.png) |

> The pencil floating action button can be used to create a new note while the calendar icon can be used to flip between calendar and month views.

> To **create a new** journal item, click the pencil icon at the bottom, that will launch a new page for you to create the note. After making changes, hit save to save the note.

| New Note | Select Weather |
| ----------- | --------------|
| ![alt text](https://raw.githubusercontent.com/wizlif/Journal-App/master/screenshots/Screenshot_20180702-011311.png) | ![alt text](https://raw.githubusercontent.com/wizlif/Journal-App/master/screenshots/Screenshot_20180702-011324.png) |

> Notes can be viewed by selecting any of the month cards.
Select a note option to view it's details.

| Notes | Note Preview |
| ---------- | ---------- |
| ![alt text](https://raw.githubusercontent.com/wizlif/Journal-App/master/screenshots/Screenshot_20180702-011247.png) | ![alt text](https://raw.githubusercontent.com/wizlif/Journal-App/master/screenshots/Screenshot_20180702-011347.png) |

## Databasing

For storage and authentication, i used Firebase Realtime database and implemented data persistence using
`firebaseDatabase.setPersistenceEnabled(true);` which enables the app to persist data even after restarts and restore data on new device.

## Challenges
* Keeping up with deadline
* Writing Tests
