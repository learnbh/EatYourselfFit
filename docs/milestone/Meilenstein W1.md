Hier ist die detaillierte Aufgabenliste für deine **erste Woche** (jeweils ca. 4 Stunden pro Tag), um ein solides Fundament für deinen Python-FastAPI-Service und die Verbindung zu deinem Java-Backend zu legen.

### **Tag 1 (Montag): Projekt-Setup & Git-Struktur**

*Fokus: Die saubere Code-Basis aufsetzen.*

> * **Stunde 1–2:** Erstellen des Python-Repositories (bzw. des Subfolders im Monorepo), Strukturierung der Ordner (/app, /routers, /models, /services, /tests).  
> * **Stunde 3:** Konfiguration von pyproject.toml oder requirements.txt (Installation von FastAPI, Uvicorn, Pydantic, LangChain).  
> * **Stunde 4:** Aufsetzen des Git-Pre-Commit-Setups und erster Commit mit einer sauberen README.md.

### **Tag 2 (Dienstag): Docker-Compose & Container-Kommunikation**

*Fokus: Dafür sorgen, dass Java und Python lokal miteinander reden können.*

> * **Stunde 1–2:** Schreiben eines robusten Dockerfile für den Python-FastAPI-Service.  
> * **Stunde 3:** Erstellen der zentralen docker-compose.yml (Verknüpfung von Java-Spring-Backend auf Port 8080 und Python-FastAPI auf Port 8000).  
> * **Stunde 4:** Testen des Docker-Netzwerks: Starten der Container und Prüfen, ob die Services erreichbar sind.

### **Tag 3 (Mittwoch): FastAPI-Endpunkte & Router-Grundgerüst**

*Fokus: Die Schnittstelle definieren, die dein Java-Backend ansprechen wird.*

> * **Stunde 1–2:** Aufsetzen der FastAPI-Anwendung in main.py inklusive CORS-Middleware und Basis-Routing.  
> * **Stunde 3:** Entwurf des zentralen Orchestrator-Endpoints (POST /api/v1/ingredient), der eingehende Anfragen von Java entgegennimmt.  
> * **Stunde 4:** Erste Implementierung der Router-Logik (Mock-Entscheidung: Ist es ein bekannter Begriff oder wird ein Fallback benötigt?).

### **Tag 4 (Donnerstag): Strikte Datentypen mit Pydantic**

*Fokus: Der "Vertrag" zwischen Java-Backend und Python-Service.*

> * **Stunde 1–2:** Schreiben der Pydantic-Modelle für den Input (z. B. Name der gesuchten Zutat) und Output (strukturierte Nährwertangaben wie Kalorien, Proteine, Fette, Vitamine).  
> * **Stunde 3:** Integration der Validierungslogik in den FastAPI-Endpunkt (ungültige oder unvollständige Daten werden direkt abgefangen).  
> * **Stunde 4:** Schreiben kleiner lokaler Tests mit pytest oder manuellem Testen über die FastAPI-Swagger-Oberfläche (/docs).

### **Tag 5 (Freitag): Integrationstest & Wochensprint-Review**

*Fokus: Der erste echte End-to-End-Durchstich.*

> * **Stunde 1–2:** Anpassung des Java-Spring-Backends (bzw. eines Test-Clients in Java), sodass ein HTTP-POST-Request an den FastAPI-Service gesendet wird.  
> * **Stunde 3:** Durchführung des ersten vollständigen Datenflusses: Java schickt Zutat $\\rightarrow$ FastAPI validiert via Pydantic $\\rightarrow$ FastAPI antwortet mit strukturiertem JSON $\\rightarrow$ Java verarbeitet es.  
> * **Stunde 4:** Code-Cleanup, Git-Push und kurzer Review: Steht das Fundament für die Agenten-Implementierung in Woche 2?