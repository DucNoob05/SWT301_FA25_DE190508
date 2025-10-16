package ductv.example;

import java.util.logging.Logger;
import java.util.logging.Level;

public class OvercatchingExceptionExample {
    private static final Logger logger = Logger.getLogger(OvercatchingExceptionExample.class.getName());

    public static void main(String[] args) {
        int[] arr = new int[5];
        int indexToAccess = 10;

        if (indexToAccess >= 0 && indexToAccess < arr.length) {
            logger.log(Level.INFO, "Value at index {0} = {1}", new Object[] { indexToAccess, arr[indexToAccess] });
        } else {
            logger.log(Level.WARNING, "Index out of bounds: {0} (length={1})", new Object[] { indexToAccess, arr.length });
        }
    }
}
