# WLY Development Tasks for CPT204 Group Project

> Owner: **WLY**  
> Role: Task A sorting module, candidate-data processing, Task C data-structure explanation, and part of Task D reflection.  
> Time range: **2026-05-01 to 2026-05-22**  
> Purpose of this file: This Markdown file is a project-side task brief for development and documentation. It can be placed inside the code project so that team members or coding assistants can understand the required structure and constraints.

---

## Important Academic Integrity Note

This coursework permits only **non-substantive AI use**, such as formatting, grammar checking, generic suggestions, and planning support.

This file should be used as:
- a task checklist;
- a development specification;
- a reminder of required design and testing points;
- a guide for reviewing and explaining your own code.

Do **not** use AI tools to produce final core code that you cannot personally explain. WLY must be able to explain every class, method, algorithm, and result in person if asked.

---

# 1. WLY Overall Responsibility

WLY is mainly responsible for the **Task A sorting workflow** and related report/presentation content.

## WLY Main Scope

- Read and process:
  - `candidates_A.csv`
  - `candidates_B.csv`
  - `candidates_C.csv`
- Create the candidate-location data model.
- Implement candidate CSV reading.
- Implement three sorting algorithms:
  - Bubble Sort
  - Quick Sort
  - Merge Sort
- Apply the required ranking rule consistently.
- Measure and compare runtime.
- Extract Top 10 selected locations from each dataset.
- Provide results to XXY for Task B shortest-path cases.
- Write Chapter 1 of the report.
- Write the data-structure part of Chapter 3.
- Write the AI-assisted collaboration part of Chapter 4.
- Prepare and explain the first half of the presentation.

---

# 2. Core Sorting Rule

All sorting algorithms must use the same ranking rule:

1. Sort by `priority_score` in **descending order**.
2. If two locations have the same `priority_score`, sort by `location_id` in **ascending order**.

Example:

```text
L0002, 98.5
L0001, 98.5
L0003, 90.0
```

After sorting:

```text
L0001, 98.5
L0002, 98.5
L0003, 90.0
```

because `L0001` is smaller than `L0002` when the scores are equal.

---

# 3. Expected Project Structure for WLY Files

WLY should mainly work on these files:

```text
CPT204_Project/
├── src/
│   ├── Location.java
│   ├── CandidateCSVReader.java
│   ├── Sorter.java
│   ├── BubbleSort.java
│   ├── QuickSort.java
│   ├── MergeSort.java
│   ├── SortingExperiment.java
│   └── Main.java
├── data/
│   ├── candidates_A.csv
│   ├── candidates_B.csv
│   ├── candidates_C.csv
│   └── paths.csv
└── output/
    ├── sorting_results.txt
    └── path_results.txt
```

`Main.java` is a shared file with XXY, but WLY should ensure that the Task A sorting process can be called correctly from `Main`.

---

# 4. Coding Tasks for WLY

## WLY-1: Prepare Task A Data Model and Reading Logic

**Suggested period:** 2026-05-01 to 2026-05-03  
**Related files:**

```text
Location.java
CandidateCSVReader.java
```

## Task Details

- Read Task A requirements carefully.
- Confirm the three candidate input files:
  - `candidates_A.csv`
  - `candidates_B.csv`
  - `candidates_C.csv`
- Confirm the required ranking rule.
- Create `Location.java`.
- Create `CandidateCSVReader.java`.

## `Location.java` Requirements

The `Location` class should represent one candidate inspection location.

Suggested fields:

```java
private String locationId;
private double priorityScore;
```

Suggested methods:

```java
public Location(String locationId, double priorityScore)
public String getLocationId()
public double getPriorityScore()
public String toString()
```

Recommended comparison helper:

```java
public static int compare(Location a, Location b)
```

The comparison logic should return:

- negative value if `a` should appear before `b`;
- positive value if `a` should appear after `b`;
- zero if they are equal under the ranking rule.

## `CandidateCSVReader.java` Requirements

Suggested method:

```java
public static ArrayList<Location> readCandidates(String filePath)
```

This method should:

- open a candidate CSV file;
- skip the header row;
- read each line;
- split by comma;
- create a `Location` object;
- return `ArrayList<Location>`.

## Completion Criteria

- All three candidate files can be read successfully.
- Each candidate dataset should contain 1000 records.
- The first 5 records can be printed for checking.
- The ranking rule is written clearly in the team document.
- WLY and XXY both understand the ranking rule.

---

## WLY-2: Implement Sorting Interface and Bubble Sort

**Suggested period:** 2026-05-04 to 2026-05-06  
**Related files:**

