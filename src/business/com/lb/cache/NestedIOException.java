package com.lb.cache;

import java.io.IOException;

/**
 * Bridge class to provide nested Exceptions with IOException which has
 * constructors that don't take Throwables.
 *
 * @version 1.2
 */
public class NestedIOException extends IOException {

    /**
     * @param cause object of type throwable
     */
    public NestedIOException(Throwable cause) {
        super(cause.getMessage());
        super.initCause(cause);
    }

    public NestedIOException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
