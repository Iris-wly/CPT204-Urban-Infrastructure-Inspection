# XXY Development Tasks for CPT204 Group Project

> Owner: **XXY**  
> Role: Task B shortest-path module, graph data-structure implementation, Task C graph-structure explanation (with WLY), and part of Task D reflection.  
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

Do **not** use AI tools to produce final core code that you cannot personally explain. XXY must be able to explain every class, method, algorithm, and result in person if asked.

---

# 1. XXY Overall Responsibility

XXY is mainly responsible for the **Task B shortest-path workflow** and related report/presentation content.

## XXY Main Scope

- Read and process:
  - `paths.csv`
- Create the graph data model (nodes and edges).
- Implement graph CSV reading.
- Implement Dijkstra's shortest-path algorithm.
- Run the four required path-finding cases using WLY's Top 10 results:
  - Case 1: A1 → A1 (trivial zero-cost case)
  - Case 2: A1 → A10 (direct shortest path)
  - Case 3: A1 → B5 → B1 (one required waypoint)
  - Case 4: A1 → B5 → C5 → C1 (two required waypoints)
- Record shortest path and total distance for each case.
- Save results to `output/path_results.txt`.
- Provide results to WLY for integration into the shared output.
- Review WLY's sorting-related code.
- Write Chapter 2 of the report.
- Write the graph-algorithm part of Chapter 3.
- Write part of Chapter 4 reflection.
- Prepare and explain the second half of the presentation.

---

# 2. Core Graph Representation and Required Cases

The graph is built from `paths.csv` and must follow these rules:

1. The graph is **undirected**: if an edge exists from A to B, it also exists from B to A.
2. Each edge has a positive integer **weight** (travel distance or cost).
3. The graph is represented using an **adjacency list** (`HashMap<String, ArrayList<Edge>>`).
4. Dijkstra must find the **minimum total weight** path between two nodes.
5. Shortest paths may pass through **non-selected locations** (i.e., nodes not in Top 10).

The graph contains 1000 nodes and 2600 undirected edges.

## Four Required Path Cases

| Case | Start | Destination | Required Waypoint(s) | Description |
|---|---|---|---|---|
| Case 1 | A1 | A1 | None | Trivial: start equals destination |
| Case 2 | A1 | A10 | None | Direct shortest path |
| Case 3 | A1 | B1 | **B5** | Must visit B5 before reaching B1 |
| Case 4 | A1 | C1 | **B5, C5** | Must visit B5, then C5, then C1 in order |

Cases 3 and 4 are computed by **path stitching**:
- Split the journey into sub-segments at each waypoint.
- Run Dijkstra independently on each sub-segment.
- Concatenate the results, removing the duplicate waypoint node at each junction.

Note: because sub-segments are solved independently, some intermediate nodes may appear more than once in the stitched path. This is expected and correct behaviour for the waypoint shortest-path approach.

---

# 3. Expected Project Structure for XXY Files

XXY should mainly work on these files:

```text
CPT204_Project/
├── src/
│   ├── Edge.java
│   ├── Graph.java
│   ├── GraphCSVReader.java
│   ├── DijkstraPathFinder.java
│   ├── PathResult.java
│   └── Main.java          ← shared with WLY; XXY adds the Task B section
├── data/
│   └── paths.csv
└── output/
    └── path_results.txt
```

`Main.java` is a shared file with WLY, but XXY should ensure that the Task B path-finding process can be called correctly from `Main` after WLY's Task A section runs.

---

# 4. Coding Tasks for XXY

## XXY-1: Prepare Graph Data Model

**Suggested period:** 2026-05-01 to 2026-05-03  
**Status: COMPLETED (2026-05-12)**  
**Related files:**

```text
Edge.java
Graph.java
```

## Task Details

- Understand the `paths.csv` format: `from_location, to_location, weight`.
- Create `Edge.java`.
- Create `Graph.java`.

## `Edge.java` Requirements

The `Edge` class represents one directed edge entry in the adjacency list.
Because the graph is undirected, each row in `paths.csv` produces **two** Edge objects.

Required fields:

```java
private String to;
private int weight;
```

Required methods:

