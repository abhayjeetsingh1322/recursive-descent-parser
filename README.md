# Recursive-Descent Parser for BL Language

## Description
This project implements a **recursive-descent parser** for the BL programming language. The parser:
1. Validates BL programs against a context-free grammar (CFG).
2. Identifies syntax errors and outputs descriptive error messages.
3. Constructs structured representations of BL programs and statements for further processing.

---

## Objectives
- Develop a parser using **recursive-descent parsing** techniques.
- Implement layered parsing methods for **Program** and **Statement** components.
- Create and execute a comprehensive, specification-based test plan for validation.

---

## Features
### 1. Program Parsing
The parser validates and processes the `program` non-terminal symbol in the BL grammar. Key features:
- Ensures program integrity, such as:
  - Matching identifiers at the beginning and end of definitions.
  - Unique naming of user-defined instructions.
  - Avoiding conflicts with primitive instruction names (`move`, `turnleft`, etc.).
- Constructs a structured representation of the program for further compilation stages.

### 2. Statement Parsing
The parser validates and processes the `statement` non-terminal symbol and its associated rules (`block`, `if`, `while`, and `call`). Key features:
- Constructs hierarchical representations of control structures and procedure calls.
- Handles recursive definitions for `block` and `statement` symbols.
- Efficiently detects and reports syntax errors.

### 3. Error Detection
The parser detects:
- Syntax violations based on the CFG for BL.
- Semantic errors such as duplicate instruction names or mismatched identifiers.

---

## Technologies Used
- **Java**: Implementation of the parser and supporting utilities.
- **JUnit**: Test framework for validating parsing methods.
- **Reporter Utility**: Handles error reporting with descriptive messages.

---

## How to Run
### Prerequisites
- Java Development Kit (JDK)
- Any Java-compatible IDE or terminal

### Steps
1. Clone the repository:
   ```bash
   git clone [repository URL]
