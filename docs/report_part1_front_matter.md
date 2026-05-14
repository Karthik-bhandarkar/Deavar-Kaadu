
# DEVARA-KAADU – SACRED GROVE SENTINEL

## A Comprehensive Android Application for Biodiversity Conservation and Traditional Ecological Knowledge Preservation of Karnataka's Sacred Groves

---

### INTERNSHIP / PROJECT REPORT

**Submitted in Partial Fulfillment of the Requirements for the Degree of**

**Bachelor of Engineering / Bachelor of Technology**

**in**

**Computer Science and Engineering**

---

| Field | Details |
|-------|---------|
| **Project Title** | 69 – Sacred Grove Sentinel |
| **Project Type** | Android Application Development |
| **Technology** | Kotlin, Jetpack Compose, MVVM Architecture |
| **Institution** | [Insert Institution Name] |
| **Department** | Computer Science and Engineering |
| **Student Name** | [Insert Student Name] |
| **USN / Roll No.** | [Insert USN] |
| **Guide / Mentor** | [Insert Guide Name] |
| **Academic Year** | 2025–2026 |
| **Date of Submission** | [Insert Date] |

---

> *"ಕಾಡು ಉಳಿದರೆ ನಾಡು ಉಳಿಯುತ್ತದೆ" — If the forest survives, the land survives.*

---

*[Insert Figure 1.1: App Logo / Splash Screen]*

**Figure 1.1** — Devara-Kaadu Application Splash Screen showing the Sacred Forest branding with animated gradient background in forest green tones.

---
---

# CERTIFICATE

This is to certify that the project work entitled **"Devara-Kaadu – Sacred Grove Sentinel"** is a bonafide work carried out by **[Insert Student Name]**, USN: **[Insert USN]**, in partial fulfillment of the requirements for the award of the degree of **Bachelor of Engineering** in **Computer Science and Engineering** of **[Insert University Name]**, during the academic year **2025–2026**.

The project has been completed under the guidance of **[Insert Guide Name]**, and it is certified that all corrections/suggestions indicated during the Internal Assessment have been incorporated in the report.

|  |  |
|--|--|
| **Signature of Guide** | **Signature of HOD** |
| Name: [Insert] | Name: [Insert] |
| Date: | Date: |

**Signature of External Examiner:**

Name: ______________________  
Date: ______________________

---
---

# ACKNOWLEDGEMENT

I would like to express my sincere gratitude to all those who contributed to the successful completion of this project.

First and foremost, I express my heartfelt thanks to my project guide, **[Insert Guide Name]**, for their constant support, valuable guidance, and constructive criticism throughout the project development lifecycle.

I am grateful to the **Head of the Department**, **[Insert HOD Name]**, for providing the necessary infrastructure and permissions to carry out this project.

I extend my thanks to the **Principal**, **[Insert Principal Name]**, for fostering a conducive academic environment.

I wish to acknowledge the **Karnataka Biodiversity Board**, **Karnataka Forest Department**, and various academic publications on Sacred Groves that provided the ecological and cultural domain knowledge essential for this project.

Special thanks to the open-source community behind **Kotlin**, **Jetpack Compose**, **Room Database**, and the **Android Jetpack** ecosystem, whose tools made this application possible.

I also thank my fellow students and friends who provided feedback during the testing and UI evaluation phases of development.

Finally, I thank my parents and family for their unwavering support and encouragement.

**[Insert Student Name]**  
**[Insert Date]**

---
---

# TABLE OF CONTENTS

