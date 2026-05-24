// Represents one candidate location from the CSV files.
// Higher priority scores come first. If scores are the same, the smaller ID comes first.
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

    // Ranking rule used by all sorting classes.
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
