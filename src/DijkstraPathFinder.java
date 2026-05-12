import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Finds the shortest path between two nodes using Dijkstra's algorithm.
 *
 * Algorithm summary:
 *   1. Initialise the distance of the start node to 0; all others to infinity.
 *   2. Use a min-heap (PriorityQueue) to always expand the node with the
 *      smallest known distance first.
 *   3. For each neighbour of the current node, check whether going through
 *      the current node produces a shorter distance. If yes, update and
 *      add a new entry to the queue.
 *   4. Track the predecessor of each node to reconstruct the full path.
 *   5. Stop early once the destination node is dequeued.
 *
 * Time complexity: O((V + E) log V) where V = nodes and E = edges.
 * Space complexity: O(V) for the distance map, predecessor map, and queue.
 *
 * Design note:
 *   A private Entry class is used so the priority queue can compare by distance
 *   without converting integers to strings. Stale entries (where the recorded
 *   distance is greater than the current best) are skipped rather than removed,
 *   which avoids the cost of a decrease-key operation.
 */
public class DijkstraPathFinder {

    /**
     * A node-distance pair used inside the priority queue.
     * Implements Comparable so PriorityQueue orders by distance ascending.
     */
    private static class Entry implements Comparable<Entry> {
        String nodeId;
        int distance;

        Entry(String nodeId, int distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }

        @Override
        public int compareTo(Entry other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    /**
     * Returns the shortest path from startId to endId in the given graph.
     *
     * Special case: if startId equals endId, the path contains only that node
     * and the total distance is 0.
     *
     * If no path exists, the returned PathResult has totalDistance = -1
     * and an empty path list.
     */
    public static PathResult findShortestPath(Graph graph, String startId, String endId) {
        if (startId.equals(endId)) {
            ArrayList<String> path = new ArrayList<>();
            path.add(startId);
            return new PathResult(startId, endId, 0, path);
        }

        // dist holds the best-known distance from startId to each visited node.
        HashMap<String, Integer> dist = new HashMap<>();

        // prev holds the node that came just before each node on the best path.
        // Used to reconstruct the full route after the search finishes.
        HashMap<String, String> prev = new HashMap<>();

        PriorityQueue<Entry> pq = new PriorityQueue<>();

        dist.put(startId, 0);
        pq.offer(new Entry(startId, 0));

        while (!pq.isEmpty()) {
            Entry current = pq.poll();

            // Skip stale queue entries.
            // A stale entry exists when a shorter path to current.nodeId was
            // found after this entry was already added to the queue.
            if (current.distance > dist.getOrDefault(current.nodeId, Integer.MAX_VALUE)) {
                continue;
            }

            // Early termination: once we dequeue the destination, we have its
            // shortest distance and there is no need to continue.
            if (current.nodeId.equals(endId)) {
                break;
            }

            // Relax all edges leaving the current node.
            for (Edge edge : graph.getNeighbors(current.nodeId)) {
                int newDist = current.distance + edge.getWeight();

                if (newDist < dist.getOrDefault(edge.getTo(), Integer.MAX_VALUE)) {
                    dist.put(edge.getTo(), newDist);
                    prev.put(edge.getTo(), current.nodeId);
                    pq.offer(new Entry(edge.getTo(), newDist));
                }
            }
        }

        // If endId was never reached, return a no-path result.
        if (!dist.containsKey(endId)) {
            return new PathResult(startId, endId, -1, new ArrayList<>());
        }

        // Reconstruct the path by walking backwards through prev.
        ArrayList<String> path = new ArrayList<>();
        String node = endId;
        while (node != null) {
            path.add(node);
            node = prev.get(node);
        }

        // Reverse so the list goes from start to end.
        Collections.reverse(path);

        return new PathResult(startId, endId, dist.get(endId), path);
    }
}