| Sr. No. | Title | Page No. |
|---------|-------|----------|
| 1 | Cover Page | 1 |
| 2 | Certificate | 2 |
| 3 | Acknowledgement | 3 |
| 4 | Table of Contents | 4 |
| 5 | About the Organization | 6 |
| 6 | Objectives of Internship | 7 |
| 7 | Learning Experiences | 8 |
| 8 | Learning Outcomes | 9 |
| 9 | Abstract | 10 |
| 10 | Introduction | 11 |
| 11 | Problem Statement | 13 |
| 12 | Literature Survey | 14 |
| 13 | Existing System | 16 |
| 14 | Proposed System | 17 |
| 15 | Feasibility Study | 18 |
| 16 | SWOT Analysis | 19 |
| 17 | System Requirements | 20 |
| 18 | Technology Stack | 21 |
| 19 | System Architecture | 23 |
| 20 | Application Modules | 26 |
| 21 | Database Design | 38 |
| 22 | UI/UX Design | 42 |
| 23 | Working Methodology | 44 |
| 24 | Feature Explanation | 45 |
| 25 | GPS Integration | 48 |
| 26 | Species Scan Simulation | 50 |
| 27 | Room Database Implementation | 52 |
| 28 | Offline-First Architecture | 54 |
| 29 | Security & Permissions | 55 |
| 30 | Testing & Debugging | 56 |
| 31 | Results & Outputs | 59 |
| 32 | Challenges Faced | 61 |
| 33 | User Benefits | 62 |
| 34 | Environmental Impact | 63 |
| 35 | Social Impact | 64 |
| 36 | Limitations | 65 |
| 37 | Risk Analysis | 66 |
| 38 | Project Timeline & Gantt Chart | 67 |
| 39 | Future Scope | 68 |
| 40 | Conclusion / Summary | 70 |
| 41 | References | 71 |
| 42 | Attachments | 73 |
| 43 | Screenshot Documentation Guide | 74 |

---

# LIST OF FIGURES

| Figure No. | Title |
|-----------|-------|
| 1.1 | Application Splash Screen |
| 5.1 | System Architecture Diagram |
| 5.2 | MVVM Architecture Flow |
| 5.3 | Navigation Flowchart |
| 6.1 | Splash Screen |
| 6.2 | Onboarding Screen (3 pages) |
| 6.3 | Home Dashboard |
| 6.4 | Grove Directory – Grid View |
| 6.5 | Grove Directory – List View |
| 6.6 | Grove Detail Screen |
| 6.7 | Species Scan Screen |
| 6.8 | Species Scan Result |
| 6.9 | Myth & Legend Screen |
| 6.10 | Nearby Grove (GPS) Screen |
| 6.11 | Conservation Alert Form |
| 6.12 | Conservation Alert History |
| 6.13 | Guardian Mode Screen |
| 6.14 | Eco Education Screen |
| 6.15 | Search Screen |
| 6.16 | Settings Screen |
| 7.1 | Database ER Diagram |
| 7.2 | Groves Table Schema |
| 7.3 | Species Table Schema |
| 7.4 | Alerts Table Schema |
| 7.5 | User Progress Table Schema |
| 8.1 | GPS Detection Workflow |
| 8.2 | Species Scan Workflow |
| 8.3 | Alert Submission Workflow |
| 9.1 | Use Case Diagram |
| 9.2 | Offline Data Flow Diagram |

# LIST OF TABLES

| Table No. | Title |
|-----------|-------|
| 3.1 | Technology Stack Summary |
| 4.1 | Hardware Requirements |
| 4.2 | Software Requirements |
| 5.1 | Application Module Summary |
| 7.1 | Groves Entity Fields |
| 7.2 | Species Entity Fields |
| 7.3 | Alerts Entity Fields |
| 7.4 | User Progress Entity Fields |
| 10.1 | Functional Test Cases |
| 10.2 | GPS Test Cases |
| 10.3 | Database Test Cases |
| 11.1 | SWOT Analysis Matrix |
| 12.1 | Risk Analysis Matrix |
| 13.1 | Project Timeline |

---
---

# CHAPTER 1: ABOUT THE ORGANIZATION

## 1.1 Organization Overview

**[Insert Organization / University Name]**

[Insert brief description of the organization, its location, departments, and academic programs relevant to Computer Science and Engineering.]

## 1.2 Department Profile

The Department of Computer Science and Engineering at [Insert Institution] is committed to producing skilled engineers proficient in modern software development technologies, including mobile application development, database systems, and human-computer interaction design.

## 1.3 Project Context

This project was developed as part of the [Internship / Final Year Project / Mini Project] requirement under the guidance of the department. The project addresses the intersection of **technology** and **environmental conservation**, specifically focusing on Karnataka's culturally significant Sacred Groves using modern Android development practices.

---
---

# CHAPTER 2: OBJECTIVES OF INTERNSHIP

## 2.1 Primary Objectives

