# CPT204 Group Project Task Requirements

> Source file: **Group Project Task Sheet.pdf**  
> Course: **CPT204 AY2526**  
> Project type: **Two-person Java group project**  
> Final coursework weight: **40%**  
> Intended use of this Markdown file: place it inside the code project so that developers, reviewers, and coding assistants can understand the full coursework requirements, constraints, outputs, and report expectations.

---

## 0. Critical Academic Integrity Notice

The task sheet states that only **non-substantive AI use** is permitted.

Allowed AI-related uses include, for example:

- grammar correction;
- formatting adjustment;
- generic suggestions;
- clarity improvement;
- planning support;
- task breakdown support.

Important restrictions:

- Do not use AI to produce final core code or final report content that the team cannot personally explain.
- Keep unedited versions of your own work before AI-assisted polishing.
- The report appendix should include a brief citation of the AI tool name, version, and purpose of use.
- Individual students may be asked to explain parts of their code in person.
- If a student cannot demonstrate understanding of the code, no credit may be awarded for that part.

This Markdown file should therefore be used as a **requirements and checklist document**, not as permission to outsource the assignment.

---

# 1. Project Overview

## 1.1 Scenario

A city is planning a large-scale infrastructure inspection task.

Three different information sources provide candidate lists of locations that may require inspection. These datasets may have different properties, such as being ordered or unordered.

The program must:

1. process the candidate datasets;
2. identify important inspection targets;
3. compute routes or paths for visiting selected targets efficiently.

## 1.2 Application to Build

The team must design and implement an **object-oriented Java application** for an:

```text
Urban Infrastructure Inspection System
```

The system should integrate:

- object-oriented design principles;
- appropriate data structures;
- sorting algorithms;
- graph algorithms;
- performance analysis;
- project planning and reflection.

## 1.3 Required Submission Files

Four separate files must be submitted:

```text
1. Java code in a ZIP file
2. Word report
3. MP4 video presentation
4. PowerPoint presentation used in the video
```

## 1.4 Important Dates

```text
Task sheet release:
Week 9, Monday, 27 April, 09:00 UTC+8

Submission deadline:
Week 12, Sunday, 24 May, 23:59 UTC+8

Submission cut-off:
Week 13, Friday, 29 May, 23:59 UTC+8
```

Late submission policy:

```text
5% lateness penalty per working day
Maximum late period: 5 working days
No submissions accepted after 29 May, 23:59 UTC+8
```

---

# 2. Provided Data Files

## 2.1 Candidate Datasets

The project provides three candidate datasets:

```text
candidates_A.csv
candidates_B.csv
candidates_C.csv
```

Each dataset contains:

```text
1000 candidate locations
```

Each row contains:

```text
location_id
priority_score
```

Meaning:

- `location_id`: unique ID for a location;
- `priority_score`: priority for inspection;
- higher `priority_score` means higher inspection priority.

## 2.2 Weighted Graph File

The project also provides one undirected weighted graph edge file:

```text
paths.csv
```

Each row in `paths.csv` represents a weighted edge.

Each row contains:

```text
from_location
to_location
weight
```

Meaning:

- `from_location`: starting node of an edge;
- `to_location`: ending node of an edge;
- `weight`: distance or cost of the edge.

Important:

```text
paths.csv is an undirected weighted graph.
Each edge can be travelled in both directions.
```

---

# 3. Task A - Algorithm Evaluation: Sorting

## 3.1 Task A Objective

Task A requires evaluating the performance of three sorting algorithms for selecting high-priority inspection locations.

The three required algorithms are:

```text
Bubble Sort
Quick Sort
Merge Sort
```

The program must apply these algorithms to all three candidate datasets:

```text
candidates_A.csv
candidates_B.csv
candidates_C.csv
```

## 3.2 Required Ranking Rule

All sorting algorithms must use exactly the same ranking rule:

```text
1. Sort by priority_score in descending order.
2. If two rows have the same priority_score, sort by location_id in ascending order.
```

This means:

- higher priority score comes first;
- if scores are equal, smaller location ID comes first.

## 3.3 Required Program Behaviour

For each dataset, the program must:

```text
1. Read the dataset from the CSV file.
2. Sort the data using the required ranking rule.
3. Measure and compare the running time of Bubble Sort, Quick Sort, and Merge Sort.
4. Identify the top 10 highest-priority locations from that dataset.
```

