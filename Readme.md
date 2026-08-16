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
- Tech-Stack & Engineering-Tools

    - Backend-Integration: 
        
        Java Spring Boot (REST-API-Client) + MongoDB.
    - ML-Service: 
    
        Python, FastAPI (typisierte Endpunkte, Pydantic-Validierung).
    - Datenpipeline & Versionierung: 
    
        Prefect/Airflow für die Datenorchestrierung, DVC für die Versionierung von Vektor-Embeddings.
    - CI/CD: 
    
        GitHub-Actions-Automatisierung mit Ruff/Black-Linter, pytest-Suite (Unit- und Integrationstests) sowie dem Bau von Docker-Images für die GHCR.
    - Monitoring: 
    
        Prometheus- & Grafana-Stack zur Überwachung der Golden Signals (Latenz, Fehler) mit Evidently AI zur Erkennung von Daten-Drift bei eingehenden Lebensmittelabfragen, abgesichert durch ein Live-Slack/Discord-Alerting-System.

### 📂 Projektstruktur

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
├── .gitignore  
├── Dockerfile  
├── EatYouFit.code-workspace  
├── EatYouFit.iml  
├── Readme.eng.md  
├── Readme.md  
└── sonar-project.properties

### 🤖 KI-Transparenz & Nutzung
Dieses Projekt wurde unter Nutzung von KI-Unterstützung entwickelt (u. a. Gemini). KI-Modelle wurden als Assistenzsysteme bei der Erstellung von Programmcode, der architektonischen Planung und der Erarbeitung der Dokumentation eingesetzt. Die finale Kontrolle, Implementierung und inhaltliche Verantwortung liegen bei den menschlichen Projektmitgliedern.