1. To design and develop a fully **offline-first Android application** for sacred grove preservation awareness.
2. To implement **MVVM architecture** with Jetpack Compose for a modern, maintainable codebase.
3. To build a **Room database** system for persistent local storage of grove, species, and user data.
4. To integrate **GPS-based location services** for proximity detection of sacred groves.
5. To create a **simulated AI species scanner** demonstrating proof-of-concept for biodiversity identification.
6. To implement a **gamification system** (Guardian Mode) encouraging user engagement with conservation activities.

## 2.2 Secondary Objectives

1. To document and digitize **traditional ecological knowledge** associated with Karnataka's sacred groves.
2. To provide an **offline conservation reporting mechanism** for threat documentation.
3. To create an **educational platform** covering biodiversity, water conservation, and climate roles of sacred groves.
4. To demonstrate proficiency in **modern Android development** using Kotlin and Jetpack libraries.

---
---

# CHAPTER 3: LEARNING EXPERIENCES

## 3.1 Technical Skills Acquired

| Skill Area | Details |
|-----------|---------|
| **Kotlin Programming** | Advanced Kotlin features: data classes, sealed classes, extension functions, coroutines, StateFlow |
| **Jetpack Compose** | Declarative UI, composable functions, state hoisting, animations, Material Design 3 |
| **MVVM Architecture** | ViewModel + Repository pattern, separation of concerns, reactive data flow |
| **Room Database** | Entity definition, DAO interfaces, database callbacks, JSON preloading, migration strategies |
| **Navigation Compose** | Type-safe navigation, argument passing, back stack management |
| **GPS Integration** | FusedLocationProviderClient, runtime permissions, Haversine distance calculation |
| **WorkManager** | Periodic background tasks, notification scheduling |
| **State Management** | StateFlow, MutableStateFlow, collectAsStateWithLifecycle, Flow operators |

## 3.2 Domain Knowledge

- Understanding of **Sacred Groves** (Devara Kaadu, Kaavu, Bana, Nagabana, Kans) of Karnataka
- **Traditional Ecological Knowledge** and its scientific validation
- **Biodiversity conservation** principles and IUCN classification
- Role of sacred groves in **water recharge**, **carbon sequestration**, and **micro-climate regulation**

## 3.3 Software Engineering Practices

- Version control with **Git**
- Project structure organization following Android best practices
- **Clean architecture** principles with clear layer separation
- JSON schema design for local data assets
- Build configuration with **Gradle Kotlin DSL** and **KSP** for annotation processing

---
---

# CHAPTER 4: LEARNING OUTCOMES

Upon completion of this project, the following learning outcomes were achieved:

1. **LO1**: Ability to design and implement a multi-screen Android application using Jetpack Compose and Kotlin.
2. **LO2**: Understanding of MVVM architecture and its practical implementation with ViewModel, Repository, and DAO layers.
3. **LO3**: Proficiency in Room database design, including entity relationships, query optimization, and JSON-based data preloading.
4. **LO4**: Practical experience with GPS location services, runtime permission handling, and proximity-based feature triggering.
5. **LO5**: Knowledge of offline-first application design patterns, ensuring full functionality without internet connectivity.
6. **LO6**: Experience with UI/UX design using Material Design 3, custom color palettes, typography systems, and micro-animations.
7. **LO7**: Understanding of conservation informatics — the application of technology to environmental preservation.
8. **LO8**: Skills in project documentation, testing methodology, and technical report writing.

---
---

# CHAPTER 5: ABSTRACT

**Devara-Kaadu – Sacred Grove Sentinel** is an offline-first Android application designed to promote the preservation of Karnataka's sacred groves through technology-driven awareness, community participation, and biodiversity documentation. Karnataka is home to over 2,000 sacred groves — forest patches traditionally protected by communities through religious and cultural beliefs for centuries. These groves serve as critical biodiversity hotspots, groundwater recharge zones, and repositories of traditional ecological knowledge.

The application is built using **Kotlin** with **Jetpack Compose** following **MVVM architecture**. It features 13 interactive screens including a grove directory with detailed ecological and mythological data for 8 representative sacred groves, a simulated AI species scanner covering 10 native species, GPS-based nearby grove detection, a conservation alert reporting system, and a gamified Guardian Mode with badge progression.

All data is stored locally using **Room Database**, with initial data preloaded from JSON assets on first launch. The application requires **no internet connectivity**, making it suitable for use in remote forest areas where sacred groves are typically located. GPS integration via Google's **FusedLocationProviderClient** enables proximity-based grove discovery.