After processing all three datasets, the program should obtain:

```text
10 selected locations from Dataset A
10 selected locations from Dataset B
10 selected locations from Dataset C
```

Total selected inspection targets:

```text
30 selected inspection targets
```

## 3.4 Timing Requirement

The report should include sorting time measurements.

Suggested Java timing methods:

```java
System.nanoTime()
System.currentTimeMillis()
```

To reduce measurement noise, the task sheet suggests running each sorting algorithm multiple times, for example:

```text
3 runs per algorithm
Report average runtime
```

## 3.5 Required Task A Report Output

The report must provide a table summarizing sorting results and timing performance.

Suggested table format:

| Dataset | Bubble (ns/ms) | Quick (ns/ms) | Merge (ns/ms) | Top 10 Selected Locations |
|---|---:|---:|---:|---|
| Dataset A |  |  |  |  |
| Dataset B |  |  |  |  |
| Dataset C |  |  |  |  |

## 3.6 Required Task A Analysis Questions

The report must analyze and compare the three sorting algorithms by answering:

1. How does the initial order of the input data affect the performance of Bubble Sort, Quick Sort, and Merge Sort on the three datasets?
2. Which of the three sorting algorithms performs best on each dataset? Explain with reference to data characteristics.
3. Which sorting algorithm behaves most consistently across the three datasets? Why?
4. If only one sorting algorithm were allowed in the final system, would the team choose:
   - the one with the best average runtime;
   - the one with the most stable behaviour;
   - or the simplest implementation?
   Explain why.
5. If the number of candidate locations became significantly larger, which sorting algorithm would be most suitable? Why?
6. If both runtime efficiency and memory usage are considered, how would the algorithm choice be affected?

---

# 4. Task B - Algorithm Evaluation: Graph

## 4.1 Task B Objective

After Task A, the team has 30 selected inspection targets:

```text
10 from Dataset A
10 from Dataset B
10 from Dataset C
```

Task B requires using these selected targets to identify several shortest paths on the weighted graph provided in:

```text
paths.csv
```

## 4.2 Important Graph Interpretation

The weighted graph in `paths.csv` defines the complete infrastructure network.

Important note:

```text
The 30 selected targets from Task A are important nodes within the graph.
They are not the only nodes in the graph.
Shortest paths may pass through other non-selected nodes.
```

## 4.3 Required Shortest-Path Cases

The report must provide results for the following four cases.

### Case 1

```text
From the 1st selected location in Dataset A to itself.
```

Expected conceptually:

```text
Path: A1
Total cost: 0
```

### Case 2

```text
From the 1st selected location in Dataset A
to the 10th selected location in Dataset A.
```

In shorthand:

```text
A1 -> A10
```

### Case 3

```text
From the 1st selected location in Dataset A
to the 1st selected location in Dataset B,
via the 5th selected location in Dataset B.
```

In shorthand:

```text
A1 -> B5 -> B1
```

This can be computed as:

```text
shortestPath(A1, B5)
shortestPath(B5, B1)
```

Then stitch the two path segments together.

### Case 4

```text
From the 1st selected location in Dataset A
to the 1st selected location in Dataset C,
such that the path must first pass through the 5th selected location in Dataset B,
and then through the 5th selected location in Dataset C, in this order.
```

In shorthand:

```text
A1 -> B5 -> C5 -> C1
```

This can be computed as:

```text
shortestPath(A1, B5)
shortestPath(B5, C5)
shortestPath(C5, C1)
```

Then stitch the three path segments together.

## 4.4 Required Output for Each Case

For each case, the report must provide:

```text
starting node
destination node
shortest path found by the program
total path cost or distance
```

For cases with required waypoints, the report should also clearly show:

```text
required waypoint(s)
```

Suggested output table:

| Case | Start Node | Destination Node | Required Waypoint(s) | Shortest Path | Total Cost |
|---|---|---|---|---|---|
| Case 1 | A1 | A1 | None |  |  |
| Case 2 | A1 | A10 | None |  |  |
| Case 3 | A1 | B1 | B5 |  |  |
| Case 4 | A1 | C1 | B5, C5 |  |  |

The report may either:

- use a table; or
- include relevant console output screenshots to show the program runs correctly.

## 4.5 Required Task B Analysis Questions

