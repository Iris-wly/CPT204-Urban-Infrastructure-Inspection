import java.util.ArrayList;

/**
 * Stores the result of one shortest-path query (Dijkstra or Bellman-Ford).
 *
 * Fields:
 *   startId       - ID of the source node
 *   endId         - ID of the destination node
 *   totalDistance - sum of edge weights along the shortest path;
 *                   -1 means no path was found
 *   path          - ordered list of node IDs from start to end
 */
public class PathResult {

    private String startId;
    private String endId;
    private int totalDistance;
    private ArrayList<String> path;

    public PathResult(String startId, String endId, int totalDistance, ArrayList<String> path) {
        this.startId = startId;
        this.endId = endId;
        this.totalDistance = totalDistance;
        this.path = path;
    }

    public String getStartId() {
        return startId;
    }

    public String getEndId() {
        return endId;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    @Override
    public String toString() {
        if (totalDistance < 0) {
            return startId + " -> " + endId + ": No path found";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("From: ").append(startId)
          .append("  To: ").append(endId)
          .append("  |  Total distance: ").append(totalDistance)
          .append("  |  Nodes in path: ").append(path.size())
          .append("\n  Path: ");

        for (int i = 0; i < path.size(); i++) {
            if (i > 0) {
                sb.append(" -> ");
            }
            sb.append(path.get(i));
        }

        return sb.toString();
    }
}
