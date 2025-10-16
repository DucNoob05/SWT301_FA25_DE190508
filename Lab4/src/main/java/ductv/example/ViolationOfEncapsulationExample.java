package ductv.example;

import java.util.Objects;
import java.util.logging.Logger;

class User {
    private static final Logger logger = Logger.getLogger(User.class.getName());

    private String name;
    private int age;

    // Default constructor
    public User() {
        this.name = "";
        this.age = 0;
    }

    // Constructor with validation
    public User(String name, int age) {
        setName(name);
        setAge(age);
    }

    // Getters
    public String getName() {
        return name;
    }

    // Setter with basic validation (null-safe + trim)
    public void setName(String name) {
        this.name = (name == null) ? "" : name.trim();
    }

    public int getAge() {
        return age;
    }

    // Setter with validation: age must be non-negative
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("age must be >= 0");
        }
        this.age = age;
    }

    // Use logger instead of System.out
    public void display() {
        logger.info(() -> "Name: " + name + ", Age: " + age);
    }

    @Override
    public String toString() {
        return "User{name='" + name + '\'' + ", age=" + age + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
