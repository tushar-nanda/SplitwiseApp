package exception;

public class InvalidEmailException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid email entered. Please enter a valid email.";
    }
}
