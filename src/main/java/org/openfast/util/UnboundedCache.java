package org.openfast.util;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.ObjIntMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.koloboke.collect.map.hash.HashObjIntMaps;
import org.openfast.FieldValue;

public class UnboundedCache implements Cache {
    private int nextIndex = 1;
    private final IntObjMap<FieldValue> indexToValueMap = HashIntObjMaps.newMutableMap();
    private final ObjIntMap<FieldValue> valueToIndexMap = HashObjIntMaps.newMutableMap();

    public int getIndex(FieldValue value) {
        return valueToIndexMap.getInt(value);
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
        return indexToValueMap.get(index);
    }
}
