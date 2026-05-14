import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Shortest-path finder using Dijkstra's algorithm.
 *
 * Uses a min-heap (PriorityQueue) to always expand the nearest node first.
 * Stale queue entries are skipped (lazy deletion) rather than using decrease-key.
 *
 * Time:  O((V + E) log V)    Space: O(V)
 */
public class Dijkstra {

    /** Node-distance pair used inside the priority queue. */
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
     * Returns the shortest path from startId to endId.
     * Returns totalDistance = -1 if no path exists.
     */
    public static PathResult findShortestPath(Graph graph, String startId, String endId) {
        if (startId.equals(endId)) {
            ArrayList<String> path = new ArrayList<>();
            path.add(startId);
            return new PathResult(startId, endId, 0, path);
        }

        HashMap<String, Integer> dist = new HashMap<>();
        HashMap<String, String> prev = new HashMap<>();
        PriorityQueue<Entry> pq = new PriorityQueue<>();

        dist.put(startId, 0);
        pq.offer(new Entry(startId, 0));

        while (!pq.isEmpty()) {
            Entry current = pq.poll();

            // Skip stale entries superseded by a shorter path found later.
            if (current.distance > dist.getOrDefault(current.nodeId, Integer.MAX_VALUE)) {
                continue;
            }

            if (current.nodeId.equals(endId)) break; // early termination

            for (Edge edge : graph.getNeighbors(current.nodeId)) {
                int newDist = current.distance + edge.getWeight();
                if (newDist < dist.getOrDefault(edge.getTo(), Integer.MAX_VALUE)) {
                    dist.put(edge.getTo(), newDist);
                    prev.put(edge.getTo(), current.nodeId);
                    pq.offer(new Entry(edge.getTo(), newDist));
                }
            }
        }

        if (!dist.containsKey(endId)) {
            return new PathResult(startId, endId, -1, new ArrayList<>());
        }

        // Reconstruct path by walking prev map backwards, then reverse.
        ArrayList<String> path = new ArrayList<>();
        String node = endId;
        while (node != null) {
            path.add(node);
            node = prev.get(node);
        }
        Collections.reverse(path);

        return new PathResult(startId, endId, dist.get(endId), path);
    }

    /**
     * Finds the shortest route visiting nodeIds[0..n-1] in order.
     *
     * Runs findShortestPath on each consecutive pair and stitches the segments
     * together, skipping the duplicate waypoint node at each join.
     * Returns totalDistance = -1 if any segment has no path.
     */
    public static PathResult findPathWithWaypoints(Graph graph, String[] nodeIds) {
        ArrayList<String> fullPath = new ArrayList<>();
        int totalDistance = 0;

        for (int i = 0; i < nodeIds.length - 1; i++) {
            PathResult segment = findShortestPath(graph, nodeIds[i], nodeIds[i + 1]);

            if (segment.getTotalDistance() < 0) {
                return new PathResult(nodeIds[0], nodeIds[nodeIds.length - 1], -1, new ArrayList<>());
            }

            if (i == 0) {
                fullPath.addAll(segment.getPath());
            } else {
                // Skip index 0 of each subsequent segment to avoid duplicating the waypoint.
                ArrayList<String> segPath = segment.getPath();
                for (int j = 1; j < segPath.size(); j++) {
                    fullPath.add(segPath.get(j));
                }
            }
            totalDistance += segment.getTotalDistance();
        }

        return new PathResult(nodeIds[0], nodeIds[nodeIds.length - 1], totalDistance, fullPath);
    }
}
