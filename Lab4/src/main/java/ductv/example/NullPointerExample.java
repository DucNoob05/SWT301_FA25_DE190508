package ductv.example;

import java.util.logging.Logger;

public class NullPointerExample {
    private static final Logger logger = Logger.getLogger(NullPointerExample.class.getName());

    public static void main(String[] args) {
        String text = (args != null && args.length > 0) ? args[0] : null;
        if (text != null && !text.isEmpty()) {
            logger.info("Text is not empty");
        } else {
            logger.info("Text is null or empty");
        }
    }
}
