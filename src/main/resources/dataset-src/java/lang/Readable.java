/*
 * @(#)Readable.java	1.5 10/03/23
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.lang;

import java.io.IOException;

/**
 * A <tt>Readable</tt> is a source of characters. Characters from
 * a <tt>Readable</tt> are made available to callers of the read
 * method via a {@link java.nio.CharBuffer CharBuffer}.
 *
 * @version 1.5 10/03/23
 * @since 1.5
 */

public interface Readable {

    /**
     * Attempts to read characters into the specified character buffer.
     * The buffer is used as a repository of characters as-is: the only
     * changes made are the results of a put operation. No flipping or
     * rewinding of the buffer is performed.
     *
     * @param cb the buffer to read characters into
     * @return @return The number of <tt>char</tt> values added to the buffer,
     *                 or -1 if this source of characters is at its end
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if cb is null
     * @throws ReadOnlyBufferException if cb is a read only buffer
     */
    public int read(java.nio.CharBuffer cb) throws IOException;

}
