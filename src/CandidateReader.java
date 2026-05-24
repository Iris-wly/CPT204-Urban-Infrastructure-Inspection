import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



 //Reads candidate locations from a CSV file.
 
 //The file has a header row, then rows in the form: location_id,priority_score.
 
public class CandidateReader {

    /**
     * Loads the candidates in the same order as the file.
     */
    public static ArrayList<Location> readCandidates(String filePath) throws IOException {
        ArrayList<Location> locations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the column names.

            String line;
            while ((line = br.readLine()) != null) {
                // Each row should contain the location ID and its priority score.
                String[] parts = line.split(",");
                String id    = parts[0].trim();
                double score = Double.parseDouble(parts[1].trim());
                locations.add(new Location(id, score));
            }
        }

        return locations;
    }
}
