package project.StoreStock.exceptions;

public class MaxQuantityReachedException extends RuntimeException {
    public MaxQuantityReachedException (String entityName, int maxSuppliers) {
        super(String.format("Cannot save more than %d %s", maxSuppliers, entityName));
    }
}
