<div align="center">

<img src="app/src/main/res/drawable/aquaintel_logo.png" alt="AquaIntel Logo" width="150"/>

# 🌊 AquaIntel

### Real-time Groundwater Resource Evaluation using DWLR Data

*Empowering India's Groundwater Management through Intelligent Monitoring*

[![Smart India Hackathon 2025](https://img.shields.io/badge/SIH-2025-blue.svg)](https://www.sih.gov.in/)
[![Problem Statement](https://img.shields.io/badge/PS%20ID-25068-green.svg)](#problem-statement)
[![Ministry of Jal Shakti](https://img.shields.io/badge/Ministry-Jal%20Shakti-orange.svg)](#)
[![CGWB](https://img.shields.io/badge/Dept-CGWB-blue.svg)](#)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)

[Features](#-key-features) • [Architecture](#-architecture--tech-stack) • [Installation](#-installation) • [Screenshots](#-screenshots) • [Impact](#-impact)

---

</div>

## 📋 Table of Contents

- [Problem Statement](#-problem-statement)
- [Our Solution](#-our-solution)
- [Key Features](#-key-features)
- [Architecture & Tech Stack](#-architecture--tech-stack)
- [Installation](#-installation)
- [Project Structure](#-project-structure)
- [Unique Features](#-unique-features)
- [Impact & Benefits](#-impact--benefits)
- [Future Roadmap](#-future-roadmap)
- [Team](#-team)
- [License](#-license)

---

## 🎯 Problem Statement

**ID:** 25068  
**Title:** Real-time Groundwater Resource Evaluation using DWLR Data  
**Organization:** Ministry of Jal Shakti  
**Department:** Central Ground Water Board (CGWB)

### Background

India, home to 16% of the global population, possesses only **4% of the world's freshwater resources**. Groundwater is critical for:
- 🚰 **Drinking water** for millions
- 🌾 **Agricultural irrigation** (60% dependency)
- 🏭 **Industrial requirements**

With 5,260 DWLR (Digital Water Level Recorder) stations across India generating high-frequency data, there's an urgent need for a comprehensive platform to:
- 📊 Analyze real-time water level fluctuations
- 💧 Estimate dynamic recharge rates
- 🔍 Evaluate groundwater resources in real-time
- 🎯 Support informed decision-making

---

## 💡 Our Solution

**AquaIntel** is a next-generation mobile application designed to revolutionize groundwater management in India. By leveraging data from 5,260+ DWLR stations, AI-powered analytics, and intuitive visualization, we provide actionable insights to stakeholders, researchers, and policymakers.

### Vision

*"Making groundwater data accessible, actionable, and impactful for every Indian"*

---

## ✨ Key Features

### 🗺️ **1. Interactive National Map**
- **Live Station Monitoring:** Real-time visualization of all 5,260 DWLR stations
- **Geospatial Intelligence:** Interactive markers color-coded by aquifer stress levels
  - 🟢 **Green:** Low stress (Safe)
  - 🟡 **Yellow:** Medium stress (Semi-critical)
  - 🟠 **Orange:** High stress (Critical)
  - 🔴 **Red:** Critical stress (Over-exploited)
- **Advanced Search:** Filter stations by state, district, or station name
- **Detailed Station Info:** Tap any marker to view comprehensive station data

### 📊 **2. Real-Time Dashboard**
- **Live Metrics Display:**
  - Active station count
  - Average water levels (Below Ground Level)
  - State-wise statistics
  - Quick access cards for monitoring, analytics, and decisions
- **Smart Alerts:** Priority notifications for critical aquifer conditions
- **Conservation Tips:** AI-curated water-saving recommendations
- **Trend Indicators:** Visual representation of water level changes

### 🔮 **3. Advanced Forecasting Engine**
- **Predictive Analytics:** 7-day, 15-day, and 30-day water level forecasts
- **Interactive Charts:** Beautiful Vico charts with confidence intervals
- **Risk Assessment:** Automated evaluation of future groundwater stress
- **Seasonal Trends:** Historical pattern analysis for better planning
- **Multi-Station Comparison:** Compare forecasts across different locations

### 🤖 **4. AI-Powered Assistant (Gemini Integration)**
- **Intelligent Chatbot:** Powered by Google's Gemini 2.5 Flash model
- **Contextual Insights:** 
  - Explain complex groundwater data in simple terms
  - Provide region-specific analysis (Madhya Pradesh focused)
  - Answer queries about water quality, aquifer health, and conservation
- **Multilingual Support:** Designed for farmers and non-experts
- **Markdown Rendering:** Rich, formatted responses for better readability

### 📱 **5. Community Reporting System**
- **Crowd-Sourced Data:** Enable field-level reporting of groundwater issues
- **Report Categories:**
  - 🚨 Equipment malfunction
  - 💧 Water quality concerns
  - 📉 Unusual water level changes
  - 📋 Data inaccuracies
- **Severity Levels:** Low, Medium, High, Critical
- **Report Tracking:** View submission history and status updates

### 📈 **6. Detailed Station Analytics**
- **Time-Series Visualization:** Historical water level trends (7, 30, 90 days)
- **Rainfall Correlation:** Compare water levels with precipitation data
- **Aquifer Information:**
  - Aquifer type (Alluvial, Hard Rock, Coastal, etc.)
  - Current stress level
  - Recharge/depletion rates
- **Export Functionality:** Download data for further analysis

### 📄 **7. Comprehensive Report Generation**
- **Customizable Reports:**
  - Daily/Weekly/Monthly summaries
  - Station-specific analytics
  - Regional comparisons
  - Seasonal trend reports
- **PDF Export:** Professional reports for stakeholders
- **Data Visualization:** Charts, graphs, and statistical summaries

---

## 🏗️ Architecture & Tech Stack

### **Modern Android Architecture**

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │
│  (Jetpack Compose + Material Design 3)  │
├─────────────────────────────────────────┤
│           ViewModel Layer               │
│        (MVVM Architecture)              │
├─────────────────────────────────────────┤
│          Repository Layer               │
│      (Data Abstraction)                 │
├─────────────────────────────────────────┤
│           Data Sources                  │
│  (DWLR API • Gemini AI • Local Cache)  │
└─────────────────────────────────────────┘
```

### **Technology Stack**

#### **Core Technologies**
- **Language:** Kotlin (100% Kotlin codebase)
- **UI Framework:** Jetpack Compose (Modern declarative UI)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Dependency Injection:** Hilt/Dagger
- **Async Processing:** Kotlin Coroutines & Flow

#### **Key Libraries & APIs**

| Category | Technology | Purpose |
|----------|-----------|---------|
| **UI Components** | Material Design 3 | Modern, adaptive UI |
| **Navigation** | Navigation Compose | Type-safe navigation |
| **Charts & Visualization** | Vico Charts 2.0 | Beautiful, performant charts |
| **Maps** | Google Maps Compose | Interactive station mapping |
| **AI Integration** | Google Gemini AI 2.5 Flash | Intelligent chat assistant |
| **Image Loading** | Coil | Efficient image caching |
| **Data Persistence** | DataStore Preferences | Local data storage |
| **Markdown Rendering** | Compose Markdown | Rich text display |

#### **Design Patterns**
- ✅ Repository Pattern
- ✅ Singleton Pattern
- ✅ Observer Pattern (StateFlow)
- ✅ Dependency Injection
- ✅ Clean Architecture Principles

---

## 🚀 Installation

### Prerequisites
- **Android Studio:** Hedgehog (2023.1.1) or later
- **JDK:** 17+
- **Android SDK:** API 26+ (Target: API 34)
- **Gradle:** 8.12.3

### Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/techsavvy185/AquaIntel.git
   cd AquaIntel
   ```

2. **Configure API Keys**
   
   Create a `local.properties` file in the root directory:
   ```properties
   # Google Gemini AI API Key
   GEMINI_API_KEY=your_gemini_api_key_here
   
   # Google Maps API Key (also update in AndroidManifest.xml)
   MAPS_API_KEY=your_maps_api_key_here
   ```

3. **Sync Project**
   ```bash
   ./gradlew build
   ```

4. **Run the Application**
   - Connect an Android device (API 26+) or start an emulator
   - Click **Run** in Android Studio or execute:
   ```bash
   ./gradlew installDebug
   ```

### Build Variants
- **Debug:** Development build with logging
- **Release:** Production-ready build (ProGuard enabled)

---

## 📁 Project Structure

```
AquaIntel/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/aquaintel/app/
│   │   │   │   ├── data/                 # Data layer
│   │   │   │   │   ├── ai/              # Gemini AI service
│   │   │   │   │   ├── models/          # Data models
│   │   │   │   │   └── mock/            # Mock data (development)
│   │   │   │   ├── di/                  # Dependency injection
│   │   │   │   ├── repository/          # Repository pattern
│   │   │   │   ├── ui/                  # Presentation layer
│   │   │   │   │   ├── components/      # Reusable UI components
│   │   │   │   │   ├── navigation/      # App navigation
│   │   │   │   │   ├── screens/         # Feature screens
│   │   │   │   │   │   ├── auth/       # Authentication flow
│   │   │   │   │   │   └── main/       # Main app screens
│   │   │   │   │   └── theme/          # App theming
│   │   │   │   ├── AquaIntelApplication.kt
│   │   │   │   └── MainActivity.kt
│   │   │   └── res/                     # Resources
│   │   │       ├── drawable/           # Vector assets
│   │   │       ├── mipmap/             # App icons
│   │   │       └── values/             # Strings, colors, themes
│   │   └── test/                        # Unit tests
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml              # Version catalog
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🌟 Unique Features

### **1. Gemini AI Integration**
- First groundwater monitoring app with AI-powered insights
- Context-aware responses tailored to Indian hydrogeological conditions
- Simplified explanations for farmers and non-technical users
- Madhya Pradesh-focused regional analysis with detailed groundwater insights

### **2. Comprehensive Data Visualization**
- **Vico Charts:** High-performance, Material Design 3 compliant charts
- **Multi-layered Analysis:** Water levels, rainfall, predictions on single screen
- **Confidence Intervals:** Visual representation of forecast reliability
- **Responsive Design:** Adapts to different screen sizes

### **3. Community-Driven Approach**
- Empowers users to report ground-level observations
- Bridges gap between automated data and real-world conditions
- Creates collaborative ecosystem for groundwater management

### **4. Offline-First Architecture** (Future Enhancement)
- Local caching of critical data
- Seamless operation in low-connectivity areas
- Background sync when network available

### **5. State-Specific Intelligence**
- Customizable regional analysis
- State/district-level aggregations
- Local language support (planned)

### **6. Professional Report Generation**
- Export-ready PDF reports
- Customizable date ranges and metrics
- Suitable for policy presentations and research papers

---

## 🌍 Impact & Benefits

### **For Stakeholders**

#### 🎓 **Researchers & Scientists**
- Access to real-time, nationwide groundwater data
- Advanced analytical tools for research
- Historical trend analysis for publications
- API integration for custom analysis

#### 🏛️ **Policymakers & Government Officials**
- Evidence-based decision making
- Quick assessment of regional water stress
- Data-driven policy formulation
- Crisis prediction and early warning

#### 👨‍🌾 **Farmers & Local Communities**
- Simplified groundwater information
- AI-powered conservation tips
- Crop planning based on water availability
- Community reporting of local issues

#### 💼 **Water Resource Managers**
- Real-time monitoring dashboard
- Predictive analytics for resource allocation
- Multi-station comparative analysis
- Automated alerting system

### **Measurable Outcomes**

| Metric | Impact |
|--------|--------|
| **Data Accessibility** | 5,260+ stations in one app |
| **Response Time** | Real-time vs. days/weeks (traditional) |
| **User Reach** | Accessible to 1.4 billion+ Indians |
| **Decision Speed** | Instant insights vs. manual analysis |
| **Cost Efficiency** | Reduces need for physical monitoring |
| **Conservation Potential** | AI-driven tips for millions |

### **Alignment with SDGs**

- 🎯 **SDG 6:** Clean Water and Sanitation
- 🎯 **SDG 13:** Climate Action
- 🎯 **SDG 15:** Life on Land
- 🎯 **SDG 17:** Partnerships for the Goals

---


## 🧪 Testing & Quality Assurance

### **Testing Strategy**
- **Unit Tests:** ViewModel logic, repository methods
- **Integration Tests:** API interactions, database operations
- **UI Tests:** Compose UI testing framework
- **Manual Testing:** Real device testing across Android versions

### **Code Quality**
- **Lint Checks:** Android Studio lint analysis
- **Static Analysis:** Detekt for Kotlin
- **Code Coverage:** Target >80%
- **Performance Monitoring:** Android Profiler

---

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit changes** (`git commit -m 'Add AmazingFeature'`)
4. **Push to branch** (`git push origin feature/AmazingFeature`)
5. **Open a Pull Request**

### **Contribution Guidelines**
- Follow Kotlin coding conventions
- Write meaningful commit messages
- Add unit tests for new features
- Update documentation as needed

---

## 👥 Team

**Team Name:** BitBenders  
**Project Type:** Smart India Hackathon 2025 - Software  
**Category:** Miscellaneous  
**Problem Statement ID:** 25068

---

## 📜 License

This project is developed for Smart India Hackathon 2025.

**Copyright © 2025 AquaIntel Team**

---

## 📞 Contact & Support

- **GitHub Repository:** [techsavvy185/AquaIntel](https://github.com/techsavvy185/AquaIntel)
- **Issues:** [Report Bug/Request Feature](https://github.com/techsavvy185/AquaIntel/issues)
- **Documentation:** [Wiki](https://github.com/techsavvy185/AquaIntel/wiki)

---

## 🙏 Acknowledgments

- **Ministry of Jal Shakti** - For the problem statement and vision
- **Central Ground Water Board (CGWB)** - For DWLR infrastructure and data
- **Google** - For Gemini AI and Maps APIs
- **Smart India Hackathon** - For the platform to innovate
- **Open Source Community** - For amazing libraries and tools

---

<div align="center">

### 🌊 Join us in revolutionizing India's groundwater management! 🌊

**Made with ❤️ for a water-secure India**

[![Stars](https://img.shields.io/github/stars/techsavvy185/AquaIntel?style=social)](https://github.com/techsavvy185/AquaIntel)
[![Forks](https://img.shields.io/github/forks/techsavvy185/AquaIntel?style=social)](https://github.com/techsavvy185/AquaIntel)
[![Issues](https://img.shields.io/github/issues/techsavvy185/AquaIntel)](https://github.com/techsavvy185/AquaIntel/issues)

---

*"Every drop counts. Let's make every decision count too."*

</div>
