package com.inventario.projeto.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String name, Integer id) {
        super(String.format("%s with id %d not found", name, id));
    }
    public NotFoundException(String name, String message) {super(String.format("%s not found, %s", name, message));}
}
