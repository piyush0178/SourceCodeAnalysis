/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections.functors;

import java.io.Serializable;

import org.apache.commons.collections.Transformer;

/**
 * Transformer implementation that returns the result of calling
 * <code>String.valueOf</code> on the input object.
 *
 * @since Commons Collections 3.0
 * @version $Revision: 894507 $ $Date: 2009-12-30 00:12:18 +0100 (Wed, 30 Dec 2009) $
 *
 * @author Stephen Colebourne
 */
public final class StringValueTransformer<T> implements Transformer<T, String>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = 7511110693171758606L;

    /** Singleton predicate instance */
    public static final Transformer<Object, String> INSTANCE = new StringValueTransformer<Object>();

    /**
     * Factory returning the singleton instance.
     *
     * @return the singleton instance
     * @since Commons Collections 3.1
     */
    @SuppressWarnings("unchecked")
    public static <T> Transformer<T, String> getInstance() {
        return (Transformer<T, String>) INSTANCE;
    }

    /**
     * Restricted constructor.
     */
    private StringValueTransformer() {
        super();
    }

    /**
     * Transforms the input to result by calling <code>String.valueOf</code>.
     *
     * @param input  the input object to transform
     * @return the transformed result
     */
    public String transform(T input) {
        return String.valueOf(input);
    }

    private Object readResolve() {
        return INSTANCE;
    }

}