```java
public Edge(String to, int weight)
public String getTo()
public int getWeight()
public String toString()
```

## `Graph.java` Requirements

The `Graph` class represents the full road network as an adjacency list.

Required field:

```java
private HashMap<String, ArrayList<Edge>> adjacencyList;
```

Required methods:

```java
public Graph()
public void addEdge(String fromId, String toId, int weight)
public ArrayList<Edge> getNeighbors(String nodeId)
public boolean containsNode(String nodeId)
public int getNodeCount()
public int getEdgeCount()
```

`addEdge()` must insert edges in **both directions** (undirected).  
`getEdgeCount()` must divide the total directed-edge count by 2.

## Completion Criteria

- `Edge` and `Graph` compile successfully.
- A manually added edge can be retrieved in both directions.
- XXY can explain why an adjacency list is used instead of an adjacency matrix.

---

## XXY-2: Implement Graph CSV Reading

**Suggested period:** 2026-05-04 to 2026-05-06  
**Status: COMPLETED (2026-05-12)**  
**Related files:**

```text
GraphCSVReader.java
```

## Task Details

- Create `GraphCSVReader.java`.
- Read `paths.csv` and build a `Graph` object.
- Verify that the graph has the correct number of nodes and edges.

## `GraphCSVReader.java` Requirements

Suggested method:

```java
public static Graph readGraph(String filePath)
```

This method should:

- open `paths.csv`;
- skip the header row;
- read each line;
- split by comma;
- call `graph.addEdge(from, to, weight)`;
- return the completed `Graph`.

## Completion Criteria

- `paths.csv` is read without errors.
- The graph reports **1000 nodes** and **2600 edges**.
- Node lookup (`containsNode`) works correctly for known IDs.
- XXY can explain what each column in `paths.csv` means.

---

## XXY-3: Implement Dijkstra's Algorithm

**Suggested period:** 2026-05-07 to 2026-05-09  
**Status: COMPLETED (2026-05-12)**  
**Related files:**

```text
DijkstraPathFinder.java
PathResult.java
```

## Task Details

- Create `PathResult.java`.
- Create `DijkstraPathFinder.java`.
- Test Dijkstra on small manually created graphs.
- Test Dijkstra on the full `paths.csv` graph.

## `PathResult.java` Requirements

Suggested fields:

```java
private String startId;
private String endId;
private int totalDistance;
private ArrayList<String> path;
```

Suggested methods:

```java
public PathResult(String startId, String endId, int totalDistance, ArrayList<String> path)
public String getStartId()
public String getEndId()
public int getTotalDistance()
public ArrayList<String> getPath()
public String toString()
```

`toString()` should show start, end, total distance, and the full ordered node list.  
If `totalDistance` is -1, it means no path was found.

## `DijkstraPathFinder.java` Requirements

Two public methods are required:

**Method 1: single-segment shortest path**

```java
public static PathResult findShortestPath(Graph graph, String startId, String endId)
```

Internal design:

- Use a private `Entry` class (nodeId + distance) that implements `Comparable<Entry>` so that `PriorityQueue<Entry>` acts as a min-heap.
- Use `HashMap<String, Integer> dist` to track best-known distances.
- Use `HashMap<String, String> prev` to track predecessors for path reconstruction.
- Skip stale queue entries by checking `current.distance > dist.get(current.nodeId)`.
- Stop early once the destination node is dequeued.
- Handle the special case where `startId.equals(endId)`: return distance 0, path = [startId].
- Reconstruct the path by walking `prev` backwards from `endId` to `startId`, then reverse the list.

**Method 2: multi-waypoint path stitching**

```java
public static PathResult findPathWithWaypoints(Graph graph, String[] nodeIds)
```

- `nodeIds` contains `[start, waypoint1, waypoint2, ..., end]`.
- Calls `findShortestPath` on each consecutive pair of nodes.
- Stitches results together: the first node of each segment after the first is skipped to avoid duplicating the waypoint junction.
- Sums up total distances across all segments.
- If any segment has no path, returns `totalDistance = -1`.

Potential issues to check:

- incorrect predecessor reconstruction leading to wrong path order;
- not handling the start == end case;
- forgetting to reverse the reconstructed path;
- stale entries causing wrong distance results;
- off-by-one error in the stitching loop (must skip index 0 of segments after the first, not index 1).

