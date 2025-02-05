package org.kickerelo.kickerelo;

public class NoSuchPlayerException extends RuntimeException {
    public NoSuchPlayerException(String message) {
        super(message);
    }
}
