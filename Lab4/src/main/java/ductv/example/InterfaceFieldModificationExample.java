package ductv.example;

final class Constants {
    private Constants() {} // prevent instantiation

    public static final int MAX_USERS = 100;
}

public class InterfaceFieldModificationExample {
    public static void main(String[] args) {
        // Constants.MAX_USERS = 200; // Compile-time error
    }
}
