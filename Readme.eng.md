### 🥗 Project Name

NutriFlow: The Intelligent Ingredient & RAG Engine

👥 Team

Members: Beatrice Henning


### ⚡ Short Pitch

Tired of dead-end recipe apps that draw a blank on exotic ingredients? NutriFlow breathes life into your kitchen by turning missing database entries into instant, AI-driven nutritional facts using an autonomous RAG Agent. It’s not just a recipe tracker—it's a production-grade, self-enriching data engine engineered to keep your plate healthy and your system rock-solid!

### 📖 The Full Story

The Problem

Traditional recipe and fitness applications heavily rely on static databases. If a user inputs an uncommon, regional, or highly specific ingredient (like “Sea Buckthorn” or “Teff Flour”), the system fails to display its nutritional value. This disrupts the user experience and leaves gaps in recipe meal-tracking.
The Solution

NutriFlow bridges this gap by transforming a standard Java Spring & React web application into an intelligent, self-learning ecosystem.
- The Flow: 

    When a user views a recipe or ingredient, the Java Spring backend checks our primary MongoDB database. If the ingredient is missing, the system doesn't give up. Instead, it triggers our containerized Python ML Service.

- The Intelligence: 

    The Python service orchestrates a smart Agent. This Agent first queries a local vector database (RAG) stocked with official nutritional guidelines. If the data isn't there, the Agent autonomously searches the web using a free LLM API to extract, structure, and validate the nutritional profile.

- The Loop: 

    The structured data is returned to the Java backend via clean REST APIs, saved to MongoDB for future users, and immediately displayed in the React frontend.

### 🏗️ Project Outline
1. Model & Approach

    - RAG System: 
    
        A vector database containing high-quality food nutritional data.

        - Autonomous Agent: 
        
            Built with LangChain (or LangGraph). It evaluates   whether the vector database can answer the query or if a web-search   fallback is required.

        - Inference: 
        
            Utilizing cost-free/free-tier LLM endpoints (e.g., Groq    with Llama-3 or Hugging Face APIs) to parse, structure, and output     clean JSON data validated by Pydantic.

2. Data Resources

    - USDA FoodData Central / Open Food Facts: 
    
        Used as the baseline ground-truth dataset to populate our RAG vector store.

    - Agent Web Search: 
    
        DuckDuckGo API or similar free search tools for real-time internet fallback.

3. Tech Stack & Engineering Tools

- To meet the high production standards of the Capstone:

    - Backend Integration: 
    
        Java Spring Boot (REST API client) + MongoDB.

    - ML Service: 
    
        Python, FastAPI (typed endpoints, Pydantic validation).

    - Data Pipeline & Versioning: 
    
        Prefect/Airflow for data orchestration, DVC for vector embeddings versioning.

    - CI/CD: 
    
        GitHub Actions automation running Ruff/Black linter, pytest suite (unit + integration), and building Docker images to GHCR.

    - Monitoring: 
    
        Prometheus & Grafana stack tracking the golden signals (latency, errors), with Evidently AI detecting data drift on incoming ingredient queries, backed by a live Slack/Discord alert system.


### 📂 Project Structure

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

### 🤖 AI Transparency & Usage
This project was developed with the assistance of AI tools (including Gemini). AI models were used as assistants for coding, architectural planning, and documentation drafting. The final implementation, technical oversight, and overall responsibility remain with the human team members.