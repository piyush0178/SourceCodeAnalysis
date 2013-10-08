/*
 * @(#)LastOwnerException.java	1.17 10/03/23
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.security.acl;

/**
 * This is an exception that is thrown whenever an attempt is made to delete
 * the last owner of an Access Control List.  
 *  
 * @see java.security.acl.Owner#deleteOwner
 *
 * @author Satish Dharmaraj 
 */
public class LastOwnerException extends Exception {

    private static final long serialVersionUID = -5141997548211140359L;

    /**
     * Constructs a LastOwnerException.
     */
    public LastOwnerException() {
    }
}
