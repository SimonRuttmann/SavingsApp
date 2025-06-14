# 💰 SparApp – Save Smart. Spend Smarter.

**SparApp** is a modern full-stack budgeting tool – designed with students, roommates, and small groups in mind.

From creating personal and group savings entries to visualizing trends and chatting in real-time – this app does it all.

<p align="center">
  <img src="media/gifs/crop/savingentry-visualization-crop.gif" width="65%"/>
  <img src="media/gifs/crop/mobile-savingentry-visualization-crop.gif" width="25%"/>
</p>

---

<!-- TOC -->
 [💰 SparApp – Save Smart. Spend Smarter.](#-sparapp--save-smart-spend-smarter)
  * [🎓 Project & Context](#-project--context)
  * [🌐 Core Features](#-core-features)
  * [🔨 Technical Overview](#-technical-overview)
  * [🔧 Architectural Overview](#-architectural-overview)
  * [🎞️ Feature Highlights](#-feature-highlights)
    * [🔐 User Management](#-user-management)
    * [📊 Visualizing Entries](#-visualizing-entries)
    * [✏️ Managing Entries and 🗂️ Categories](#-managing-entries-and--categories)
    * [👥 Managing Groups](#-managing-groups)
    * [💬 Real-Time Group Chat](#-real-time-group-chat)
    * [📱 Mobile Visualization](#-mobile-visualization)
  * [🧑‍💻 Teamwork Makes the Dream Work](#-teamwork-makes-the-dream-work)
  * [📜 License](#-license)
  * [🖼️ Videos](#-videos)
<!-- TOC -->

---

## 🎓 Project & Context

This application was developed as part of our studies at **Aalen University** during the **Cloud and Distributed Computing** course.  
Our goal was to build a **secure, scalable, cloud-ready** app with:

- Authenticated personal spaces
- Extensive Group & User Management
- Shared group data
- Real-time communication features
- Data visualization and projections
- Full Mobile and desktop-sized Webbrowser support
- Scalability in mind

---

## 🌐 Core Features

| Feature                  | Description                                                             |
|--------------------------|-------------------------------------------------------------------------|
| 🔐 Auth                 | Login, logout, register via Keycloak                                     |
| 📚 Entries              | Create, update, delete savings entries                                   |
| 🗂️ Categories           | Manage personal and group categories                                     |
| 📈 Visualization        | Charts by user, category, and time                                       |
| 🤝 Groups               | Create, join, leave groups; invite users                                 |
| 💬 Chat                 | Real-time group chat with WebSocket & Redis                              |
| 🔍 Filters              | Search and filter entries in flexible dashboards                         |
| 📉 Inflation Support    | Adjust savings and forecasts using real inflation data                   |

---

## 🔨 Technical Overview

One aspect of this project was to familiarize ourselves with a wide range of
technologies in order to gain a broad overview. These are the technologies we used.

![](media/screenshots/design/technologies.png)

- **Frontend:** 
  - React.js 
  - Redux Store
  - REST API integration 
  - Keycloak Auth integration
- **Backend:** 
  - Multiple Java Spring Boot Microservices
- **Auth:** 
  - Keycloak
- **Databases:** 
  - PostgreSQL
  - MongoDB
  - Redis (Sub/Pub Plugin)
- **Deployment:** 
  - Docker 
  - Kubernetes

---

## 🔧 Architectural Overview

The system essentially consists of **microservices**, **shared modules**, **databases**,
the reverse proxy **Keycloak** and the **client app**.

![](media/screenshots/design/top-level-architecture.png)

The client makes requests to the microservices. These pass through the reverse proxy,
which authenticates or rejects the requests. The microservices accept the incoming
requests and execute their business logic by using the implemented modules.

The modules offer functionality that is used by several microservices. This applies,
for example, to the persistence of objects or validation of shared Data Transfer Objects.

![](media/screenshots/design/package-diagram.png)

The application is designed in a cloud-ready manner.
For our non-production operation, we operated the databases directly in 
kubernetes in pods. The remaining applications are provided via deployments
and exposed with services.

![](media/screenshots/design/deployment-diagram.png)
---

## 🎞️ Feature Highlights

### 🔐 User Management

The application is secured by an **OIDC Authorization Code Flow** implemented by **Keycloak**.


<div align="center">
<img src="media/gifs/crop/user-management-login-crop.gif" />
</div>

<div align="center">
<img src="media/gifs/crop/user-management-registration-crop.gif" />
</div>

### 📊 Visualizing Entries

The dashboard visualizes:

- Income vs Expenses
- Category distribution over time
- User-specific and group-based trends
- Historical + future budget projections (with inflation!)

---

<div align="center">
<img src="media/gifs/crop/savingentry-visualization-crop.gif" />
</div>

### ✏️ Managing Entries and 🗂️ Categories

Well what would you expect from a saving app? 😅

We support:
- Full CRUD for entries
- Full CRUD for categories

<div align="center">
<img src="media/gifs/crop/savingentry-creation-crop.gif" />
</div>

<div align="center">
<img src="media/gifs/crop/savingentry-edit-delete-crop.gif" />
</div>

<div align="center">
<img src="media/gifs/crop/category-creation-crop.gif" />
</div>

### 👥 Managing Groups

Everyone can create groups and invite people.
People accept those invitations. Of course they can also leave the groups.
People within the same group can chat to each other via the real-time chat.
Groups also enable filtering of expenses based on the individual members.

<div align="center">
<img src="media/gifs/crop/group-management-crop.gif" />
</div>

### 💬 Real-Time Group Chat

Stay in sync with your group through an integrated chat system – powered by:

- WebSocket connections to a dedicated chat-service
- Pub/Sub channels powered by a Redis-Plugin
- Message history persistence


<div align="center">
<img src="media/gifs/crop/group-chat-send-crop.gif"/>
</div>

<div align="center">
<img src="media/gifs/crop/group-chat-receive-crop.gif"/>
</div>

### 📱 Mobile Visualization

The whole app is designed with mobile devices in mind!

<p align="center">
  <img src="media/gifs/crop/mobile-slide-to-bottom-crop.gif" width="200"/>
  <img src="media/gifs/crop/mobile-savingentry-visualization-crop.gif" width="200"/>
</p>

---


## 🧑‍💻 Teamwork Makes the Dream Work

Developed by:

- Simon Ruttmann
- Veronika Scheller
- Michael Ulrich
- Robin Röcker

---

## 📜 License

This project is licensed under the [Apache 2.0 License](LICENSE).
Feel free to use or remix it – just give proper credit. 🤝

## 🖼️ Videos

🎬 [Watch Desktop-Walkthrough (MP4)](media/video/Desktop-Walkthrough.mp4)

🎬 [Watch Mobile-Walkthrough (MP4)](media/video/Mobile-Walkthrough.mp4)

