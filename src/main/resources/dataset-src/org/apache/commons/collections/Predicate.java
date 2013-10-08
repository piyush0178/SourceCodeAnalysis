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
package org.apache.commons.collections;

/**
 * Defines a functor interface implemented by classes that perform a predicate
 * test on an object.
 * <p>
 * A <code>Predicate</code> is the object equivalent of an <code>if</code> statement.
 * It uses the input object to return a true or false value, and is often used in
 * validation or filtering.
 * <p>
 * Standard implementations of common predicates are provided by
 * {@link PredicateUtils}. These include true, false, instanceof, equals, and,
 * or, not, method invokation and null testing.
 *
 * @param <T> the type that the predicate queries
 *
 * @since Commons Collections 1.0
 * @version $Revision: 814997 $ $Date: 2009-09-15 07:29:56 +0200 (Tue, 15 Sep 2009) $
 *
 * @author James Strachan
 * @author Stephen Colebourne
 */
public interface Predicate<T> {

    /**
     * Use the specified parameter to perform a test that returns true or false.
     *
     * @param object  the object to evaluate, should not be changed
     * @return true or false
     * @throws ClassCastException (runtime) if the input is the wrong class
     * @throws IllegalArgumentException (runtime) if the input is invalid
     * @throws FunctorException (runtime) if the predicate encounters a problem
     */
    public boolean evaluate(T object);

}
