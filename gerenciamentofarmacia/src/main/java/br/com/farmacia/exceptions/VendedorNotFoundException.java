package br.com.farmacia.exceptions;

public class VendedorNotFoundException extends RuntimeException {

    public VendedorNotFoundException(String message) {
        super(message);
    }
}
