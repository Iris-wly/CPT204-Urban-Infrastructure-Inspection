import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Shortest-path finder using the Bellman-Ford algorithm.
 *
 * Relaxes every edge (V − 1) times with early termination when no update occurs.
 * Unlike Dijkstra, this algorithm correctly handles negative-weight edges,
 * but is slower on this non-negative-weight graph.
 *
 * Time:  O(V × E)    Space: O(V)
 */
public class BellmanFord {

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

        ArrayList<String> allNodes = graph.getAllNodes();
        int nodeCount = allNodes.size();

        HashMap<String, Integer> dist = new HashMap<>();
        HashMap<String, String> prev = new HashMap<>();
        for (String node : allNodes) {
            dist.put(node, Integer.MAX_VALUE);
        }
        dist.put(startId, 0);

        for (int iter = 0; iter < nodeCount - 1; iter++) {
            boolean updated = false;

            for (String u : allNodes) {
                int distU = dist.get(u);
                if (distU == Integer.MAX_VALUE) continue; // unreachable node

                for (Edge edge : graph.getNeighbors(u)) {
                    String v = edge.getTo();
                    int newDist = distU + edge.getWeight();
                    if (newDist < dist.get(v)) {
                        dist.put(v, newDist);
                        prev.put(v, u);
                        updated = true;
                    }
                }
            }

            if (!updated) break; // converged early
        }

        if (dist.get(endId) == Integer.MAX_VALUE) {
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
     * Uses the same segment-stitching logic as {@link Dijkstra#findPathWithWaypoints}.
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
