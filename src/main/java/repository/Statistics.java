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

/**
 * The Statistics class encapsulates the statistics of a game including wins, losses, and ties.
 * It provides functionalities to initialize, load from file, and save to file.
 */
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

    /**
     * Loads the statistics from the default file "stats.txt".
     *
     * @return the statistics loaded from the file "stats.txt"
     * @throws JAXBException if an error occurs during the unmarshalling process
     */
    public static Statistics loadFromFile() throws JAXBException {
        return readFromFileImplementation("stats.txt");
    }

    /**
     * Loads the statistics from a specified file.
     *
     * @param fileName the name of the file from which to load the statistics
     * @return the statistics loaded from the specified file
     * @throws JAXBException if an error occurs during the unmarshalling process
     */
    public static Statistics loadFromFile(String fileName) throws JAXBException {
        return readFromFileImplementation(fileName);
    }

    /**
     * Reads the statistics from the specified file using JAXB unmarshalling.
     *
     * @param fileName the name of the file from which to read the statistics
     * @return the statistics read from the file
     * @throws JAXBException if an error occurs during the unmarshalling process
     */
    private static Statistics readFromFileImplementation(String fileName) throws JAXBException {
        Path filePath = Paths.get(fileName);
        File file = filePath.toFile();

        return JAXB.unmarshal(file, Statistics.class);
    }

    /**
     * Saves the current statistics object to a default file named "stats.txt".
     * This method delegates the actual file-saving process to the
     * saveToFileImplementation method.
     */
    public void saveToFile() {
        saveToFileImplementation("stats.txt");
    }

    /**
     * Saves the current statistics object to a specified file.
     * This method delegates the actual file-saving process to the saveToFileImplementation method.
     *
     * @param fileName the name of the file to which the statistics should be saved
     */
    public void saveToFile(String fileName) {
        saveToFileImplementation(fileName);
    }

    /**
     * Saves the current statistics object to the specified file. This method handles
     * file creation if it does not exist and uses JAXB to marshal the current object
     * into the file.
     *
     * @param fileName the name of the file to which the statistics should be saved
     */
    private void saveToFileImplementation(String fileName) {
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.err.println("Error in creating file");
                System.err.println(e.getMessage());
                System.err.println(Arrays.toString(e.getStackTrace()));
                return;
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
