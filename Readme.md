### 🥗 Projektname
NutriFlow: Die intelligente Lebensmittel- & RAG-Engine

👥 Team

Mitglieder: Beatrice Henning

### ⚡ Kurzbeschreibung

Genug von Rezept-Apps, die bei ausgefallenen Lebensmitteln streiken? NutriFlow bringt Leben in deine Küche, indem es fehlende Datenbankeinträge mithilfe eines autonomen RAG-Agenten in sofortige, KI-gestützte Nährwertangaben verwandelt. Das ist nicht nur ein Rezept-Tracker – es ist eine produktionstaugliche, sich selbst anreichernde Daten-Engine, entwickelt, um deine Ernährung gesund und dein System felsenfest zu halten!

### 📖 Die ganze Geschichte

- Das Problem

    Herkömmliche Rezept- und Fitness-Anwendungen basieren stark auf statischen Datenbanken. Wenn ein Benutzer ein ungewöhnliches, regionales oder sehr spezifisches Lebensmittel eingibt (wie „Sanddorn“ oder „Teff-Mehl“), zeigt das System keine Nährwerte an. Das stört das Nutzererlebnis und hinterlässt Lücken im Rezept- und Ernährungs-Tracking.

- Die Lösung

    NutriFlow schließt diese Lücke, indem es eine standardmäßige Java Spring- & React-Webanwendung in ein intelligentes, selbstlernendes Ökosystem verwandelt.

    - Der Ablauf:

        Wenn ein Benutzer ein Rezept oder ein Lebensmittel aufruft, prüft das Java Spring-Backend unsere primäre MongoDB. Fehlt das Lebensmittel, gibt das System nicht auf. Stattdessen triggert es unseren containerisierten Python-ML-Service.

        - Die Intelligenz:

            Der Python-Service steuert einen intelligenten Agenten. Dieser Agent durchsucht zuerst eine lokale Vektordatenbank (RAG) mit offiziellen Nährwertrichtlinien. Ist die Information dort nicht enthalten, durchsucht der Agent das Web autonom über eine kostenfreie LLM-API, um das Nährwertprofil zu extrahieren, zu strukturieren und zu validieren.

        - Der Kreislauf:

            Die strukturierten Daten werden über saubere REST-APIs an das Java-Backend zurückgegeben, für zukünftige Nutzer in MongoDB gespeichert und sofort im React-Frontend angezeigt.

### 🏗️ Projektumriss

- Modell & Ansatz

    - RAG-System:

        Eine Vektordatenbank mit hochwertigen Ernährungs- und Nährwolldaten.

    - Autonomer Agent:

        Erstellt mit LangChain (oder LangGraph). Er bewertet, ob die Vektordatenbank die Anfrage beantworten kann oder ob ein Web-Such-Fallback erforderlich ist.
    - Inferenz:

        Nutzung kostenloser/Free-Tier-LLM-Endpunkte (z. B. Groq mit Llama-3 oder Hugging Face APIs) zum Parsen, Strukturieren und Ausgeben sauberer JSON-Daten, validiert durch Pydantic.
- Datenressourcen
    - USDA FoodData Central / Open Food Facts:

        Dienen als Basis-Ground-Truth-Datensatz zur Befüllung unseres RAG-Vektorspeichers.
    - Agenten-Websuche:

        DuckDuckGo-API oder ähnliche kostenfreie Such-Tools für den Echtzeit-Internet-Fallback.

### 🏗️ Architektur & Technologie-Stack

Das Projekt gliedert sich in drei Hauptkomponenten, die durch eine robuste CI/CD-Pipeline automatisiert auf Render deployed werden:
1. Backend (Java Spring Boot)

    Kern: REST-API zur Verwaltung von Rezepten, Benutzern und Nährwerten.

    Datenbank: MongoDB für persistente Speicherung.

    Qualität: Einsatz von Sonar zur statischen Code-Analyse und Einhaltung von Clean-Code-Prinzipien.

2. Frontend (React)

    Technologie: Vite + TypeScript.

    Fokus: Intuitive Benutzeroberfläche zur Rezeptverwaltung, Wochenplanung und Shopping-Listen-Generierung.

3. ML-Service (Python FastAPI)

    KI-Agent: LangChain/LangGraph-Agent für autonomes Web-Browsing (Fallback) und RAG-gestützte Recherche.

    Validierung: Pydantic für strikte Datenschemata.

    Produktion: Containerisiert via Docker für nahtlose Integration in die Pipeline.

4. DevOps & CI/CD

    Automatisierung: GitHub Actions Workflows für Build, Test (Unit & Integration) und Linting.

    Code-Qualität: Ruff & Black für Python, Sonar-Integration für Java.

    Deployment: Container-gestütztes Deployment auf Render.

    Monitoring: Prometheus & Grafana für Echtzeit-Metriken (Latenz, Fehler) sowie Evidently AI zur Erkennung von Daten-Drift.

📂 Projektstruktur grobe Übersicht
```text
EatYouFit/
├── .github/workflows/    # CI/CD (Maven, Sonar, Deployment)
├── backend/              # Java Spring Boot & MongoDB Integration
├── frontend/             # React + Vite UI
├── ml-service/           # FastAPI, LangChain & Agenten-Logik
├── sonar-project.properties
├── Dockerfile            # Container-Setup
└── ...
```
📖 Der Prozess: Wie NutriFlow lernt

    Anfrage: Der Nutzer fragt ein Lebensmittel an.

    Prüfung: Das Java-Backend sucht in der lokalen MongoDB.

    Fallback (bei Bedarf): Falls das Lebensmittel unbekannt ist, sendet das Backend einen Request an den ml-service.

    Autonome Recherche: Der ML-Agent nutzt RAG für offizielle Daten oder recherchiert im Web.

    Validierung & Speicherung: Die Ergebnisse werden validiert, als JSON zurückgegeben und in der MongoDB für die Zukunft gespeichert.

