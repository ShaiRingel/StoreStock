package project.StoreStock.exceptions;

public class IDNotFoundException extends RuntimeException {
    public IDNotFoundException(String action, String entityName, int id) {
        super(String.format("Cannot %s %s with ID %d. It is not found", action, entityName, id));
    }
}
