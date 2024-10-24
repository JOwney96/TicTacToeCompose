import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.Statistics;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsTest {
    @Test
    @DisplayName("Can use both constructors")
    public void constructors() {
        Statistics statistics1 = new Statistics();
        Statistics statistics2 = new Statistics(1, 1, 1);

        assertEquals(0, statistics1.wins);
        assertEquals(1, statistics2.wins);
    }

    @Test
    @DisplayName("Can save to file with default name")
    public void saveFileDefault() {
        Statistics statistics = new Statistics();
        Path filePath = Paths.get("stats.txt");
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        assert (Files.notExists(filePath));

        statistics.saveToFile();

        assert (Files.exists(filePath));

        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Can save to file with given name")
    public void saveFile() {
        String filename = "stats2222.txt";

        Statistics statistics = new Statistics();
        Path filePath = Paths.get(filename);
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        assert (Files.notExists(filePath));

        statistics.saveToFile(filename);

        assert (Files.exists(filePath));

        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Can read from file with default name")
    public void loadFileDefault() {
        Statistics statistics = new Statistics(1, 2, 3);
        statistics.saveToFile();

        statistics = null;

        try {
            statistics = Statistics.loadFromFile();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        assertEquals(statistics.ties, 3);

        Path filePath = Paths.get("stats.txt");

        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Can read from file with given name")
    public void loadFile() {
        String fileName = "stats1234.txt";

        Statistics statistics = new Statistics(1, 2, 3);
        statistics.saveToFile(fileName);

        statistics = null;

        try {
            statistics = Statistics.loadFromFile(fileName);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        assertEquals(statistics.ties, 3);

        Path filePath = Paths.get(fileName);

        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
