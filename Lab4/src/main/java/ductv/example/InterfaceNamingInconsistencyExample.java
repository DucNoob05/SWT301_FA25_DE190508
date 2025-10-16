package ductv.example;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceNamingInconsistencyExample {
    private static final Logger logger = Logger.getLogger(InterfaceNamingInconsistencyExample.class.getName());

    public interface LoginHandler {
        boolean login(String username, String password);
    }

    public static class SimpleLoginHandler implements LoginHandler {
        private static final Logger logger = Logger.getLogger(SimpleLoginHandler.class.getName());

        @Override
        public boolean login(String username, String password) {
            if (username == null || password == null) {
                logger.warning("Null credential provided");
                return false;
            }
            String expectedUser = System.getenv("APP_USER");
            String expectedPass = System.getenv("APP_PASS");
            if (expectedUser == null || expectedPass == null) {
                logger.severe("Auth configuration missing");
                return false;
            }
            boolean ok = username.equals(expectedUser) && password.equals(expectedPass);
            if (ok) {
                logger.info(() -> "Login success for user=" + username);
            } else {
                logger.warning(() -> "Login failed for user=" + username);
            }
            return ok;
        }
    }

    public static void main(String[] args) {
        LoginHandler auth = new SimpleLoginHandler();
        boolean result = auth.login("admin", "1234");
        logger.log(Level.INFO, "Authenticated? {0}", result);
    }
}
