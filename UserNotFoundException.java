public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        System.err.println(message);
    }
}
