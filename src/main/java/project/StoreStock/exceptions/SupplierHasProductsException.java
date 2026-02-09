package project.StoreStock.exceptions;

public class SupplierHasProductsException extends RuntimeException {

    public SupplierHasProductsException(int supplierId) {
        super("Cannot delete supplier with ID " + supplierId + " because it still has products.");
    }
}