The report must evaluate the graph algorithm by answering:

1. What graph algorithm was used, and why is it suitable for the weighted graph in this coursework?
2. How is the chosen graph algorithm implemented in the program?
3. What are its time and space complexities in Big-O notation?
4. If each shortest-path query is solved optimally, does that mean the whole inspection-planning problem is solved optimally? Why or why not?
5. What alternative graph algorithms could be considered if the graph were unweighted? How might they compare with the current choice?
6. What alternative graph algorithms or route-planning approaches might become more suitable if:
   - the graph became much larger;
   - or node coordinates were available?
   Discuss advantages and limitations.

## 4.6 Recommended Algorithm

The task sheet does not force a single graph algorithm, but for an undirected weighted graph with non-negative distances, the most suitable standard choice is usually:

```text
Dijkstra's algorithm
```

Recommended implementation with:

```text
Adjacency list
PriorityQueue
HashMap for distances
HashMap for previous nodes
```

Expected complexity if implemented with adjacency list and priority queue:

```text
Time complexity: O((V + E) log V)
Space complexity: O(V + E)
```

---

# 5. Task C - Design of the Overall Application

## 5.1 Task C Objective

Task C requires explaining and justifying the design of the overall Urban Infrastructure Inspection System as a complete object-oriented Java application.

The report must cover:

```text
Data Structure
Classes and Functions
Object-Oriented Design
```

## 5.2 Data Structure Requirements

The report must answer:

1. What data structures were used to represent the three candidate datasets?
2. What data structures were used to represent the weighted graph in `paths.csv`?
3. Why are these data structures suitable for:
   - sorting;
   - graph traversal;
   - shortest-path computation?
4. How do these data structures support:
   - implementation efficiency;
   - implementation clarity?

Recommended structures:

```java
ArrayList<Location>
```

for candidate datasets.

```java
Map<String, ArrayList<Edge>>
```

or:

```java
HashMap<String, ArrayList<Edge>>
```

for the weighted graph adjacency list.

## 5.3 Classes and Functions Requirements

The report must answer:

1. What classes were used in the application?
2. What are the responsibilities of these classes?
3. What important public or private functions do these classes have?
4. How do these classes and functions collaborate to complete the full inspection workflow?

The report may use:

```text
UML diagrams
code screenshots
tables
```

## 5.4 Recommended Class Design

The following class design is recommended for a clear object-oriented implementation.

| Class / Interface | Suggested Responsibility |
|---|---|
| `Location` | Store `location_id` and `priority_score`. |
| `CandidateCSVReader` | Read candidate CSV files and return `ArrayList<Location>`. |
| `Sorter` | Common interface for sorting algorithms. |
| `BubbleSort` | Implement Bubble Sort. |
| `QuickSort` | Implement Quick Sort. |
| `MergeSort` | Implement Merge Sort. |
| `SortingExperiment` | Run sorting algorithms, measure time, and extract Top 10. |
| `Edge` | Represent a weighted edge in the graph. |
| `Graph` | Store the weighted graph using an adjacency list. |
| `GraphCSVReader` | Read `paths.csv` and build the graph. |
| `DijkstraPathFinder` | Compute shortest paths and waypoint paths. |
| `PathResult` | Store shortest path and total cost. |
| `Main` | Coordinate the full workflow. |

## 5.5 Object-Oriented Design Requirements

The report must discuss object-oriented principles such as:

```text
Encapsulation
Inheritance
Polymorphism
Abstraction
```

The report must explain:

1. How these principles are applied specifically.
2. How polymorphism supports different behaviours through a common interface or abstraction.
3. Why these OOP principles are important to the program.

Suggested examples:

## Encapsulation

Fields should be private.

Example:

```java
private String locationId;
private double priorityScore;
```

Access should be provided through methods such as:

```java
getLocationId()
getPriorityScore()
```

## Abstraction

Use `Sorter` as an interface:

```java
public interface Sorter {
    void sort(ArrayList<Location> locations);
    String getName();
}
```

## Polymorphism

Different sorting algorithms can be used through the same type:

```java
Sorter sorter = new QuickSort();
sorter.sort(locations);
```

## Interface Implementation

Sorting classes implement the common interface:

```java
BubbleSort implements Sorter
QuickSort implements Sorter
MergeSort implements Sorter
```

---

# 6. Task D - Project Reflection

