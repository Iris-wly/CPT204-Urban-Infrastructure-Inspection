# CPT204 Urban Infrastructure Inspection

**Group project - Java - 2026**

## Project Overview

This project implements a two-task urban infrastructure inspection system:

- **Task A** - Rank candidate inspection locations by priority score using three sorting
  algorithms (Bubble Sort, Quick Sort, Merge Sort) and identify the Top 10 from each dataset.
- **Task B** - Build a weighted undirected graph from road network data and find shortest
  inspection routes between key locations using Dijkstra's algorithm.

## How to Compile and Run

Compile:

```bash
javac -encoding UTF-8 -d out src/*.java
```

Run:

```bash
java -cp out Main
```

Output is printed to the console and also saved to `output/path_results.txt`.

## Source Files

| File | Role |
|------|------|
| `Main.java` | Entry point; runs Task A then Task B |
| `SortBenchmark.java` | Timed sorting experiment, extracts Top 10 |
| `BubbleSort.java` | Bubble Sort implementation |
| `QuickSort.java` | Quick Sort implementation |
| `MergeSort.java` | Merge Sort implementation |
| `Sorter.java` | Common sort interface |
| `Location.java` | Candidate location model and ranking comparator |
| `CandidateReader.java` | Reads candidates CSV |
| `Graph.java` | Adjacency-list weighted graph |
| `Edge.java` | Directed edge model |
| `GraphReader.java` | Reads paths CSV and builds graph |
| `Dijkstra.java` | Dijkstra shortest-path and waypoint stitching |
| `BellmanFord.java` | Bellman-Ford shortest-path implementation |
| `PathResult.java` | Shortest-path query result |

## Data Files (`data/`)

| File | Description |
|------|-------------|
| `candidates_A.csv` | Dataset A: location IDs and priority scores |
| `candidates_B.csv` | Dataset B |
| `candidates_C.csv` | Dataset C |
| `paths.csv` | Road network: `from, to, weight` edges |

## Final Experiment Results

**Confirmed by: WLY and XXY - 2026-05-14**

### Task A - Sorting Benchmark

#### Sorting Runtime (average over 3 runs)

| Algorithm   | Dataset A  | Dataset B  | Dataset C  |
|-------------|------------|------------|------------|
| Bubble Sort | 6.060 ms   | 2.030 ms   | 7.401 ms   |
| Quick Sort  | 7.783 ms   | 0.101 ms   | 1.580 ms   |
| Merge Sort  | 0.673 ms   | 0.485 ms   | 1.308 ms   |

Merge Sort is consistently the fastest because it always runs in O(n log n) regardless
of input order. Bubble Sort and Quick Sort vary because their performance depends on the
initial arrangement of the data.

#### Top 10 Selected Locations

**Dataset A**

| Rank | Location ID | Priority Score |
|------|-------------|----------------|
| 1    | L0001       | 10000.00       |
| 2    | L0002       | 9999.00        |
| 3    | L0003       | 9998.00        |
| 4    | L0004       | 9997.00        |
| 5    | L0005       | 9996.00        |
| 6    | L0006       | 9995.00        |
| 7    | L0007       | 9994.00        |
| 8    | L0008       | 9993.00        |
| 9    | L0009       | 9992.00        |
| 10   | L0010       | 9991.00        |

**Dataset B**

| Rank | Location ID | Priority Score |
|------|-------------|----------------|
| 1    | L0101       | 10000.00       |
| 2    | L0102       | 9999.00        |
| 3    | L0103       | 9998.00        |
| 4    | L0104       | 9997.00        |
| 5    | L0105       | 9996.00        |
| 6    | L0106       | 9995.00        |
| 7    | L0107       | 9994.00        |
| 8    | L0108       | 9993.00        |
| 9    | L0109       | 9992.00        |
| 10   | L0110       | 9991.00        |

**Dataset C**

| Rank | Location ID | Priority Score |
|------|-------------|----------------|
| 1    | L0201       | 5000.00        |
| 2    | L0202       | 5000.00        |
| 3    | L0203       | 5000.00        |
| 4    | L0204       | 5000.00        |
| 5    | L0205       | 5000.00        |
| 6    | L0206       | 5000.00        |
| 7    | L0207       | 5000.00        |
| 8    | L0208       | 5000.00        |
| 9    | L0209       | 5000.00        |
| 10   | L0210       | 5000.00        |

Dataset C contains many locations tied at score 5000.00. The tie-breaking rule
(ascending location ID) selects L0201-L0210 as the Top 10.

### Task B - Shortest Path Results

**Graph:** 1000 nodes, 2600 edges (data/paths.csv)

**Key nodes from Task A:**

| Label | Location ID |
|-------|-------------|
| A1    | L0001       |
| A10   | L0010       |
| B1    | L0101       |
| B5    | L0105       |
| C1    | L0201       |
| C5    | L0205       |

#### Path Cases

| Case | Start | Waypoints       | Destination | Total Cost |
|------|-------|-----------------|-------------|------------|
| 1    | L0001 | None            | L0001       | **0**      |
| 2    | L0001 | None            | L0010       | **27**     |
| 3    | L0001 | L0105           | L0101       | **39**     |
| 4    | L0001 | L0105 -> L0205  | L0201       | **48**     |

**Case 2 path:**
`L0001 -> L0340 -> L0339 -> L0895 -> L0894 -> L0082 -> L0284 -> L0010`

**Case 3 path:**
`L0001 -> L0340 -> L0339 -> L0247 -> L0017 -> L0128 -> L0107 -> L0106 -> L0105 -> L0106 -> L0107 -> L0108 -> L0827 -> L0996 -> L0101`

**Case 4 path:**
`L0001 -> L0340 -> L0339 -> L0247 -> L0017 -> L0128 -> L0107 -> L0106 -> L0105 -> L0106 -> L0107 -> L0108 -> L0243 -> L0242 -> L0241 -> L0385 -> L0205 -> L0201`

Locked final result: 2026-05-14. All report, PPT, and video materials reference this version.
