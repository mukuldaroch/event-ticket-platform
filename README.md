Event Ticketing Platform

EventForge is a modular **event management and ticketing backend** built with RESTful APIs.
It empowers **organizers** to create and manage events, define ticket types, and sell them online — all through clean, scalable endpoints.

Think of it as the **brain of an event platform** — running everything from creation to checkout.

---

## Database Design

Here’s the complete ERD:

### [ Database Design and Entity Relationships](https://miro.com/app/board/uXjVJC_Ymcs=/?share_link_id=897811772590)

---

## Features

- Organizer can create and manage events (name, date, time, venue).
- Multiple ticket types (VIP, Regular, Student, etc.) with different prices.
- Control total available tickets per type.
- Events become visible instantly after publishing.
- Attendees can browse and purchase tickets securely.
- Fully modular REST API for future extensions (staff, payments, analytics, etc.).

---

## How It Works

1. Organizer creates an event.
2. Defines ticket types and total count.
3. Event is published.
4. Attendees view events and buy tickets.
5. System tracks sales and available tickets in real time.

---

## Project Modules

| Module            | Description                                             |
| ----------------- | ------------------------------------------------------- |
| **Organizer API** | Create, update, and manage events and tickets.          |
| **Attendee API**  | Browse and purchase tickets.                            |
| **Staff API**     | (Planned) Manage ticket scanning, entry, and analytics. |

---

## API Structure

|  Method   | Endpoint                                                                  | Description                         | Request Body     |
| :-------: | :------------------------------------------------------------------------ | :---------------------------------- | :--------------- |
| **POST**  | `/api/events`                                                             | Create a new event                  | Event object     |
|  **GET**  | `/api/events`                                                             | List all events                     | —                |
|  **PUT**  | `/api/events/{event_id}`                                                  | Update existing event               | Event object     |
|  **GET**  | `/api/events/{event_id}`                                                  | Retrieve specific event             | —                |
|  **GET**  | `/api/events/{event_id}/tickets`                                          | List tickets for event              | —                |
|  **GET**  | `/api/events/{event_id}/tickets/{ticket_id}`                              | Retrieve ticket details             | —                |
| **PATCH** | `/api/events/{event_id}/tickets`                                          | Partial ticket update               | Partial object   |
|  **GET**  | `/api/events/{event_id}/ticket-types`                                     | List ticket types                   | —                |
|  **GET**  | `/api/events/{event_id}/ticket-types/{ticket_type_id}`                    | Retrieve ticket type                | —                |
| **PATCH** | `/api/events/{event_id}/ticket-types/{ticket_type_id}`                    | Update ticket type                  | Partial object   |
|  **GET**  | `/api/published-event/{published_event_id}`                               | View published event                | —                |
| **POST**  | `/api/published-event/{published_event_id}/ticket-types/{ticket_type_id}` | Purchase ticket                     | Purchase details |
|  **GET**  | `/api/tickets`                                                            | List all tickets for logged-in user | —                |
|  **GET**  | `/api/tickets/{ticket_id}`                                                | Retrieve specific user ticket       | —                |

---

## 🧩 Tech Stack

- **Backend:** Spring Boot (Java 17+)
- **Build Tool:** Gradle
- **Database:** PostgreSQL
- **Containerization:** Docker & Docker Compose
- **Auth:** JWT-based Authentication
- **Frontend (Planned):** React + Tailwind
- **Deployment:** Dockerized microservice setup (Planned)

---

## ⚙️ How to Run Locally

You can run EventForge locally **directly via Gradle BootRun**.

---

### Run directly (recommended)

#### Requirements:

- Java 17+
- Gradle (wrapper included)
- PostgreSQL running locally

#### Steps:

```bash
# 1️⃣ Clone the repo
git clone https://github.com/mukuldaroch/eventforge.git
cd eventforge

# 2️⃣ Configure application properties
# Update src/main/resources/application.yml or .env with your local DB creds

# 3️⃣ Clean build
./gradlew clean build

# 4️⃣ Run the application
./gradlew  clean bootRun
```

App will start on:

- `http://localhost:8080`

#### Test it:

```bash
curl http://localhost:8080/api/events
```

---

## Future Plans

- Payment gateway integration (Stripe/Razorpay)
- Ticket QR code generation and validation
- Email confirmation system
- Real-time event analytics dashboard
- Role-based access (Organizer, Staff, Admin)
- UI dashboard for organizers

---

## 🤝 Contributing

Contributions welcome
Open issues, suggest features, or submit PRs — all are appreciated.
Let’s make EventForge the open-source backbone of modern event systems.

---

## 👨‍💻 Author

- [@Mukul Daroch](https://github.com/mukuldaroch)