```text
Sorter.java
BubbleSort.java
```

## Task Details

- Create the `Sorter` interface.
- Create the `BubbleSort` class.
- Make `BubbleSort` implement `Sorter`.
- Test Bubble Sort on small sample data.
- Test Bubble Sort on all three candidate datasets.

## `Sorter.java` Requirements

Suggested interface:

```java
public interface Sorter {
    void sort(ArrayList<Location> locations);
    String getName();
}
```

Purpose:

- Provide one common interface for all sorting algorithms.
- Support abstraction and polymorphism in the report.
- Allow the experiment code to run different sorting algorithms in the same way.

## `BubbleSort.java` Requirements

- Must implement Bubble Sort manually.
- Must not use Java built-in sorting methods to replace Bubble Sort.
- Must use the exact required ranking rule:
  - `priority_score` descending;
  - `location_id` ascending if scores are equal.

Suggested structure:

```java
public class BubbleSort implements Sorter {
    @Override
    public void sort(ArrayList<Location> locations) {
        // Bubble Sort implementation
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}
```

## Completion Criteria

- `Sorter` interface compiles successfully.
- `BubbleSort` compiles successfully.
- Bubble Sort correctly sorts a small manually created test list.
- Bubble Sort correctly sorts `candidates_A.csv`, `candidates_B.csv`, and `candidates_C.csv`.
- WLY can explain Bubble Sort's basic idea and time complexity.

---

## WLY-3: Implement Quick Sort, Merge Sort, and Top 10 Extraction

**Suggested period:** 2026-05-07 to 2026-05-09  
**Related files:**

```text
QuickSort.java
MergeSort.java
SortingExperiment.java
```

## Task Details

- Create `QuickSort.java`.
- Create `MergeSort.java`.
- Create or complete `SortingExperiment.java`.
- Run Bubble Sort, Quick Sort, and Merge Sort on all candidate datasets.
- Measure runtime.
- Extract Top 10 locations from each dataset.

## `QuickSort.java` Requirements

- Must implement Quick Sort manually.
- Must implement `Sorter`.
- Must not use Java built-in sorting as replacement.
- Must follow the same ranking rule.

Suggested helper methods:

```java
private void quickSort(ArrayList<Location> list, int low, int high)
private int partition(ArrayList<Location> list, int low, int high)
```

Potential issues to check:

- array index out of bounds;
- infinite recursion;
- incorrect handling of equal `priority_score`;
- inconsistent comparison logic.

## `MergeSort.java` Requirements

- Must implement Merge Sort manually.
- Must implement `Sorter`.
- Must not use Java built-in sorting as replacement.
- Must follow the same ranking rule.

Suggested helper methods:

```java
private void mergeSort(ArrayList<Location> list, int left, int right)
private void merge(ArrayList<Location> list, int left, int mid, int right)
```

Potential points to explain later:

- Merge Sort has stable `O(n log n)` time complexity.
- Merge Sort usually needs extra memory for merging.

## `SortingExperiment.java` Requirements

Suggested responsibilities:

- load each candidate dataset;
- copy the original list before each sorting run;
- run each sorting algorithm;
- measure runtime;
- optionally run each algorithm 3 times and use the average runtime;
- extract Top 10 selected locations;
- print or save results.

Suggested sorter list:

```java
ArrayList<Sorter> sorters = new ArrayList<>();
sorters.add(new BubbleSort());
sorters.add(new QuickSort());
sorters.add(new MergeSort());
```

Suggested timing method:

```java
long start = System.nanoTime();
sorter.sort(copy);
long end = System.nanoTime();
long duration = end - start;
```

## Important Experiment Rule

Do not sort the same already-sorted list repeatedly without copying the original data.

For fair timing, each algorithm should receive the same original dataset state.

Recommended process:

```text
Read original dataset
For each sorter:
    Create a copy of original dataset
    Start timer
    Sort the copy
    End timer
    Extract or verify Top 10
```

## Completion Criteria

- Quick Sort, Merge Sort, and Bubble Sort all produce the same sorted order.
- Dataset A, B, and C each have Top 10 selected locations.
- Runtime results are recorded for all three algorithms and all three datasets.
- The output can be used directly in Chapter 1.
- Top 10 results are shared with XXY for Task B.

---

## WLY-4: Integrate Task A with Main and Review XXY Code

**Suggested period:** 2026-05-10 to 2026-05-12  
**Related files:**

```text
Main.java
SortingExperiment.java
All WLY sorting files
```

## Task Details

