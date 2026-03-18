# Percolation Threshold Simulation (Data Structures and Algorithms)

<div align="center">
  
  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
  ![Data Structures](https://img.shields.io/badge/Data_Structure-Union_Find-007396?style=for-the-badge)
  ![Algorithm](https://img.shields.io/badge/Algorithm-Amortized_O(1)-success?style=for-the-badge)
  ![Academic](https://img.shields.io/badge/Academic_Project-2nd_Year-purple?style=for-the-badge)

</div>

> **REFACTOR:**
> This repository contains the evolved, production-ready version of my Data Structures university assignment. 
> The original legacy code (which relied on a highly inefficient recursive DFS approach) has been completely rewritten using an optimized **Disjoint-Set (Union-Find)** architecture, allowing it to process massive datasets that would otherwise crash the JVM stack.

---

## About the Project

This project models a physical percolation system (often used in chemistry and materials science to model porosity, electrical conductivity, or fluid flow). The system simulates an $N \times N$ grid of insulating materials. Over time, "cosmic rays" strike random cells, transmuting them into electrical conductors. 

The primary objective of the algorithm is to detect the exact moment a contiguous path of conducting cells connects the top row to the bottom row (a short circuit or "percolation"), utilizing a **Monte Carlo Simulation**.

<img width="827" height="302" alt="image" src="https://github.com/user-attachments/assets/a8a8784d-e533-41f9-9b53-262ea6e21186" />




<img width="568" height="392" alt="image" src="https://github.com/user-attachments/assets/0b1c950c-cb45-4d86-8689-8111c62f8c33" />

### Tech Stack
- **Language:** Java 11+
- **Paradigm:** Object-Oriented Programming (OOP) & Algorithmic Optimization
- **Core Algorithm:** Union-Find (Disjoint-Set)

### Key Technical Optimizations
* **Path Compression:** Flattens the structure of the tree whenever `find()` is called, keeping the tree almost completely flat.
* **Union by Size:** Links smaller trees below larger ones, ensuring the tree height grows logarithmically rather than linearly.
* **Virtual Nodes Concept:** Instead of iterating through the entire top row and bottom row to check for connections (which requires $O(N)$ time per check), the grid is augmented with a **Virtual Top Node** and a **Virtual Bottom Node**. Percolation is instantly detected in $O(1)$ time by checking if `connected(VirtualTop, VirtualBottom)` evaluates to true.
* **8-Way Directional Adjacency:** Safely handles array boundary conditions while checking for contiguous conducting paths across all cardinal and diagonal directions.

---

## Performance Benchmark Suite

To empirically demonstrate the mathematical superiority of the Union-Find architecture over the legacy approach, a benchmarking suite is included in the source code.

The benchmark pits both algorithms against the exact same randomized cosmic ray strikes on increasingly massive grids ($N=10$ up to $N=1000$). You will observe the Legacy DFS algorithm slowing down exponentially and eventually crashing (`StackOverflowError`) due to extreme recursion depth, while the Optimized Union-Find resolves massive 1,000,000-cell grids in mere milliseconds.

---

## How to Run Locally

To execute the Monte Carlo simulation or the Benchmark suite on your machine:

**1. Clone the repository:**
```bash
git clone [https://github.com/YOUR_USERNAME/Percolation-Threshold-Simulation.git](https://github.com/YOUR_USERNAME/Percolation-Threshold-Simulation.git)
cd Percolation-Threshold-Simulation/src
```

**2. Compile the Java files:**

```bash
javac *.java
```

**3. Run the Monte Carlo Simulation:**

```bash
java Simulation
(This will generate a 10x10 grid, randomly strike cells, and halt the exact moment percolation is achieved, outputting the final grid state).
```

**4. Run the Performance Benchmark:**

```bash
java Benchmark
(This will execute the stress test comparing the Legacy DFS vs. Optimized Union-Find across massive matrices).
```

**Original Authors**

-Iván Moro Cienfuegos y Eric Soto San José
