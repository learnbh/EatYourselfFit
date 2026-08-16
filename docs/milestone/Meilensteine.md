Das ist ein absolut spannendes Projekt\! Du hast bereits ein starkes Fundament mit deinem Java Spring / React Stack. Die größte Herausforderung wird hierbei sein, das Projekt von einer klassischen Web-App in ein **echtes Machine Learning Engineering (MLE) System** nach den Kriterien deines Capstone-PDFs zu verwandeln.  
Das PDF betont unmissverständlich: **„Keep the modelling simple. The point is the engineering: Clean code, an API, a pipeline, CI/CD, and monitoring.“** Das bedeutet für dich: Der Fokus liegt nicht darauf, das komplexeste LLM-Modell zu bauen, sondern den neuen Python-Agenten- & RAG-Service professionell zu paketieren, zu testen, zu deployen und zu überwachen.  
Hier ist dein maßgeschneiderter Fahrplan bei **20–25 Stunden Arbeitszeit pro Woche** (insgesamt ca. 80–100 Stunden für die 4 Wochen).

## **🛠️ Architektur-Konzept (Integration von Java/React & Python)**

Um die Capstone-Kriterien (Python-Package, FastAPI, Docker, Monitoring)  sauber zu erfüllen, trennen wir die Machine-Learning-Infrastruktur strikt vom bestehenden Java-Backend.

\[ React Frontend \] \<--\> \[ Java Spring Backend (Port 8080\) \] \<--\> \[ MongoDB \]  
                              ^  
                              | (REST API / JSON)  
                              v  
             \[ Python FastAPI Service (Port 8000\) \]   
               ├── Agenten-Logik (z. B. mit LangChain/LangGraph)  
               └── RAG Vektor-Datenbank (z. B. Qdrant / Chroma / pgvector)

> * **Java Spring** bleibt dein Haupt-Backend und verwaltet die Benutzer, Rezepte und die primäre MongoDB.  
> * **Python FastAPI** wird dein neuer "ML-Service". Sobald Java eine Zutat nicht in der MongoDB findet, feuert es einen API-Request an den FastAPI-Service.  
> * Der **FastAPI-Service** nutzt einen kostenfreien Agenten (z. B. via Hugging Face API, Ollama oder ein günstiges/freies API-Modell wie Groq/Llama-3), sucht per Web-Search oder RAG nach den Nährstoffen, speichert sie und gibt das strukturierte Ergebnis (Pydantic-validiert\!) an Java zurück.

## **📅 Meilensteine & Timeline**

Da dein PDF zwei wichtige Präsentationstermine nennt (Midterm am **31.07.** und das Finale am **14.08.**), teilen wir das Projekt in zwei klare Phasen auf.

### **Phase 1: Bis zur Midterm-Präsentation (Freitag, 31.07.2026)**

**Fokus:** Kern-ML-Funktionalität (Python-Service, Agent, RAG) und die Schnittstelle zu Java.

> * **Aufwand:** \~40–50 Stunden (2 Wochen)  
> * **Schritt 1: Setup des Python-Services & API (ca. 10h)**  
  * Erstelle ein neues, sauberes Python-Repository (separat oder als Sub-Folder im Monorepo).  
  * Setze FastAPI mit Pydantic für die Datenvalidierung auf (Eingabe: Zutat-Name; Ausgabe: Strukturierte Nährwertdaten).  
  * Implementiere Code-Qualitäts-Tools: Ruff/Black für Linting & Formatierung, Pre-commit Hooks einrichten.  
> * **Schritt 2: Agenten- & RAG-Logik (Kostenfrei) (ca. 20h)**  
  * **RAG-Komponente:** Baue eine kleine Vektor-Datenbank (z. B. ChromaDB oder Qdrant als Docker-Container), die mit einem Open-Source-Datensatz für Lebensmittel-Nährwerte gefüttert wird.  
  * **Agenten-Logik:** Nutze z. B. LangChain. Der Agent prüft zuerst die Vektordatenbank (RAG). Findet er nichts, nutzt er ein kostenfreies Web-Search-Tool (z. B. DuckDuckGo Search API) kombiniert mit einem freien LLM (z. B. Llama-3 über die Groq-API im Free-Tier).  
> * **Schritt 3: Java-Spring-Integration (ca. 10h)**  
  * Passe den Java-Code an: Wenn MongoDB-Abfrage fehlschlägt $\\rightarrow$ HTTP-Call an FastAPI.  
  * FastAPI liefert JSON zurück $\\rightarrow$ Java speichert es in MongoDB und zeigt es im React-Frontend an.  
> * **Schritt 4: Vorbereitung Midterm-Präsentation (ca. 5h)**  
  * **Meilenstein 1 (31.07.):** Funktionierender Prototyp (React $\\leftrightarrow$ Java $\\leftrightarrow$ Python FastAPI mit lauffähigem Agenten).

### **Phase 2: Bis zur Finalen Präsentation (Freitag, 14.08.2026)**

**Fokus:** Die harten MLE-Requirements (Testing, CI/CD, Monitoring, Datenpipeline).