## Completion Criteria

- Dijkstra produces the correct shortest distance on a small hand-verified graph.
- Dijkstra handles the case where start equals end.
- The full path node list is in the correct order from start to end.
- `findPathWithWaypoints` correctly stitches two or three segments without duplicating waypoints.
- XXY can explain how the priority queue ensures the closest node is always expanded first.
- XXY can explain the stale-entry skip and why it is correct.
- XXY can explain why waypoint paths may revisit some intermediate nodes.

---

## XXY-4: Integrate Task B with Main and Review WLY Code

**Suggested period:** 2026-05-10 to 2026-05-12  
**Status: COMPLETED (2026-05-12)**  
**Related files:**

```text
Main.java
All XXY graph files
All WLY sorting files
```

## Task Details

- Work with WLY to integrate Task A and Task B.
- Receive the six key node IDs from WLY's Top 10 results.
- Ensure `Main.java` calls `GraphCSVReader.readGraph()` and `DijkstraPathFinder.findShortestPath()` for all three cases.
- Print path results to the console.
- Save path results to `output/path_results.txt`.
- Review WLY's sorting-related code.

## Actual Results (Confirmed 2026-05-12)

Node IDs in use: A1=L0001, A10=L0010, B1=L0101, B5=L0105, C1=L0201, C5=L0205

```text
Graph loaded: 1000 nodes, 2600 edges

Case 1:
Start: L0001
Destination: L0001
Waypoints: None
Path: L0001
Total Cost: 0

Case 2:
Start: L0001
Destination: L0010
Waypoints: None
Path: L0001 -> L0340 -> L0339 -> L0895 -> L0894 -> L0082 -> L0284 -> L0010
Total Cost: 27

Case 3:
Start: L0001
Destination: L0101
Waypoints: L0105
Path: L0001 -> L0340 -> L0339 -> L0247 -> L0017 -> L0128 -> L0107 -> L0106 -> L0105
      -> L0106 -> L0107 -> L0108 -> L0827 -> L0996 -> L0101
Total Cost: 39

Case 4:
Start: L0001
Destination: L0201
Waypoints: L0105, L0205
Path: L0001 -> L0340 -> L0339 -> L0247 -> L0017 -> L0128 -> L0107 -> L0106 -> L0105
      -> L0106 -> L0107 -> L0108 -> L0243 -> L0242 -> L0241 -> L0385 -> L0205 -> L0201
Total Cost: 48
```

Note on Cases 3 and 4: some intermediate nodes (e.g. L0106, L0107) appear more than once because the path is stitched from independent sub-segments. This is correct and expected behaviour when using the segment-and-stitch approach required by the coursework.

## XXY Should Review These WLY Files

```text
Location.java
CandidateCSVReader.java
Sorter.java
BubbleSort.java
QuickSort.java
MergeSort.java
SortingExperiment.java
```

## Review Focus

- Does `Location.compare()` correctly implement the ranking rule (descending score, ascending ID)?
- Does `CandidateCSVReader` skip the header row and parse both fields correctly?
- Do all three sorting algorithms produce the same sorted order?
- Does `SortingExperiment` give each algorithm a fresh copy of the dataset for fair timing?
- Can WLY explain the implementation clearly?

## Completion Criteria

- `Main.java` can run the complete workflow (Task A + Task B).
- All four path cases produce correct output.
- `output/path_results.txt` is generated and readable.
- At least 5 code review notes or confirmations are recorded.
- XXY can explain the main Dijkstra workflow and path-stitching logic at a high level.
- No prohibited third-party libraries are used.

---

# 5. Report and Presentation Tasks for XXY

## XXY-5: Write Chapter 2 and Graph Data Structure Section

**Suggested period:** 2026-05-13 to 2026-05-15  
**Related report sections:**

```text
Chapter 2 - Shortest Path Algorithm
Chapter 3 - Graph data-structure section (coordinate with WLY)
```

## Chapter 2 Suggested Structure

```text
2.1 Task B Objective
2.2 Graph Representation
2.3 Dijkstra's Algorithm Design
2.4 Path Cases and Results
2.5 Performance Analysis
2.6 Algorithm Discussion
```

