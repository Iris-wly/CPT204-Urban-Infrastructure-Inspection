import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CandidateCSVReader {

    /**
     * Reads a candidate CSV file and returns a list of Location objects.
     * Expected CSV format:
     *   location_id,priority_score
     *   L0001,98.5
     *   ...
     */
    public static ArrayList<Location> readCandidates(String filePath) throws IOException {
        ArrayList<Location> locations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0].trim();
                double score = Double.parseDouble(parts[1].trim());
                locations.add(new Location(id, score));
            }
        }

        return locations;
    }
}
