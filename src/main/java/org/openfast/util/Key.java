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
package org.openfast.util;

import java.util.Objects;

public class Key {
    private final Object a;
    private final Object b;

    public Key(Object key1, Object key2) {
        if (key1 == null) {
            throw new NullPointerException("key1 must not ne null");
        }
        if (key2 == null) {
            throw new NullPointerException("key2 must not ne null");
        }
        this.a = key1;
        this.b = key2;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Key))
            return false;
        Key other = (Key) obj;
        return Objects.equals(a, other.a) && Objects.equals(b, other.b);
    }

    public int hashCode() {
        int hashCode = a.hashCode();
        return hashCode + b.hashCode() * 37;
    }

    public String toString() {
        return "Key(a=" + a + ",b=" + b + ")";
    }
}