## Chapter 2 Must Answer

- How is the road network represented as a graph? Why use an adjacency list?
- How does Dijkstra's algorithm guarantee the shortest path?
- Why is a min-heap (PriorityQueue) used instead of a linear scan?
- What is the time complexity of Dijkstra? How does it depend on V and E?
- Why can the shortest path between two selected locations pass through non-selected nodes?
- How are the three path cases derived from WLY's Top 10 output?
- What do the actual results (distances and paths) show about the graph structure?

## Chapter 3 Graph Data Structure Section Should Explain

- Why `HashMap<String, ArrayList<Edge>>` is used for the adjacency list.
- Why an adjacency list is preferred over an adjacency matrix for a sparse graph.
- How `Edge` stores direction and weight.
- How `PathResult` stores both the distance and the ordered node list.
- How Task B receives Top 10 results from Task A and uses them as query endpoints.

## Completion Criteria

- Chapter 2 contains a complete result table with all three path cases.
- Chapter 2 includes analysis, not only outputs.
- Chapter 3 graph section is consistent with WLY's data-structure section.
- Writing style is consistent with WLY's report sections.

---

## XXY-6: Write Reflection Section and Check Own Code Section

**Suggested period:** 2026-05-16 to 2026-05-18  
**Related report sections:**

```text
Chapter 4 - Development reflection (XXY part)
Chapter 5 - Program Code
```

## Chapter 4 Reflection (XXY Part)

XXY should write the development and testing reflection part.

Possible points:

- How the graph was incrementally tested: first on small manual graphs, then on the full `paths.csv`.
- How the three path cases were verified against expected results.
- Challenges encountered: e.g., stale queue entries, path reconstruction order, handling the start == end case.
- How integration with WLY's output was coordinated: WLY's Top 10 IDs were used as Dijkstra query endpoints.
- What would change if the graph became directed.
- What would change if negative edge weights were introduced.

## Chapter 5 XXY Code Section

XXY should check that the following files are included as text in Chapter 5:

```text
Edge.java
Graph.java
GraphCSVReader.java
DijkstraPathFinder.java
PathResult.java
```

The Task B portion of `Main.java` should also be included or clearly referenced.

## Completion Criteria

- Reflection is specific to this project, not generic.
- The report does not claim that AI generated core code or core report content.
- XXY's code is included in Chapter 5 as text, not screenshots.
- Chapter 5 code matches the final ZIP version.

---

## XXY-7: Help Create PPT and Prepare Second-Half Script

**Suggested period:** 2026-05-19 to 2026-05-21  
**Related files:**

```text
PPT presentation
Video script
MP4 video
```

## XXY Presentation Scope

XXY should mainly present:

- graph data model and `paths.csv` structure;
- Dijkstra's algorithm design and key implementation decisions;
- the three path cases and their results;
- brief analysis of path distances;
- conclusion and summary for the whole project.

## Suggested XXY Speaking Time

```text
3.5 to 4 minutes
```

## Suggested XXY Script Structure

```text
1. Graph representation and data model - about 40 seconds
2. Dijkstra algorithm design          - about 2 minutes
3. Path results and analysis           - about 1 minute
4. Project conclusion                  - about 30 seconds
```

## Completion Criteria

- XXY's slides are consistent with the report.
- XXY's spoken explanation matches the PPT.
- XXY's speaking time is controlled within 4 minutes.
- XXY appears on camera and uses own voice.
- The final video does not exceed 8 minutes in total.

---

# 6. Final Submission Check for XXY

**Suggested date:** 2026-05-22

XXY should help WLY check the final four submission files:

```text
1. Java ZIP file
2. Word report
3. MP4 video
4. PPT file
```

## XXY Final Checklist

## Java ZIP

- All `.java` files are included.
- The project can compile.
- The program can run.
- Graph CSV reading works.
- Dijkstra path results are correct.
- `output/path_results.txt` is generated.

## Word Report

- Chapter 2 is complete.
- Chapter 3 graph data-structure section is included.
- Chapter 4 XXY reflection is included.
- Chapter 5 includes XXY's code as text.
- Formatting is correct:
  - Calibri
  - font size 12
  - line spacing 1.5
  - normal margins.