## 6.1 Task D Objective

Task D requires reflection beyond technical implementation.

The report should show:

- what the team learned;
- how the team used available tools and resources;
- how the team planned, developed, and evaluated the project;
- how the team understands broader software development considerations.

## 6.2 AI-Assisted Planning and Collaboration

The report must discuss:

1. How AI tools or project management tools, such as JIRA and Trello, were used to help planning and collaboration.
2. How they supported task allocation.
3. The advantages and disadvantages of AI-empowered project management tools.

Possible project-specific points:

```text
The team used a Jira-style task board.
Tasks were divided between WLY and XXY.
Deadlines were assigned to each task.
Task status was tracked with To Do, In Progress, Review, and Done.
Comments were used to record questions and updates.
```

Possible advantages:

- clearer task allocation;
- easier progress tracking;
- better deadline management;
- easier collaboration;
- more transparent workload distribution.

Possible disadvantages:

- tools require manual updates;
- task cards may become outdated;
- AI suggestions may be too generic;
- over-reliance may reduce direct communication;
- final decisions still require human judgement.

## 6.3 Equality, Diversity, and Inclusion

The report must discuss:

1. Understanding of equality, diversity, and inclusion in the project context.
2. Why these principles are important in software design.
3. How the current project could be optimized in the future based on EDI principles.
4. Challenges that may arise when applying these improvements.
5. How these challenges could be addressed.

Possible future improvements:

- text-to-speech for visually impaired users;
- clearer error messages;
- multilingual interface or documentation;
- color-blind-friendly visual design;
- simpler interface for non-technical users;
- keyboard accessibility;
- alternative text for diagrams or maps.

Possible challenges:

- additional development time;
- more testing requirements;
- difficulty supporting multiple languages;
- accessibility requirements may increase interface complexity;
- balancing simplicity with advanced functionality.

## 6.4 Life-Long Learning and Future Improvement

The report must discuss:

1. What the project taught the team about life-long learning in software development.
2. How each student contributed to advanced software components.
3. What the next step would be if the application were developed further.

Possible future improvements:

- graphical user interface;
- map visualization;
- database support;
- larger-scale route optimization;
- A* search if coordinates are available;
- better input validation;
- exportable reports;
- more advanced inspection route planning.

---

# 7. Task E - Project Presentation

## 7.1 Presentation Objective

Task E requires creating a PPT and video explanation to present the project clearly and succinctly.

The presentation should cover:

1. project purpose;
2. key objectives;
3. role of the three candidate datasets;
4. role of the weighted graph;
5. Task A sorting algorithms and Top 10 selection;
6. Task B graph algorithm and shortest-path cases;
7. Task C object-oriented design;
8. Task D reflection;
9. future development.

## 7.2 PPT Requirements

The PPT should support the video explanation.

Suggested slide structure:

| Slide | Content |
|---|---|
| 1 | Title and team members |
| 2 | Project background |
| 3 | Task A sorting algorithms |
| 4 | Task A results |
| 5 | Task B graph algorithm |
| 6 | Task B path results |
| 7 | Task C OOP design and UML |
| 8 | Task D reflection |
| 9 | Conclusion |

The PPT can use any template and is not limited to the XJTLU standard theme.

## 7.3 Video Requirements

The video must satisfy:

```text
MP4 format
Maximum length: 8 minutes
Recorded jointly by both students
Each student presents roughly half of the content
Both students must use their own voice
Both students must appear on camera
```

Important warning:

```text
Exceeding the time limit, using English audio translation software for narration,
or failing to show faces will result in 0 for Task E.
```

---

# 8. Report Requirements

## 8.1 Required Report Structure

The Word report must follow this structure:

```text
Coursework submission cover page
Chapter 1 - Sorting Algorithm (Task A)
Chapter 2 - Graph Algorithm (Task B)
Chapter 3 - Design of the Overall Application (Task C)
Chapter 4 - Project Reflection (Task D)
Chapter 5 - Program Code
Chapter 6 - Appendix
Chapter 7 - Contribution Form
```

## 8.2 Chapter 5 Code Requirement

Chapter 5 must include all code as text.

Important:

```text
Do not use screenshots for Chapter 5 code.
Copy and paste each source file content into the report.
```

## 8.3 Chapter 6 Appendix

The appendix may include supplementary sources, such as:

