/**
 * Represents a candidate inspection location with a priority score.
 *
 * Each Location has a unique ID (e.g. "L0001") and a numeric priority score.
 * The static {@link #compare(Location, Location)} method defines the canonical
 * ranking rule used by all three sorting algorithms:
 *   1. Higher priority score ranks first (descending order).
 *   2. On a tie, the smaller location ID ranks first (ascending lexicographic order).
 */
public class Location {

    private String locationId;
    private double priorityScore;

    public Location(String locationId, double priorityScore) {
        this.locationId = locationId;
        this.priorityScore = priorityScore;
    }

    public String getLocationId() {
        return locationId;
    }

    public double getPriorityScore() {
        return priorityScore;
    }

    /**
     * Ranking rule:
     *   1. Higher priorityScore comes first (descending).
     *   2. If scores are equal, smaller locationId comes first (ascending).
     *
     * Returns negative -> a should come before b
     * Returns positive -> a should come after b
     * Returns 0        -> equal under the ranking rule
     */
    public static int compare(Location a, Location b) {
        int scoreComparison = Double.compare(b.priorityScore, a.priorityScore);
        if (scoreComparison != 0) {
            return scoreComparison;
        }

        return a.locationId.compareTo(b.locationId);
    }

    @Override
    public String toString() {
        return String.format("%s, score = %.2f", locationId, priorityScore);
    }
}
