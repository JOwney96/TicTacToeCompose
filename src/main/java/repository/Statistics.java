package repository;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

// This needs to be JAXB compatible
@XmlRootElement
public class Statistics {
    public int wins;
    public int loses;
    public int ties;

    public Statistics() {
        wins = 0;
        loses = 0;
        ties = 0;
    }

    public Statistics(int wins, int loses, int ties) {
        this.wins = wins;
        this.loses = loses;
        this.ties = ties;
    }

    public static Statistics loadFromFile() throws JAXBException {
        return readFromFileImplementation("stats.txt");
    }

    public static Statistics loadFromFile(String fileName) throws JAXBException {
        return readFromFileImplementation(fileName);
    }

    private static Statistics readFromFileImplementation(String fileName) throws JAXBException {
        Path filePath = Paths.get(fileName);
        File file = filePath.toFile();

        return JAXB.unmarshal(file, Statistics.class);
    }

    public void saveToFile() {
        saveToFileImplementation("stats.txt");
    }

    public void saveToFile(String fileName) {
        saveToFileImplementation(fileName);
    }

    private void saveToFileImplementation(String fileName) {
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.err.println("Error in creating file");
                System.err.println(e.getMessage());
                System.err.println(Arrays.toString(e.getStackTrace()));
            }
        }

        File statsFile = filePath.toFile();

        try (FileWriter fileWriter = new FileWriter(statsFile);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            JAXB.marshal(this, bufferedWriter);

        } catch (IOException e) {
            System.err.println("Error writing stats to file");
            System.err.println(e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
