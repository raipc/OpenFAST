/*
The contents of this file are subject to the Mozilla Public License
Version 1.1 (the "License"); you may not use this file except in
compliance with the License. You may obtain a copy of the License at
http://www.mozilla.org/MPL/

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
License for the specific language governing rights and limitations
under the License.

The Original Code is OpenFAST.

The Initial Developer of the Original Code is The LaSalle Technology
Group, LLC.  Portions created by The LaSalle Technology Group, LLC
are Copyright (C) The LaSalle Technology Group, LLC. All Rights Reserved.

Contributor(s): Jacob Northey <jacob@lasalletech.com>
                Craig Otis <cotis@lasalletech.com>
*/
package org.openfast;

import java.util.stream.IntStream;

import org.openfast.template.LongValue;
import org.openfast.util.Util;

public abstract class NumericValue extends ScalarValue {
    private static final long serialVersionUID = 1L;
    public abstract NumericValue increment();
    public abstract NumericValue decrement();
    public abstract NumericValue subtract(NumericValue priorValue);
    public abstract NumericValue add(NumericValue addend);
    public abstract boolean equals(int value);
    public abstract long toLong();
    public abstract int toInt();

    public static NumericValue create(long value) {
        if (value <= CacheHolder.CACHE_SIZE && value >= 0) {
            return CacheHolder.FIRST_INT_VALUES[(int)value];
        }
        return Util.isBiggerThanInt(value) ? new LongValue(value) : new IntegerValue((int) value);
    }

    private static class CacheHolder {
        private static final int CACHE_SIZE = resolveCacheSize();
        private static final IntegerValue[] FIRST_INT_VALUES = IntStream.rangeClosed(0, CACHE_SIZE)
                .mapToObj(IntegerValue::new)
                .toArray(IntegerValue[]::new);

        private static int resolveCacheSize() {
            return Integer.parseInt(System.getProperty("openfast.integer.cache.size", "10000"));
        }
    }
}
