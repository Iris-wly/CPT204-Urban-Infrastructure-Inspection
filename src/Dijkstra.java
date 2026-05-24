import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

// Finds shortest paths in the weighted graph using Dijkstra's algorithm.
// The priority queue helps pick the currently closest node quickly.
public class Dijkstra {

    // Small pair used by the priority queue.
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

    // Returns the shortest path from startId to endId.
    // If no path exists, the returned distance is -1.
    public static PathResult findShortestPath(Graph graph, String startId, String endId) {
        if (startId.equals(endId)) {
            ArrayList<String> path = new ArrayList<>();
            path.add(startId);
            return new PathResult(startId, endId, 0, path);
        }

        HashMap<String, Integer> dist = new HashMap<>();
        HashMap<String, String> prev = new HashMap<>();
        PriorityQueue<Entry> pq = new PriorityQueue<>();

        // dist stores the best distance found so far for each node.
        // prev stores where each node came from, so the path can be rebuilt later.
        dist.put(startId, 0);
        pq.offer(new Entry(startId, 0));

        while (!pq.isEmpty()) {
            Entry current = pq.poll();

            // If a shorter route to this node was already found, ignore this old queue entry.
            if (current.distance > dist.getOrDefault(current.nodeId, Integer.MAX_VALUE)) {
                continue;
            }

            if (current.nodeId.equals(endId)) break; // Stop once the destination is the closest node.

            for (Edge edge : graph.getNeighbors(current.nodeId)) {
                int newDist = current.distance + edge.getWeight();
                if (newDist < dist.getOrDefault(edge.getTo(), Integer.MAX_VALUE)) {
                    // Found a better path to this neighbor.
                    dist.put(edge.getTo(), newDist);
                    prev.put(edge.getTo(), current.nodeId);
                    pq.offer(new Entry(edge.getTo(), newDist));
                }
            }
        }

        if (!dist.containsKey(endId)) {
            return new PathResult(startId, endId, -1, new ArrayList<>());
        }

        // Rebuild the path from end to start, then reverse it.
        ArrayList<String> path = new ArrayList<>();
        String node = endId;
        while (node != null) {
            path.add(node);
            node = prev.get(node);
        }
        Collections.reverse(path);

        return new PathResult(startId, endId, dist.get(endId), path);
    }

    // Finds the shortest route visiting nodeIds[0..n-1] in order.
    // It searches each pair of nodes in order and joins the path pieces.
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
                // Skip the first node so the waypoint is not printed twice.
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