- Work with XXY to integrate Task A and Task B.
- Ensure `Main.java` can call the sorting workflow.
- Ensure the final output includes:
  - Dataset A Top 10;
  - Dataset B Top 10;
  - Dataset C Top 10;
  - Bubble Sort runtime;
  - Quick Sort runtime;
  - Merge Sort runtime.
- Help verify that XXY can use WLY's Top 10 output for graph cases.
- Review XXY's graph-related code.

## WLY Should Review These XXY Files

```text
Edge.java
Graph.java
GraphCSVReader.java
DijkstraPathFinder.java
PathResult.java
```

## Review Focus

- Does `Graph` correctly represent an undirected weighted graph?
- Does `GraphCSVReader` read `paths.csv` correctly?
- Does Dijkstra handle start node equal to end node?
- Does path stitching avoid duplicate waypoint nodes?
- Can XXY explain the implementation clearly?

## Completion Criteria

- `Main.java` can run the complete workflow.
- Sorting results and path results are consistent.
- At least 5 code review notes or confirmations are recorded.
- WLY can explain the main graph workflow at a high level.
- No prohibited third-party libraries are used.

---

# 5. Report and Presentation Tasks for WLY

## WLY-5: Write Chapter 1 and Data Structure Section

**Suggested period:** 2026-05-13 to 2026-05-15  
**Related report sections:**

```text
Chapter 1 - Sorting Algorithm
Chapter 3 - Data Structure section
```

## Chapter 1 Suggested Structure

```text
1.1 Task A Objective
1.2 Ranking Rule
1.3 Sorting Algorithms Implemented
1.4 Timing Method
1.5 Experimental Results
1.6 Performance Analysis
1.7 Algorithm Choice Discussion
```

## Chapter 1 Must Answer

- How does the initial order of input data affect Bubble Sort, Quick Sort, and Merge Sort?
- Which sorting algorithm performs best on each dataset?
- Which sorting algorithm behaves most consistently across the three datasets?
- If only one sorting algorithm could be used in the final system, which one would be chosen?
- If the number of candidate locations became much larger, which algorithm would be most suitable?
- If both runtime efficiency and memory usage are considered, how would the choice be affected?

## Chapter 3 Data Structure Section Should Explain

- Why `ArrayList<Location>` is used for candidate datasets.
- Why `ArrayList` is suitable for sorting.
- Why the graph is represented with an adjacency list.
- How Task A Top 10 results are passed to Task B.
- How the chosen data structures support efficiency and clarity.

## Completion Criteria

- Chapter 1 contains a complete result table.
- Chapter 1 includes analysis, not only outputs.
- Chapter 3 contains a clear data-structure explanation.
- The writing style is consistent with XXY's report sections.

---

## WLY-6: Write AI Collaboration Reflection and Check Own Code Section

**Suggested period:** 2026-05-16 to 2026-05-18  
**Related report sections:**

```text
Chapter 4 - AI-assisted planning and collaboration
Chapter 5 - Program Code
```

## Chapter 4 AI Collaboration Reflection

WLY should write the AI-assisted planning and collaboration part.

Possible points:

- The team used Jira to create task cards.
- Tasks were assigned to WLY and XXY.
- Deadlines were set for each stage.
- The board status columns helped track progress:
  - To Do
  - In Progress
  - Review
  - Done
- Comments were used to record problems, questions, and updates.
- Advantages:
  - clearer task allocation;
  - easier deadline tracking;
  - better progress visibility;
  - easier team communication.
- Disadvantages:
  - task boards still require manual updates;
  - over-reliance may reduce active communication;
  - AI or automation suggestions may be too generic;
  - final decisions still require human judgement.

## Chapter 5 WLY Code Section

WLY should check that the following files are included as text in Chapter 5:

```text
Location.java
CandidateCSVReader.java
Sorter.java
BubbleSort.java
QuickSort.java
MergeSort.java
SortingExperiment.java
```

## Completion Criteria

- AI collaboration reflection is specific to this project.
- The report does not claim that AI generated core code or core report content.
- WLY's code is included in Chapter 5 as text, not screenshots.
- Chapter 5 code matches the final ZIP version.

---

## WLY-7: Help Create PPT and Prepare First-Half Script

**Suggested period:** 2026-05-19 to 2026-05-21  
**Related files:**

```text
PPT presentation
Video script
MP4 video
```

## WLY Presentation Scope

WLY should mainly present:

- project background;
- Task A sorting algorithms;
- Task A ranking rule;
- Task A runtime results;
- Task A Top 10 selection;
- data structure design;
- transition to XXY's graph algorithm part.

