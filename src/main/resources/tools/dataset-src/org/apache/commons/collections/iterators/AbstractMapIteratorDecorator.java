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
package org.apache.commons.collections.iterators;

import org.apache.commons.collections.MapIterator;

/**
 * Provides basic behaviour for decorating a map iterator with extra functionality.
 * <p>
 * All methods are forwarded to the decorated map iterator.
 *
 * @since Commons Collections 3.0
 * @version $Revision: 814997 $ $Date: 2009-09-15 07:29:56 +0200 (Tue, 15 Sep 2009) $
 *
 * @author Stephen Colebourne
 */
public class AbstractMapIteratorDecorator<K, V> implements MapIterator<K, V> {

    /** The iterator being decorated */
    protected final MapIterator<K, V> iterator;

    //-----------------------------------------------------------------------
    /**
     * Constructor that decorates the specified iterator.
     *
     * @param iterator  the iterator to decorate, must not be null
     * @throws IllegalArgumentException if the collection is null
     */
    public AbstractMapIteratorDecorator(MapIterator<K, V> iterator) {
        super();
        if (iterator == null) {
            throw new IllegalArgumentException("MapIterator must not be null");
        }
        this.iterator = iterator;
    }

    /**
     * Gets the iterator being decorated.
     * 
     * @return the decorated iterator
     */
    protected MapIterator<K, V> getMapIterator() {
        return iterator;
    }

    //-----------------------------------------------------------------------
    public boolean hasNext() {
        return iterator.hasNext();
    }

    public K next() {
        return iterator.next();
    }

    public void remove() {
        iterator.remove();
    }
    
    public K getKey() {
        return iterator.getKey();
    }

    public V getValue() {
        return iterator.getValue();
    }

    public V setValue(V obj) {
        return iterator.setValue(obj);
    }

}