
# Agrotical – Crop Farming Management

**Agrotical** is a full-stack application that empowers farmers to **digitally manage fields, crops, and production economics**. 
Built with Spring Boot backend and React.js/Tailwind frontend, this project allows users to register fields, input crop parameters, calculate results (profit, cost, yield), and improve the economic management of your crops and farms.



---

##  About

Motivated by the need for efficient farm management, Agrotical provides farmers a system to:

- Follow crop lifecycle: planting → fertilization → harvesting  
- Compute data per field: production yield, costs, and net profit  
- Visualize insights per field and overall performance


---

##  Features

- Authentication via username/password  
- Field CRUD: add and manage fields with name & area  
- Crop registration: input irrigation/fertilization/spraying flags  
- Result computation: auto-calculate production, revenue, expenses, profit  
- Data visualization: summary tables and charts 


- Clean architecture with Builder & Factory & Adapter patterns for further development 
- Unit tests: JUnit + Mockito
- Modular service/repository/controller layers for scalability

---

##  Technologies

| Layer        | Technology                             |
|--------------|----------------------------------------|
| Backend      | Spring Boot, Spring Data JPA, Hibernate, PostgreSQL, JUnit, Mockito |
| Frontend     | React, TailwindCSS, Axios, React Router |
| Build Tools  | Maven (backend), npm (frontend)   |

---

##  Project Structure

```
agrotical/
├── src/main/java/com/agrotical
│   ├── controller/        
│   ├── service/          
│   ├── repository/        
│   ├── entity/            
│   └── dto/               
└── src/main/resources
    └── application.properties  
```

```
agrotical-frontend/
├── public/
├── src/
│   ├── components/       
│   ├── pages/             
│   ├── services/          
│   ├── App.js             
│   └── index.js          
└── tailwind.config.js    

```

---

##  Getting Started

### Prerequisites
- Java 17, Node.js, PostgreSQL installed ,"create React app"
- DB "agrodb" created with matching credentials in `application.properties`

### Setup Database
```bash
psql -U postgres
CREATE DATABASE agrodb;
```

### Backend Launch
```bash
cd agrotical
./mvnw spring-boot:run
# Runs on http://localhost:8080
```

### Frontend Launch
```bash
cd agrotical-frontend
npm install
npm start
# Runs on http://localhost:3000
```

---

##  Workflow

1. Register/Login with a username & password  
2. Create fields with specific area (e.g. "Χωράφι" – 10 στρέμματα)  
3. Add parameters: choose crop + flags for irrigation/fertilization/spraying  
4. The app computes: (production, costs, revenues, profit) per field 
5. After computation: Board per field which: filter by crop, view results per field/crop  
6. Inspect summaries and charts for data insights
7. Calculation of whole data ,and computation of the total profit

---

##  Testing

Run backend tests using:

```bash
cd agrotical
./mvnw test
```


|  Name : Konstantinos Kazakidis   |