## MP4 Video

- XXY appears on camera.
- XXY uses own voice.
- XXY explains Task B clearly.
- Total video length is below 8 minutes.

## PPT

- Task B slides are included.
- Results match the report.
- Slides are clear and not too text-heavy.

## Completion Criteria

- WLY and XXY each check the final files once.
- All four files can be opened.
- Results in code output, report, PPT, and video are consistent.
- Final version is ready for submission.

---

# 7. XXY Quick Development Checklist

Use this list during coding.

## Graph Data Model

- [x] `Edge.java` created.
- [x] `to` field added.
- [x] `weight` field added.
- [x] Constructor implemented.
- [x] Getters implemented.
- [x] `toString()` implemented.
- [x] `Graph.java` created.
- [x] Adjacency list field added.
- [x] `addEdge()` inserts edges in both directions.
- [x] `getNeighbors()` returns correct edge list.
- [x] `containsNode()` implemented.
- [x] `getNodeCount()` and `getEdgeCount()` implemented.

## Graph CSV Reading

- [x] `GraphCSVReader.java` created.
- [x] Header row skipped.
- [x] CSV lines parsed correctly.
- [x] `Graph` object returned.
- [x] Graph reports 1000 nodes.
- [x] Graph reports 2600 edges.

## Dijkstra

- [x] `PathResult.java` created.
- [x] `DijkstraPathFinder.java` created.
- [x] Private `Entry` class implements `Comparable<Entry>`.
- [x] `dist` map initialised with start node = 0.
- [x] Stale entry skip is implemented.
- [x] Predecessor map (`prev`) is populated during relaxation.
- [x] Path is reconstructed by walking `prev` backwards.
- [x] Path list is reversed before returning.
- [x] Start == end special case handled.
- [x] No-path case returns `totalDistance = -1`.
- [x] `findPathWithWaypoints()` implemented for Cases 3 and 4.
- [x] Path stitching skips duplicate waypoint junction node.
- [x] Small manual test passed.
- [x] Full graph test passed (all 4 cases).

## Integration

- [x] Task B is called from `Main.java` after Task A.
- [x] All four path cases use WLY's Top 10 node IDs correctly.
- [x] Console output matches the required format (Start / Destination / Waypoints / Path / Total Cost).
- [x] `output/path_results.txt` is generated.
- [x] XXY can explain all graph and Dijkstra code.

---

# 8. Suggested Console Output Format

The program output for Task B follows this style:

```text
==============================
Shortest Path Results
==============================

Graph loaded: 1000 nodes, 2600 edges

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
Total Cost: xx

Case 3:
Start: Lxxxx
Destination: Lxxxx
Waypoints: Lxxxx
Path: Lxxxx -> Lxxxx -> ...
Total Cost: xx

Case 4:
Start: Lxxxx
Destination: Lxxxx
Waypoints: Lxxxx, Lxxxx
Path: Lxxxx -> Lxxxx -> ...
Total Cost: xx

Path results saved to output/path_results.txt
```

---

# 9. Key Points XXY Must Be Able to Explain

Before submission, XXY should be able to explain:

- What `Edge` represents and why two Edge objects are created per CSV row.
- Why an adjacency list is used instead of an adjacency matrix.
- How `addEdge()` keeps the graph undirected.
- How `GraphCSVReader` builds the graph from `paths.csv`.
- Why Dijkstra's algorithm always finds the shortest path in a non-negative weighted graph.
- How the min-heap (PriorityQueue) works in Dijkstra.
- What a "stale entry" is and why it must be skipped.
- How the predecessor map (`prev`) is used to reconstruct the full path.
- Why the reconstructed path must be reversed before returning.
- How the start == end special case is handled.
- How Task B receives the six key node IDs from Task A.
- Why shortest paths may include non-Top-10 nodes as waypoints.
- How `findPathWithWaypoints` stitches multiple sub-segments together.
- Why duplicating the waypoint junction node is avoided and how the code does it.
- Why the stitched path for Cases 3 and 4 may revisit some intermediate nodes.
- Why AI tools were used only for planning or formatting support.