### 📂 Projektstruktur
```text
EatYouFit/
├── .github/
│   └── workflows/
│       ├── deploy.yml
│       ├── maven.yml
│       ├── sonarbuild-backend.yml
│       └── sonarbuild-frontend.yml
├── .idea/
├── .vscode/
├── backend/
│   ├── src/main/java/org/bea/backend/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── enums/
│   │   ├── exception/
│   │   ├── mapper/
│   │   ├── model/
│   │   │   ├── Ingredient.java
│   │   │   ├── IngredientCreate.java
│   │   │   ├── IngredientDto.java
│   │   │   ├── IngredientProfile.java
│   │   │   ├── Job.java
│   │   │   ├── Nutrient.java
│   │   │   ├── Nutrients.java
│   │   │   ├── Recipe.java
│   │   │   ├── RecipeDto.java
│   │   │   ├── RecipeIngredient.java
│   │   │   ├── User.java
│   │   │   ├── UserDto.java
│   │   │   └── UserUpdateDto.java
│   │   ├── openai/
│   │   ├── repository/
│   │   │   ├── IngredientRepository.java
│   │   │   ├── NutrientsRepository.java
│   │   │   ├── RecipeRepository.java
│   │   │   └── UserReprository.java
│   │   ├── security/
│   │   ├── service/
│   │   ├── utils/
│   │   └── BackendApplication.java
│   ├── src/test/
│   ├── target/
│   ├── .gitattributes
│   ├── HELP.md
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── docs/
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── assets/
│   │   ├── component/
│   │   ├── context/
│   │   ├── layout/
│   │   ├── page/
│   │   │   ├── home.tsx
│   │   │   ├── ingredient\_create.tsx
│   │   │   ├── ingredient\_details.tsx
│   │   │   ├── jobs.tsx
│   │   │   ├── login\_success.tsx
│   │   │   ├── login.tsx
│   │   │   ├── profile.tsx
│   │   │   ├── recipe\_details.tsx
│   │   │   ├── recipe.tsx
│   │   │   ├── recipeplan.tsx
│   │   │   ├── shoppinglist.tsx
│   │   │   └── weekplan.tsx
│   │   ├── types/
│   │   │   └── user.tsx
│   │   ├── App.css
│   │   ├── App.tsx
│   │   ├── enums.ts
│   │   ├── helper.ts
│   │   ├── index.css
│   │   ├── login.css
│   │   ├── main.tsx
│   │   ├── types.ts
│   │   └── vite-env.d.ts
│   ├── eslint.config.js
│   ├── frontend.iml
│   ├── index.html
│   ├── package-lock.json
│   ├── package.json
│   ├── README.md
│   ├── tsconfig.app.json
│   ├── tsconfig.json
│   └── vite.config.ts
├── ml-service/
│   ├── src/
│   │   ├── app/
│   │   │   ├── main.py
│   │   │   ├── routers/
│   │   │   ├── models/
│   │   │   ├── services/
│   │   │   └── utils/
│   │   └── test/
│   └── pyproject.toml
├── .gitignore
├── Dockerfile
├── EatYouFit.code-workspace
├── EatYouFit.iml
├── Readme.eng.md
├── Readme.md
└── sonar-project.properties
```
### 🛠️ Code-Qualität & Workflow

Zur Sicherung hoher Code-Standards und zur Unterstützung von Clean-Code-Prinzipien setzen wir für den Python-basierten **`ml-service`** automatisierte **Git Pre-Commit-Hooks** (integriert mit **Ruff**) ein.

* **Warum nutzen wir das?** Die Hooks prüfen den Code innerhalb des `ml-service`-Verzeichnisses automatisch lokal vor jedem Commit. So werden Formatierungsfehler, ungenutzter Code oder Stilverstöße frühzeitig erkannt und behoben, noch bevor der Code in das Repository gelangt.
* **Hinweis für Entwickler:** Die Hooks sind auf den Ordner `ml-service/` beschränkt und haben keinen Einfluss auf Änderungen in anderen Projektbereichen (Backend/Java, Frontend/React).
* **Was passiert bei einem Verstoß gegen die Standards?** 
  Sollte ein Commit aufgrund von Standard-Verstößen abgelehnt werden, korrigiert Ruff viele Fehler (wie Formatierungen) oft automatisch. Du musst die geänderten Dateien dann lediglich erneut zum Staging-Bereich hinzufügen (`git add`) und den Commit wiederholen. Bei verbleibenden Linter-Fehlern zeigt dir das Terminal die genauen Stellen an, die du manuell korrigieren musst.
### 🤖 KI-Transparenz & Nutzung
Dieses Projekt wurde unter Nutzung von KI-Unterstützung entwickelt (u. a. Gemini). KI-Modelle wurden als Assistenzsysteme bei der Erstellung von Programmcode, der architektonischen Planung und der Erarbeitung der Dokumentation eingesetzt. Die finale Kontrolle, Implementierung und inhaltliche Verantwortung liegen bei den menschlichen Projektmitgliedern.
