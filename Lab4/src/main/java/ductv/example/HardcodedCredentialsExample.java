package ductv.example;

import java.util.logging.Logger;

public class HardcodedCredentialsExample {
    private static final Logger logger = Logger.getLogger(HardcodedCredentialsExample.class.getName());

    public static void main(String[] args) {
        String username = System.getenv("APP_USER");
        String password = System.getenv("APP_PASS");

        if (username == null || password == null) {
            logger.warning("Environment variables APP_USER or APP_PASS not set.");
            return;
        }

        if (authenticate(username, password)) {
            logger.info("Access granted");
        } else {
            logger.warning("Access denied");
        }
    }

    private static boolean authenticate(String user, String pass) {
        return "admin".equals(user) && "123456".equals(pass);
    }
}
