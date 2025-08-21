package com.khamis.dreamshops.exceptions;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String theImageNotFound) {
        super(theImageNotFound);
    }
}
