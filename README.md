# MyGES

Application Android native développée avec **Jetpack Compose** pour les étudiants du Campus Eductive d'Aix-en-Provence. Elle centralise les services essentiels via l'API [Kordis](https://github.com/Melv1no/myges-api).

---

## Fonctionnalités

| Écran | Description |
|-------|-------------|
| **Connexion** | Authentification OAuth 2.0 avec persistance du token |
| **Agenda** | Emploi du temps par semaine, navigation ← → |
| **Notes** | Notes groupées par semestre et triées par matière |
| **Actualités** | Fil d'actualités paginé |
| **Profil** | Informations étudiant et déconnexion |

---

## Architecture

Clean Architecture + MVVM, organisée en trois couches strictement séparées :

```
app/
├── core/
│   ├── data/
│   │   ├── remote/          # Retrofit, DTOs, AuthInterceptor
│   │   └── repository/      # Implémentations des repositories
│   ├── domain/
│   │   ├── model/           # Modèles de domaine
│   │   ├── repository/      # Interfaces
│   │   └── usecase/         # Logique métier
│   └── di/                  # Modules Hilt
├── ui/
│   ├── theme/               # Material3 (Color, Type, Shape, Theme)
│   ├── components/          # LoadingContent, ErrorContent, EmptyContent
│   ├── navigation/          # Routes, NavGraph, BottomNavItem
│   └── [feature]/           # Screen + ViewModel + UiState par écran
└── util/                    # AgendaRangeCalculator, TokenManager, AcademicYearCalculator
```

---

## Stack technique

| Technologie | Rôle |
|-------------|------|
| Kotlin | Langage principal |
| Jetpack Compose + Material3 | UI déclarative |
| Navigation Compose | Navigation entre écrans |
| Hilt | Injection de dépendances |
| Retrofit + OkHttp | Appels réseau REST |
| Coroutines + StateFlow / SharedFlow | Asynchronisme et réactivité |
| Gson | Désérialisation JSON |
| ViewModel + Lifecycle | Gestion du cycle de vie |

---

## Bonnes pratiques

- **Zéro hardcoding** — toutes les chaînes dans `strings.xml`, toutes les couleurs via `MaterialTheme.colorScheme`, toutes les routes dans `Routes.kt`
- **Événements one-shot** — `SharedFlow(replay=0)` pour la navigation post-login et le logout, évitant les redirect loops
- **Scroll** — `pinnedScrollBehavior` + `nestedScroll` sur chaque écran, la TopAppBar gère son fond automatiquement
- **Listes** — `key` sur tous les `items()` de `LazyColumn` pour limiter les recompositions
- **Objets coûteux** — formatters de dates et calculs encapsulés dans `remember { }`
- **Sécurité** — `HttpLoggingInterceptor` actif uniquement en `DEBUG`, token supprimé automatiquement sur 401
- **Navigation bar** — pill animée avec `weight` dynamique (1.8f sélectionné / 1f non sélectionné) pour éviter la troncature des labels

---

## Internationalisation

L'application supporte le **français** et l'**anglais**. La langue suit le paramètre système de l'appareil.

```
res/
├── values/strings.xml       # Anglais (défaut)
└── values-fr/strings.xml    # Français
```

---

## Prérequis

- Android Studio Hedgehog ou supérieur
- SDK Android 34+
- Compte étudiant GES (Campus Eductive)

## Installation

```bash
git clone https://github.com/<votre-repo>/myges.git
```

Ouvrir le projet dans Android Studio, synchroniser Gradle et lancer sur un émulateur ou appareil physique (API 34 minimum).

---

## Auteur

Projet réalisé dans le cadre du **Mini-Projet Jetpack Compose — 4ESGI AIX**.
