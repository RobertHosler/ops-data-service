package me.roqb.opsdata.restservice.settings;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class CommunityInclusions {

    private static final String PATH = "src/main/resources/communityInclusions.txt";

    private List<String> fileContents;

    public CommunityInclusions() {
        this.fileContents = readFile();
    }

    private List<String> readFile() {
        List<String> lines = null;
        try {
            Path path = Paths.get(PATH);
            System.out.println("path: " + path);
            lines = Files.readAllLines(path);
            System.out.println("lines: " + lines);
        } catch (IOException e) {
            // Caught Exception! - Got Yah!
            System.out.println("exception: " + e.getMessage());
        }
        return lines;
    }

    public List<String> getFileContents() {
        return this.fileContents;
    }

}
