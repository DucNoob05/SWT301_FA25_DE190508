package ductv.example;

import java.util.logging.Logger;
import java.util.logging.Level;

public class UnreachableCodeExample {
    private static final Logger logger = Logger.getLogger(UnreachableCodeExample.class.getName());

    public static int getNumber() {
        logger.info("About to return 42");
        return 42;
    }

    public static void main(String[] args) {
        logger.log(Level.INFO, () -> String.valueOf(getNumber()));
    }
}
