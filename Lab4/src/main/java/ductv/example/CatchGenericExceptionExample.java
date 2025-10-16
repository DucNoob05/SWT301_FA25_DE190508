package ductv.example;

import java.util.logging.Logger;
import java.util.logging.Level;

public class CatchGenericExceptionExample {
    private static final Logger logger = Logger.getLogger(CatchGenericExceptionExample.class.getName());

    public static void main(String[] args) {
        String s = (args != null && args.length > 0) ? args[0] : null;
        if (s == null) {
            logger.warning("Input string 's' is null; aborting length check.");
            return;
        }
        logger.log(Level.INFO, () -> "Length = " + s.length());
    }
}
