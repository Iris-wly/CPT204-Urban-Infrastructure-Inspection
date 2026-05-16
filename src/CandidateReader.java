import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


//Reads the candidate CSV file and constructs a list of {@link Location} objects.
//The file format is: location_id,priority_score

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
