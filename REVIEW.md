# Code Review Record

Reviewer pairing:
- **WLY reviews XXY's graph algorithm code** (Dijkstra, BellmanFord, Graph, GraphReader, PathResult)
- **XXY reviews WLY's sorting algorithm code** (BubbleSort, QuickSort, MergeSort, SortBenchmark, Location, CandidateReader)

---

## WLY reviewing XXY's graph code

**[R1 – Confirmed] Dijkstra stale-entry handling is correct**
`Dijkstra.findShortestPath` checks `current.distance > dist.get(current.nodeId)` before
processing any node. This correctly discards outdated queue entries that arose when a
shorter path was found after the entry was enqueued. Without this guard the algorithm
would still produce correct distances but would do unnecessary work.

**[R2 – Confirmed] Undirected edges stored correctly in Graph**
`Graph.addEdge` inserts two `Edge` objects for every call (A→B and B→A), making the
graph effectively undirected. `getEdgeCount` divides the directed total by 2 before
returning, so the reported edge count is consistent with the CSV input.

**[R3 – Confirmed] BellmanFord early-termination is safe**
The `updated` flag is reset at the start of every relaxation pass. If a full pass over
all nodes and their neighbours produces no improvement, the algorithm breaks early.
This is a valid optimisation for sparse, non-negative-weight graphs and does not affect
correctness.

**[R4 – Issue found & fixed] PathResult Javadoc said "Dijkstra query"**
`PathResult` is used by both `Dijkstra` and `BellmanFord`, but its class Javadoc
originally read "Stores the result of one Dijkstra shortest-path query."
Fixed to "one shortest-path query" so the description is accurate for both callers.

**[R5 – Confirmed] Waypoint stitching removes duplicate nodes correctly**
In `findPathWithWaypoints`, the first segment is added in full; every subsequent
segment skips index 0 (the shared waypoint node) before appending. This ensures the
final path contains no duplicate node at each join point.

---

## XXY reviewing WLY's sorting code

**[R6 – Confirmed] All three sorts use the same comparator**
`BubbleSort`, `QuickSort`, and `MergeSort` all delegate to `Location.compare(a, b)`.
The ranking rule (descending score, ascending ID on tie) is defined in exactly one
place, so all algorithms are guaranteed to produce the same sorted order.

**[R7 – Confirmed] SortBenchmark uses fresh copies for every timed run**
`copyLocations` is called inside the inner loop (per run, not per sorter), so each
run starts from the original unsorted state. This is necessary for fair timing:
without the copy, the second and third runs of each algorithm would sort an
already-sorted list, artificially lowering Bubble Sort's reported time.

**[R8 – Confirmed] QuickSort partition handles equal-priority elements correctly**
The partition condition is `Location.compare(current, pivot) <= 0`, meaning elements
equal to the pivot go to the left side. This is the standard Lomuto scheme and
produces a valid partition. It does not affect correctness, only performance on
inputs with many ties.

**[R9 – Issue found & fixed] MergeSort had excessive inline comments**
The original `merge` method contained line-by-line narration comments that restated
what each line of code does. These were trimmed to a single meaningful note explaining
*why* a temporary copy is needed, which is the only non-obvious design decision.

**[R10 – Confirmed] No third-party libraries used**
All imports across the project are from `java.util` and `java.io`, both part of the
standard Java SE library. No external dependencies are required.
