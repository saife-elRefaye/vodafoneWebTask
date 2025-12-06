# Vodafone Web Automation Suite

This project is a **Web Automation Testing Framework** built using **Java**, **Cucumber (BDD)**, and **Selenium WebDriver**. It automates user scenarios for browsing and purchasing items on e-commerce platforms like Noon.

---

## 📂 Project Structure
```
vodafoneWebTask/
│
├── src/test/java/org/vodafoneWeb/
│   ├── hooks/                      # Cucumber hooks for setup and teardown
│   ├── pages/                      # Page Object Model classes
│   │   ├── categoryPage.java
│   │   ├── filterPage.java
│   │   ├── homePage.java
│   │   ├── ResultPage.java
│   │   ├── SearchPage.java
│   │   └── ShoppingCartHelper.java
│   ├── runners/                    # Test runner classes
│   │   └── TestRunner.java
│   └── steps/                      # Step definitions for Cucumber scenarios
│       └── NoonSteps.java
│
├── resources/
│   ├── buyheadphones.feature       # Cucumber feature file for buying headphones
│   ├── invalidsearch.feature       # Feature file for invalid search scenarios
│   └── openElectronics.feature     # Feature file for electronics category
│
├── pom.xml                         # Maven dependencies and build configuration
└── target/                         # Compiled classes and reports
```

---

## ✅ Features
- **Behavior-Driven Development (BDD)** using Cucumber.
- **Page Object Model (POM)** for maintainable and reusable code.
- **Data-Driven Testing** with Examples in feature files.
- Supports **Regression** and **Smoke** test tags.

---

## ⚙️ Prerequisites
- **Java 11+**
- **Maven 3+**
- **Cucumber**
- **Selenium WebDriver**
- **ChromeDriver** (or any browser driver)

---

## 🚀 How to Run
1. **Install dependencies**:
   ```bash
   mvn clean install
   ```

2. **Run tests using Maven**:
   ```bash
   mvn test
   ```

3. **Run specific tags** (e.g., Regression):
   ```bash
   mvn test -Dcucumber.filter.tags="@Regression"
   ```

---

## 🧪 Test Execution
- Feature files define scenarios in Gherkin syntax:
  ```gherkin
  Scenario: Browse noon and search for headsets
    Given User opens the browser
    When User navigates to the homepage
    And User searches for "headphones"
    Then User verifies the cart has 3 items priced from 490 to 510
  ```

---

## 📊 Reporting
- Cucumber generates HTML reports in the `target` folder.
- Integrate with **Allure** or **Extent Reports** for advanced reporting.

---

## 💡 Best Practices
- Use **Page Object Model** for better maintainability.
- Keep feature files simple and readable.
- Tag scenarios for selective execution.