**Keywords**: Sacred Groves, Biodiversity Conservation, Android, Jetpack Compose, Room Database, Offline-First, MVVM, Karnataka, Traditional Ecological Knowledge, GPS Location Services.

---
---

# CHAPTER 6: INTRODUCTION

## 6.1 Background

Sacred groves, known as **"Devara Kaadu"** (God's Forest) in Kannada, are among the oldest forms of community-based nature conservation in the world. These are forest patches that have been protected by local communities for centuries through religious beliefs, taboos, and traditional practices. In Karnataka, sacred groves exist in various forms:

- **Devara Kaadu** — Literally "God's Forest," large protected forest patches
- **Kaavu** — Village-level sacred groves with spirit deity associations
- **Bana** — Semi-evergreen forest patches dedicated to specific deities
- **Nagabana** — Serpent deity groves, protecting snake habitats
- **Kans** — Shola-grassland interface forest fragments in the Western Ghats

Karnataka has over **2,000 documented sacred groves** spread across districts including Kodagu, Uttara Kannada, Dakshina Kannada, Chikkamagaluru, and Shivamogga. These groves harbor extraordinary biodiversity — studies show they contain **3–10 times more species per hectare** than surrounding managed forests.

## 6.2 Motivation

Despite their ecological importance, sacred groves face increasing threats from urbanization, encroachment, waste dumping, and erosion of traditional beliefs among younger generations. There is an urgent need for **technology-driven interventions** that:

1. **Digitize and preserve** traditional ecological knowledge before it is lost
2. **Create awareness** among urban and semi-urban populations
3. **Enable community reporting** of threats to sacred groves
4. **Gamify conservation** to engage younger demographics
5. **Work offline** in remote areas where these groves are located

## 6.3 Project Scope

The Devara-Kaadu application addresses these needs through an offline-first mobile platform that combines biodiversity data, cultural narratives, GPS-based discovery, species identification simulation, and gamified conservation participation. The current implementation is a **functional prototype** demonstrating the full user journey with sample data for 8 sacred groves and 10 native species.

---
---

# CHAPTER 7: PROBLEM STATEMENT

## 7.1 Core Problem

Karnataka's sacred groves, which have been preserved for centuries through traditional community guardianship, are facing unprecedented threats. The younger generation is increasingly disconnected from the ecological and cultural knowledge that sustained these groves. There is no centralized, accessible, and engaging digital platform that combines scientific biodiversity information with traditional cultural narratives to promote sacred grove conservation.

## 7.2 Sub-Problems Identified

1. **Knowledge Fragmentation**: Traditional ecological knowledge exists only in oral traditions and is being lost with each generation.
2. **Lack of Digital Documentation**: No mobile-friendly, offline database of Karnataka's sacred groves exists with both scientific and cultural data.
3. **No Community Reporting Mechanism**: Citizens lack a convenient way to report threats (tree cutting, waste dumping, encroachment) to sacred groves.
4. **Low Youth Engagement**: Conservation messaging fails to reach younger demographics who are primarily smartphone users.
5. **Connectivity Constraints**: Sacred groves are in remote areas with poor or no internet connectivity, ruling out cloud-dependent solutions.
6. **Species Identification Gap**: Visitors cannot easily identify the flora and fauna of sacred groves without expert knowledge.

## 7.3 Proposed Solution

An offline-first Android application that serves as a comprehensive sacred grove companion — combining grove discovery, biodiversity information, cultural storytelling, species identification, threat reporting, and gamified engagement — all functioning without internet connectivity.

---
---

# CHAPTER 8: LITERATURE SURVEY

## 8.1 Sacred Groves of India

Chandran & Hughes (1997) documented that India has an estimated 100,000–150,000 sacred groves, with Karnataka ranking among the top five states. These groves range from 0.5 hectares to over 100 hectares and serve as refugia for endemic species.

## 8.2 Technology in Conservation

Joppa (2015) argued that conservation biology must embrace mobile technology and citizen science platforms. The iNaturalist model demonstrated that gamified species documentation by non-experts significantly contributes to biodiversity databases.

## 8.3 Offline-First Architecture

Mehta et al. (2020) established design patterns for offline-first mobile applications, recommending local databases with JSON-seeded initial data, which is the approach adopted in Devara-Kaadu.

## 8.4 Jetpack Compose and Modern Android

Google's Android team (2021–2025) introduced Jetpack Compose as the recommended UI toolkit, replacing XML-based layouts. The declarative paradigm enables more maintainable, testable, and visually consistent applications. Material Design 3 (Material You) provides dynamic theming capabilities.

## 8.5 Room Database

The Android Architecture Components, including Room (first stable release 2017, continuous updates through 2025), provide an abstraction layer over SQLite, enabling compile-time verified SQL queries and reactive data access via Kotlin Flows.

## 8.6 Key References Summary

| Author(s) | Year | Contribution |
|-----------|------|-------------|
| Chandran & Hughes | 1997 | Documentation of sacred groves in Western Ghats |
| Bhagwat & Rutte | 2006 | Role of sacred groves in biodiversity conservation |
| Google Android Team | 2021+ | Jetpack Compose, Material 3, Room |
| Joppa | 2015 | Technology applications in conservation biology |
| Karnataka Biodiversity Board | 2019 | Sacred grove survey data for Karnataka |

---
---

# CHAPTER 9: EXISTING SYSTEM

## 9.1 Current Approaches

| Existing System | Limitations |
|----------------|-------------|
| **Karnataka Forest Department Records** | Paper-based, not accessible to public |
| **Academic Research Papers** | Focused on specific groves, not user-friendly |
| **General Nature Apps (iNaturalist, Seek)** | Require internet, no sacred grove focus, no cultural data |
| **Google Maps** | No sacred grove data, no biodiversity information |
| **Government Websites** | Not mobile-optimized, require internet, no offline capability |

## 9.2 Gaps in Existing Systems

1. No **offline-first** solution exists for sacred grove exploration
2. No platform combines **scientific** and **cultural/mythological** data
3. No **gamification** approach to engage youth in grove conservation
4. No **community threat reporting** mechanism for sacred groves
5. No **species identification** tool focused on sacred grove biodiversity

---
---

# CHAPTER 10: PROPOSED SYSTEM

## 10.1 System Overview

Devara-Kaadu is a **fully offline Android application** that addresses all identified gaps through:

1. **Local-first database** with Room, seeded from JSON assets — no internet required
2. **Dual-knowledge architecture** — every grove has both scientific and cultural data sections
3. **Guardian Mode** gamification with points, badges, and streak tracking
4. **Conservation Alert** system for offline threat documentation
5. **Simulated AI Species Scanner** demonstrating proof-of-concept identification
6. **GPS-based proximity detection** for nearby grove discovery

## 10.2 Key Differentiators

| Feature | Existing Apps | Devara-Kaadu |
|---------|--------------|--------------|
| Offline Operation | ❌ Most require internet | ✅ 100% offline |
| Sacred Grove Focus | ❌ Generic nature apps | ✅ Karnataka sacred groves |
| Cultural Narratives | ❌ Science-only | ✅ Mythology + Science |
| Gamification | ❌ Limited | ✅ Full badge system |
| Threat Reporting | ❌ Not available | ✅ Offline alert system |
| Kannada Language | ❌ English only | ✅ Trilingual species names |

---
---

# CHAPTER 11: FEASIBILITY STUDY

## 11.1 Technical Feasibility

- **Android SDK**: Mature platform with comprehensive Jetpack libraries — ✅ Feasible
- **Room Database**: Proven local storage solution for structured data — ✅ Feasible
- **GPS Services**: FusedLocationProviderClient available on all modern Android devices — ✅ Feasible
- **Jetpack Compose**: Production-ready UI toolkit since 2021 — ✅ Feasible
- **AI Species Scanner**: Full ML model not feasible in prototype scope — ⚠️ Simulated in current version

## 11.2 Economic Feasibility

- **Development Cost**: Minimal — uses free, open-source tools (Android Studio, Kotlin, Gradle)
- **Deployment Cost**: Zero server costs (offline-first architecture)
- **Maintenance Cost**: Low — data updates via app updates, no API maintenance
- **Total Budget**: ₹0 infrastructure cost (student project)

## 11.3 Operational Feasibility

- **Target Users**: Students, conservationists, forest department staff, tourists
- **Device Requirements**: Android 8.0+ (API 26) — covers 95%+ of active Android devices
- **Training**: Intuitive UI with onboarding screens — no training required
- **Adoption**: Offline capability enables use in remote grove locations

---
---

# CHAPTER 12: SWOT ANALYSIS

**Table 11.1: SWOT Analysis Matrix**

| Category | Details |
|----------|---------|
| **Strengths** | ✅ Fully offline operation — works in remote forest areas |
| | ✅ Combines scientific and cultural knowledge uniquely |
| | ✅ Gamified engagement through Guardian Mode |
| | ✅ Modern tech stack (Compose, Room, MVVM) |
| | ✅ Zero infrastructure cost |
| | ✅ Trilingual species data (English, Scientific, Kannada) |
| **Weaknesses** | ⚠️ Species scanner is simulated, not real AI |
| | ⚠️ Limited to 8 sample groves and 10 species |
| | ⚠️ No cloud sync — data stays on single device |
| | ⚠️ Settings toggles (dark mode, language) are UI-only prototypes |
| | ⚠️ No real image assets — uses emoji placeholders |
| **Opportunities** | 🌱 Integration with Karnataka Forest Department database |
| | 🌱 Real ML model integration for species identification |
| | 🌱 Cloud sync for community data sharing |
| | 🌱 Expansion to all 2,000+ sacred groves |
| | 🌱 Government adoption as official documentation tool |
| **Threats** | ⚡ Low smartphone penetration in rural Karnataka |
| | ⚡ Competing generic nature apps with larger budgets |
| | ⚡ Data staleness without update mechanism |
| | ⚡ GPS accuracy in dense forest canopy |

---
---

# CHAPTER 13: SYSTEM REQUIREMENTS

## 13.1 Hardware Requirements

**Table 4.1: Hardware Requirements**

| Component | Minimum | Recommended |
|-----------|---------|-------------|
| **Processor** | Quad-core 1.5 GHz | Octa-core 2.0 GHz+ |
| **RAM** | 2 GB | 4 GB+ |
| **Storage** | 100 MB free | 256 MB free |
| **Display** | 5.0" 720p | 6.0"+ 1080p |
| **GPS** | Required | Required |
| **Camera** | Optional (for future AI scan) | Recommended |
| **Development Machine** | 8 GB RAM, Intel i5 | 16 GB RAM, Intel i7/M-series |

## 13.2 Software Requirements

**Table 4.2: Software Requirements**

| Software | Version | Purpose |
|----------|---------|---------|
| **Android OS** | 8.0 (API 26) minimum | Runtime platform |
| **Android Studio** | Hedgehog 2023.1+ | Development IDE |
| **JDK** | 11+ | Kotlin compilation |
| **Gradle** | 8.5+ | Build system |
| **Kotlin** | 2.0+ | Programming language |
| **KSP** | Compatible with Kotlin version | Room annotation processing |
| **Git** | 2.0+ | Version control |

---
---

# CHAPTER 14: TECHNOLOGY STACK

**Table 3.1: Technology Stack Summary**

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Kotlin** | 2.0+ | Primary programming language |
| **Jetpack Compose** | BOM-managed | Declarative UI framework |
| **Material Design 3** | Latest | Design system and components |
| **Navigation Compose** | Latest | Screen routing and back stack |
| **Room Database** | Latest | Local SQLite abstraction |
| **KSP** | Latest | Compile-time annotation processing for Room |
| **Gson** | 2.10+ | JSON parsing for asset data |
| **Accompanist Permissions** | Latest | Runtime permission management |
| **WorkManager** | Latest | Background periodic tasks |
| **FusedLocationProvider** | Play Services | GPS location retrieval |
| **Core SplashScreen** | Latest | Android 12+ splash screen API |
| **Coil** | Latest | Image loading (included, minimal usage) |
| **DataStore Preferences** | Latest | Settings persistence (included) |
| **StateFlow + Coroutines** | Latest | Reactive state management |

## 14.1 Architecture Pattern: MVVM

The project implements the **Model-View-ViewModel** (MVVM) architecture pattern:

- **Model**: Room entities (`Grove`, `Species`, `Alert`, `UserProgress`) and repository classes
- **View**: Jetpack Compose screens (13 composable screens)
- **ViewModel**: 5 AndroidViewModel classes managing UI state via StateFlow

## 14.2 Build System

- **Gradle Kotlin DSL** with version catalog (`libs.versions.toml`)
- **KSP** plugin for Room compile-time code generation
- **Compile SDK**: 35 | **Target SDK**: 35 | **Min SDK**: 26