```text
console outputs
extra sorting results
shortest-path outputs
UML diagrams not included in the main text
AI use statement
```

## 8.4 Chapter 7 Contribution Form

The contribution form should indicate each student's contribution percentage.

Example:

| Student ID | Contribution |
|---|---:|
| WLY | 50% |
| XXY | 50% |

The total percentage must be:

```text
100%
```

## 8.5 Formatting Requirements

The Word report formatting must be:

```text
Font: Calibri
Font size: 12
Line spacing: 1.5
Margins: Normal
```

Page limit:

```text
Maximum 20 pages
Excluding cover page and Chapters 5-7
```

The report should consider using:

```text
images
tables
UML diagrams
screenshots of outputs
```

to improve readability.

---

# 9. Coding Restrictions

The task sheet states:

```text
Only libraries covered in CPT204 may be used.
This includes libraries introduced in class and/or in Liang's textbook, where applicable.
```

Important:

```text
Using third-party libraries not covered in CPT204 may result in penalties.
```

Recommended safe Java libraries include standard Java utility and IO classes, such as:

```java
java.util.ArrayList
java.util.HashMap
java.util.Map
java.util.PriorityQueue
java.util.Set
java.io.BufferedReader
java.io.FileReader
java.io.IOException
```

Avoid external third-party libraries unless explicitly approved by the module.

---

# 10. Submission Requirements

Four separate submission portals will be set up on LMO.

Submit:

```text
1. ZIP file containing all code files
2. Word report
3. MP4 video recording
4. PPT file used in the video recording
```

---

# 11. Marking Metrics

## 11.1 Project Report: Tasks A-D, 80/100

| Chapter | Weight | Main Criteria |
|---|---:|---|
| Chapter 1 - Task A | 20 | Correct sorting implementation, correct Top 10 selection, dataset-property analysis, algorithm choice justification. |
| Chapter 2 - Task B | 20 | Correct graph construction, correct graph algorithm application, correct required cases and outputs, algorithm analysis. |
| Chapter 3 - Task C | 20 | Clear application design, data structure justification, class/function explanation, OOP principles. |
| Chapter 4 - Task D | 20 | AI-assisted planning reflection, EDI awareness, life-long learning and team role, depth of reflection. |

## 11.2 Code and Presentation: 20/100

| Component | Weight | Main Criteria |
|---|---:|---|
| Chapter 5 - Code | 10 | Code clarity, organization, readability, correct sorting workflow, graph workflow, overall application logic, coherent OOP design. |
| PPT + Video | 10 | Logical presentation flow, coverage of key tasks, clarity and fluency, effective communication. |

---

# 12. Recommended Development Checklist

## 12.1 Task A Checklist

- [ ] Read `candidates_A.csv`.
- [ ] Read `candidates_B.csv`.
- [ ] Read `candidates_C.csv`.
- [ ] Create `Location`.
- [ ] Create candidate CSV reader.
- [ ] Implement common comparison rule.
- [ ] Implement Bubble Sort.
- [ ] Implement Quick Sort.
- [ ] Implement Merge Sort.
- [ ] Measure Bubble Sort runtime.
- [ ] Measure Quick Sort runtime.
- [ ] Measure Merge Sort runtime.
- [ ] Run all algorithms on Dataset A.
- [ ] Run all algorithms on Dataset B.
- [ ] Run all algorithms on Dataset C.
- [ ] Extract Dataset A Top 10.
- [ ] Extract Dataset B Top 10.
- [ ] Extract Dataset C Top 10.
- [ ] Save or print results.

## 12.2 Task B Checklist

- [ ] Read `paths.csv`.
- [ ] Build undirected weighted graph.
- [ ] Store graph using adjacency list.
- [ ] Implement shortest-path algorithm.
- [ ] Implement path result object.
- [ ] Implement waypoint path stitching.
- [ ] Run Case 1.
- [ ] Run Case 2.
- [ ] Run Case 3.
- [ ] Run Case 4.
- [ ] Save or print path results.

## 12.3 Task C Checklist

- [ ] Explain candidate dataset structure.
- [ ] Explain graph data structure.
- [ ] Explain all major classes.
- [ ] Explain collaboration between classes.
- [ ] Explain encapsulation.
- [ ] Explain abstraction.
- [ ] Explain polymorphism.
- [ ] Explain interface implementation.
- [ ] Add UML diagram if possible.

