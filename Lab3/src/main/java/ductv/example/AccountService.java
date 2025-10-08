package ductv.example;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class AccountService {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern UPPERCASE = Pattern.compile(".*[A-Z].*");
    private static final Pattern DIGIT = Pattern.compile(".*\\d.*");
    private static final Pattern SPECIAL = Pattern.compile(".*[^a-zA-Z0-9].*");

    private final Set<String> existing = new HashSet<>();

    public boolean registerAccount(String username, String password, String email) {
        username = username == null ? null : username.trim();
        email    = email == null ? null : email.trim();
        if (isBlank(username) || isBlank(password) || isBlank(email)) return false;
        if (username.length() <= 3) return false;
        if (existing.contains(username)) return false;
        if (!isStrongPassword(password)) return false;
        if (!isValidEmail(email)) return false;
        existing.add(username);
        return true;
    }

    public boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private static boolean isBlank(String s) { return s == null || s.isBlank(); }

    private static boolean isStrongPassword(String pw) {
        if (pw == null || pw.length() <= 6) return false;
        if (!UPPERCASE.matcher(pw).matches()) return false;
        if (!DIGIT.matcher(pw).matches()) return false;
        if (!SPECIAL.matcher(pw).matches()) return false;
        return true;
    }
}
