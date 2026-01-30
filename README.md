Enterprise-Grade Hybrid Automation Framework
Selenium + Java + Cucumber BDD + TestNG
📌 Project Overview
This repository contains a sophisticated, Hybrid BDD Automation Framework designed for high-stakes enterprise applications, specifically within the Financial Management System (FMS) domain. Drawing on over a decade of testing experience, this framework is engineered for resilience, scalability, and business-centric reporting.

🏗 High-Level Architecture
=
The framework follows a strict Layered Architecture to decouple business intent from technical implementation:
Feature Layer (Gherkin): Readable test scenarios for non-technical stakeholders (BAs/POs).
Flow Layer (Orchestration): Represents end-to-end business processes without leaking UI logic.
Page Object Layer (POM): Acts as a UI Stabilizer, encapsulating all locators and defensive automation logic.
Factory/Util Layer: Centralized DriverFactory using ThreadLocal for parallel-ready, thread-safe execution.

🚀 Key Technical Features
=
Self-Healing Page Objects: Implemented a global UI Kill Switch using JavaScript to neutralize unpredictable enterprise pop-ups and ghost overlays that block standard Selenium execution.
Data-Driven Engine: Integrated Apache POI for Excel-driven testing, allowing management of large datasets without modifying code or feature files.
Advanced Reporting: Powered by Extent Reports, featuring phase-wise/module-wise summaries, step-level logs, and automatic screenshots on failure.
Parallel Execution Ready: Thread-safe design supports concurrent test runs to optimize CI/CD pipeline performance.

💼 Domain-Specific Engineering: Supplier Module
=
A highlight of this framework is the SupplierDetailsPage strategy:
Adaptive Interaction: Used JavaScript-based event dispatching (change, blur) to trigger complex backend validations that simple Selenium sendKeys() often missed.
Multi-Layer Save Strategy: Handles "silent failures" through a redundant save protocol (JS + Physical clicks) followed by explicit state verification.

🛠 Tech Stack
=
Language: Java 17+ 
Core: Selenium WebDriver 4.x 
BDD: Cucumber 7.x (Gherkin) 
Execution: TestNG, Maven 
Data/Reporting: Apache POI, Extent Reports 5.x 

🔧 How to Run
=
Clone the Repo: git clone [Your Repository URL]
Set Environment: Update config.properties with your target browser and URL.

Execute via Maven:
=
Bash
mvn test -Dcucumber.filter.tags="@smoke"
View Report: Open target/FMS-Reports/AutomationReport.html.