## 12.4 Task D Checklist

- [ ] Explain Jira/Trello-style planning.
- [ ] Explain task allocation.
- [ ] Explain progress tracking.
- [ ] Evaluate advantages of planning tools.
- [ ] Evaluate disadvantages of planning tools.
- [ ] Discuss EDI.
- [ ] Discuss future accessibility improvements.
- [ ] Discuss life-long learning.
- [ ] Discuss team contribution.
- [ ] Discuss future development.

## 12.5 Task E Checklist

- [ ] Create PPT.
- [ ] Prepare script.
- [ ] Both students speak.
- [ ] Both students appear on camera.
- [ ] Use own voice.
- [ ] Keep video under 8 minutes.
- [ ] Export MP4.
- [ ] Check PPT opens correctly.

---

# 13. Suggested End-to-End Program Workflow

A clear implementation can follow this workflow:

```text
1. Load candidate datasets A, B, and C.
2. For each dataset:
   a. Run Bubble Sort on a copy.
   b. Run Quick Sort on a copy.
   c. Run Merge Sort on a copy.
   d. Measure runtimes.
   e. Extract Top 10 selected locations.
3. Combine the selected targets conceptually as 30 important locations.
4. Load paths.csv.
5. Build the undirected weighted graph.
6. Identify:
   A1, A10, B1, B5, C1, C5.
7. Run the four required shortest-path cases.
8. Print or save sorting and graph results.
9. Use outputs in the report and presentation.
```

---

# 14. Suggested Console Output Format

## 14.1 Sorting Output

```text
==============================
Dataset A Sorting Results
==============================
Bubble Sort Average Time: xxx ns
Quick Sort Average Time: xxx ns
Merge Sort Average Time: xxx ns

Top 10 Selected Locations:
1. Lxxxx, score = xx.xx
2. Lxxxx, score = xx.xx
...
10. Lxxxx, score = xx.xx
```

Repeat for Dataset B and Dataset C.

## 14.2 Path Output

```text
==============================
Shortest Path Results
==============================

Case 1:
Start: Lxxxx
Destination: Lxxxx
Waypoints: None
Path: Lxxxx
Total Cost: 0

Case 2:
Start: Lxxxx
Destination: Lxxxx
Waypoints: None
Path: Lxxxx -> Lxxxx -> ...
Total Cost: xx.xx

Case 3:
Start: Lxxxx
Destination: Lxxxx
Waypoints: Lxxxx
Path: Lxxxx -> Lxxxx -> ...
Total Cost: xx.xx

Case 4:
Start: Lxxxx
Destination: Lxxxx
Waypoints: Lxxxx, Lxxxx
Path: Lxxxx -> Lxxxx -> ...
Total Cost: xx.xx
```

---

# 15. Important Things the Team Must Be Able to Explain

Before submission, both students should be able to explain:

- the overall purpose of the system;
- the meaning of each input file;
- the sorting rule;
- why three sorting algorithms are compared;
- how Top 10 locations are selected;
- how the graph is represented;
- why the graph is undirected and weighted;
- why shortest paths may pass through non-selected nodes;
- how each of the four cases is handled;
- how waypoint paths are stitched;
- why local shortest paths do not necessarily solve the whole inspection-planning problem globally;
- how classes collaborate;
- how OOP principles are used;
- how Jira/Trello-style planning helped teamwork;
- what EDI means in this software context;
- what future improvements are possible;
- what AI tools were used for and what they were not used for.

---

# 16. Notes for Coding Assistants or Review Tools

A coding assistant or code review tool reading this file should understand these constraints:

1. The required implementation language is Java.
2. The project must be object-oriented.
3. The required sorting algorithms must be manually implemented:
   - Bubble Sort
   - Quick Sort
   - Merge Sort
4. Java built-in sorting must not replace the required algorithms.
5. The same ranking rule must be used by all sorting algorithms.
6. The graph is undirected and weighted.
7. The 30 selected nodes are not the only graph nodes.
8. Shortest paths may pass through intermediate non-selected nodes.
9. Waypoint cases should be computed by segment shortest paths and path stitching.
10. Avoid third-party libraries not covered in CPT204.
11. The final code must be readable, organized, and explainable by the students.
12. Academic integrity restrictions must be respected.
