package org.openfast.util;

import java.util.HashMap;
import java.util.Map;
import org.openfast.FieldValue;

public class UnboundedCache implements Cache {
    private int nextIndex = 1;
    private final IntegerMap indexToValueMap = new SimpleIntegerMap();
    private final Map<FieldValue, Integer> valueToIndexMap = new HashMap<>();

    public int getIndex(FieldValue value) {
        return valueToIndexMap.get(value);
    }

    public int store(FieldValue value) {
        int next = nextIndex;
        indexToValueMap.put(next, value);
        valueToIndexMap.put(value, next);
        nextIndex++;
        return next;
    }

    public void store(int index, FieldValue value) {
        indexToValueMap.put(index, value);
        valueToIndexMap.put(value, index);
    }

    public boolean containsValue(FieldValue value) {
        return valueToIndexMap.containsKey(value);
    }

    public FieldValue lookup(int index) {
        return (FieldValue) indexToValueMap.get(index);
    }
}
