package ductv.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PathTraversalExample {
    private static final Logger logger = Logger.getLogger(PathTraversalExample.class.getName());

    private static final Path BASE_DIR = Paths.get("data").toAbsolutePath().normalize();

    public static void main(String[] args) {
        String userInput = (args != null && args.length > 0) ? args[0] : "public.txt";

        try {
            Path target = BASE_DIR.resolve(userInput).normalize();
            if (!target.startsWith(BASE_DIR)) {
                logger.log(Level.WARNING, "Rejected path traversal attempt for input: {0}", userInput);
                return;
            }

            if (!Files.exists(target) || !Files.isRegularFile(target) || !Files.isReadable(target)) {
                logger.log(Level.INFO, "File not accessible: {0}", target.getFileName());
                return;
            }

            try (BufferedReader reader = Files.newBufferedReader(target, StandardCharsets.UTF_8)) {
                logger.log(Level.INFO, "Reading file: {0}", target.getFileName());
                String line;
                while ((line = reader.readLine()) != null) {
                    String finalLine = line;
                    logger.log(Level.FINE, () -> finalLine);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "I/O error while accessing file", e);
        }
    }
}
