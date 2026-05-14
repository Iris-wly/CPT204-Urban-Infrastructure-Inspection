import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads a candidate CSV file and constructs a list of {@link Location} objects.
 *
 * Expected format (header row is skipped):
 * <pre>
 *   location_id,priority_score
 *   L0001,98.5
 * </pre>
 */
public class CandidateReader {

    /**
     * @param filePath path to the candidates CSV file
     * @return list of {@link Location} objects in file order (unsorted)
     * @throws IOException if the file cannot be read
     */
    public static ArrayList<Location> readCandidates(String filePath) throws IOException {
        ArrayList<Location> locations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id    = parts[0].trim();
                double score = Double.parseDouble(parts[1].trim());
                locations.add(new Location(id, score));
            }
        }

        return locations;
    }
}
