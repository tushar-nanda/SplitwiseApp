package exception;

public class ExcessAmountException extends Exception {
    @Override
    public String getMessage() {
        return "Excess amount entered. Please enter a valid amount.";
    }
}
