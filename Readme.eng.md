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

### 🏗️ Architecture & Technology Stack

The project is divided into three main components, which are automatically deployed to Render via a robust CI/CD pipeline:

1. Backend (Java Spring Boot)
Core: REST API for managing recipes, users, and nutritional values.
Database: MongoDB for persistent storage.
Quality: Use of Sonar for static code analysis and adherence to clean code principles.
2. Frontend (React)
Technology: Vite + TypeScript.
Focus: Intuitive user interface for recipe management, weekly planning, and shopping list generation.
3. ML Service (Python FastAPI)
AI Agent: LangChain/LangGraph agent for autonomous web browsing (fallback) and RAG-supported research.
Validation: Pydantic for strict data schemas.
Production: Containerized via Docker for seamless integration into the pipeline.
4. DevOps & CI/CD
Automation: GitHub Actions workflows for build, test (unit & integration), and linting.
Code Quality: Ruff & Black for Python, Sonar integration for Java.
Deployment: Container-based deployment on Render.
Monitoring: Prometheus & Grafana for real-time metrics (latency, errors) as well as Evidently AI for detecting data drift.

📂 Project Structure (Rough Overview)

```text
EatYouFit/
├── .github/workflows/    # CI/CD (Maven, Sonar, Deployment)
├── backend/              # Java Spring Boot & MongoDB Integration
├── frontend/             # React + Vite UI
├── ml-service/           # FastAPI, LangChain & Agent Logic
├── sonar-project.properties
├── Dockerfile            # Container Setup
└── ...

```

📖 The Process: How NutriFlow Learns

```
Request: The user queries an ingredient.

Check: The Java backend searches the local MongoDB.

Fallback (if needed): If the ingredient is unknown, the backend sends a request to the ml-service.

Autonomous Research: The ML agent uses RAG for official data or searches the web.

Validation & Storage: The results are validated, returned as JSON, and stored in MongoDB for future use.

```

### 📂 Project Structure
```
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
### 🛠️ Code Quality & Workflow

To maintain high code standards and support clean-code principles, we use automated **Git Pre-Commit Hooks** (integrated with **Ruff**) specifically for the **`ml-service`**.

* **Why do we use them?** The hooks automatically check the code within the `ml-service/` directory locally before every commit to catch formatting issues, unused code, or style violations early, before the code reaches the repository.
* **Developer Note:** These hooks are strictly scoped to the `ml-service/` folder and do not affect development in other parts of the project (Backend/Java, Frontend/React).
* **What should a developer do if a commit doesn't meet the standards?** 
  If a commit is rejected because the code violates the defined standards, Ruff will often automatically fix formatting issues for you. You just need to stage the modified files again (`git add`) and retry your commit. For any remaining linter errors, the terminal will display the exact issues that need to be fixed manually.
### 🤖 AI Transparency & Usage
This project was developed with the assistance of AI tools (including Gemini). AI models were used as assistants for coding, architectural planning, and documentation drafting. The final implementation, technical oversight, and overall responsibility remain with the human team members.