> * **Aufwand:** \~40–50 Stunden (2 Wochen)  
> * **Schritt 5: Containerisierung & CI/CD (ca. 12h)**  
  * Schreibe ein robustes Dockerfile für den Python-FastAPI-Service.  
  * Erweitere deine GitHub Actions: Neben dem Java-Build läuft nun eine Python-Pipeline, die Unit-Tests (pytest) ausführt, Code linst (Ruff) und das Python-Docker-Image baut.  
> * **Schritt 6: Data Pipeline & MLOps (ca. 12h)**  
  * **Pipeline (Prefect/Airflow):** Erstelle eine einfache, orchestrierte Pipeline, die z. B. einmal wöchentlich neue Zutaten-Einträge validiert oder die Vektordatenbank im Hintergrund aktualisiert.  
  * **Versionierung:** Nutze DVC (Data Version Control) für deine RAG-Vektordaten oder Embeddings-Modelle.  
> * **Schritt 7: Monitoring & Alerts (Pflicht-Kriterium\!) (ca. 12h)**  
  * Setze Prometheus & Grafana lokal (via Docker Compose) auf, um die API-Requests und Antwortzeiten (Golden Signals) deines FastAPI-Services zu überwachen.  
  * Integriere **Evidently AI** in deine Python-App, um Datendrift (z. B. wenn plötzlich ungewöhnliche Zutaten-Anfragen reinkommen) zu tracken.  
  * Richte mindestens einen Alert ein (z. B. Discord-Alert oder Email, wenn die Fehlerrate der API über 5% steigt).  
> * **Schritt 8: Zusatzfeatures & Schliff (ca. 6h)**  
  * *Optional:* Das Chatfenster im React-Frontend für den direkten Chat mit dem Agenten umsetzen (nur wenn noch Zeit ist\!).  
  * *Optional:* Berechnung der Gesamtnährwerte für Rezepte im Java-Backend (kann einfach in Java/SQL gelöst werden, hat aber niedrigere Priorität als die ML-Requirements ).  
> * **Schritt 9: Präsentations-Vorbereitung (ca. 8h)**  
  * 11.08. Dry Run: Präsentation muss im Groben stehen.  
  * Erstelle die Slides (Fokus auf Systemarchitektur, Docker, CI/CD-Pipelines und Monitoring-Dashboards, NICHT nur auf die Genauigkeit des LLM).  
  * **Meilenstein 2 (14.08.):** Final Presentation & Graduation Day\!

## **📊 Zusammenfassende Aufwandsmatrix**

| Aufgabe | Priorität | Geschätzter Aufwand (Stunden) | Relevanz für Capstone-Bewertung |
| :---- | :---- | :---- | :---- |
| **Python FastAPI-Service & API-Design** | 🔴 Hoch | 10h |  **Pflicht** (Containerised Service) |
| **Agenten-Logik & RAG-Infrastruktur** | 🔴 Hoch | 20h | Kern-Feature des Projekts |
| **Java & React Anbindung** | 🔴 Hoch | 10h | System-Integrations-Nachweis |
| **Unit-Tests & GitHub Actions CI/CD** | 🔴 Hoch | 12h |  **Pflicht** (Automated Tests, Pipeline) |
| **Monitoring (Grafana \+ Evidently) & Alert** | 🔴 Hoch | 12h |  **Pflicht** (Dashboard \+ Alert live) |
| **Datenpipeline (z. B. Prefect/DVC)** | 🟡 Mittel | 12h |  **Pflicht** (Data Pipeline & Lifecycle) |
| **UI-Chatfenster & Rezeptberechnung** | 🟢 Niedrig | 6h | Optionales "Nice-to-have" |
| **Slides & Präsentations-Vorbereitung** | 🔴 Hoch | 13h |  **Pflicht** (Midterm, Dry Run, Final) |

## **💾 Datenressourcen (Kostenfrei)**

Um deinen RAG-Agenten mit einer soliden Wissensbasis zu füttern, kannst du folgende kostenfreie Datenquellen nutzen:

> 1. **USDA FoodData Central API / Datensätze:** Die offizielle US-Behörden-Datenbank für Nährwerte. Bietet riesige CSV/JSON-Exporte, die du perfekt als Grundlage in deine Vektor-DB (RAG) laden kannst.  
> 2. **Open Food Facts (OFF) Database:** Ein riesiges, freies, kollaboratives Verzeichnis von Lebensmittelprodukten weltweit mit vollständigen Nährwertangaben (gibt fertige Python-Wrapper dafür).  
> 3. **Kaggle-Datensätze:** Suche nach "Nutrition Facts" oder "Food Ingredients JSON" für kleinere, bereinigte Datensätze zum schnellen Mocken deiner Datenbank.

## **💡 Wichtige Tipps für deinen Erfolg:**

> * **Verliere dich nicht im Frontend:** Das Chatfenster auf der React-Seite sieht cool aus, bringt dir aber für die MLE-Bewertung fast keine Punkte. Konzentriere dich zuerst auf die **Robustheit des Python-Backends**, die **Tests** und das **Monitoring**.  
> * **Tägliche Team-Screenshots:** Vergiss nicht, dass du (falls du in einer Gruppe arbeitest) täglich ein Kamera-Gruppenfoto im Discord-Kanal posten musst\!

Mit diesem Fahrplan hast du die perfekte Balance zwischen deinem bestehenden Java-Projekt und den harten Python-MLE-Anforderungen des Capstone-Kurses\!