## Suggested WLY Speaking Time

```text
3.5 to 4 minutes
```

## Suggested WLY Script Structure

```text
1. Project background - about 40 seconds
2. Task A sorting rule and algorithms - about 2 minutes
3. Task A results and analysis - about 1 minute
4. Data structure design and transition - about 30 seconds
```

## Completion Criteria

- WLY's slides are consistent with the report.
- WLY's spoken explanation matches the PPT.
- WLY's speaking time is controlled within 4 minutes.
- WLY appears on camera and uses own voice.
- The final video does not exceed 8 minutes in total.

---

# 6. Final Submission Check for WLY

**Suggested date:** 2026-05-22

WLY should help XXY check the final four submission files:

```text
1. Java ZIP file
2. Word report
3. MP4 video
4. PPT file
```

## WLY Final Checklist

## Java ZIP

- All `.java` files are included.
- The project can compile.
- The program can run.
- Candidate CSV reading works.
- Sorting outputs are correct.

## Word Report

- Chapter 1 is complete.
- Chapter 3 data-structure section is included.
- Chapter 4 AI collaboration reflection is included.
- Chapter 5 includes WLY's code as text.
- Formatting is correct:
  - Calibri
  - font size 12
  - line spacing 1.5
  - normal margins.

## MP4 Video

- WLY appears on camera.
- WLY uses own voice.
- WLY explains Task A clearly.
- Total video length is below 8 minutes.

## PPT

- Task A slides are included.
- Results match the report.
- Slides are clear and not too text-heavy.

## Completion Criteria

- WLY and XXY each check the final files once.
- All four files can be opened.
- Results in code output, report, PPT, and video are consistent.
- Final version is ready for submission.

---

# 7. WLY Quick Development Checklist

Use this list during coding.

## Data Model

- [ ] `Location.java` created.
- [ ] `locationId` field added.
- [ ] `priorityScore` field added.
- [ ] Constructor implemented.
- [ ] Getters implemented.
- [ ] `toString()` implemented.
- [ ] Shared comparison logic implemented.

## CSV Reading

- [ ] `CandidateCSVReader.java` created.
- [ ] Header row skipped.
- [ ] CSV lines parsed correctly.
- [ ] `ArrayList<Location>` returned.
- [ ] Dataset A reads 1000 rows.
- [ ] Dataset B reads 1000 rows.
- [ ] Dataset C reads 1000 rows.

## Sorting

- [ ] `Sorter.java` created.
- [ ] `BubbleSort.java` created.
- [ ] `QuickSort.java` created.
- [ ] `MergeSort.java` created.
- [ ] All sorting classes implement `Sorter`.
- [ ] No Java built-in sort used to replace required algorithms.
- [ ] All algorithms use the same ranking rule.
- [ ] Small sample data test passed.
- [ ] Dataset A sorting passed.
- [ ] Dataset B sorting passed.
- [ ] Dataset C sorting passed.

## Experiment

- [ ] `SortingExperiment.java` created.
- [ ] Each algorithm receives a fresh copy of the original list.
- [ ] Runtime measured.
- [ ] Average runtime calculated if repeated runs are used.
- [ ] Top 10 extracted for Dataset A.
- [ ] Top 10 extracted for Dataset B.
- [ ] Top 10 extracted for Dataset C.
- [ ] Results shared with XXY.

## Integration

- [ ] Task A can be called from `Main.java`.
- [ ] Final console output is clear.
- [ ] Sorting results are saved or copied for report use.
- [ ] WLY can explain all sorting code.

---

# 8. Suggested Console Output Format

The program output for Task A can follow this style:

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
3. Lxxxx, score = xx.xx
4. Lxxxx, score = xx.xx
5. Lxxxx, score = xx.xx
6. Lxxxx, score = xx.xx
7. Lxxxx, score = xx.xx
8. Lxxxx, score = xx.xx
9. Lxxxx, score = xx.xx
10. Lxxxx, score = xx.xx
```

Repeat the same structure for Dataset B and Dataset C.

---

# 9. Key Points WLY Must Be Able to Explain

Before submission, WLY should be able to explain:

- What `Location` represents.
- How candidate CSV files are read.
- What the sorting rule is.
- Why the same comparison logic is reused.
- How Bubble Sort works.
- How Quick Sort works.
- How Merge Sort works.
- How runtime is measured.
- Why each algorithm should receive a fresh copy of the dataset.
- How Top 10 locations are extracted.
- How Task A output is passed to Task B.
- How `Sorter` supports abstraction and polymorphism.
- Why AI tools were used only for planning or formatting support.
