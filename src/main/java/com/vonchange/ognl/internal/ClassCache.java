package com.vonchange.ognl.internal;

import com.vonchange.ognl.ClassCacheInspector;

/**
 * This is a highly specialized map for storing values keyed by Class objects.
 */
public interface ClassCache {

    void setClassInspector(ClassCacheInspector inspector);

    void clear();

    int getSize();

    Object get(Class key);

    Object put(Class key, Object value);
}
