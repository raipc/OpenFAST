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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openfast.template.Group;

public class ApplicationTypeDictionary implements Dictionary {

    private Map<QName, Map<QName, ScalarValue>> dictionary = new HashMap<>();

    public ScalarValue lookup(Group template, QName key, QName applicationType) {
        Map<QName, ScalarValue> map = dictionary.get(template.getTypeReference());
        return map != null ? map.getOrDefault(key, ScalarValue.UNDEFINED) : ScalarValue.UNDEFINED;
    }

    public void reset() {
        dictionary = new HashMap<>();
    }

    public void store(Group group, QName applicationType, QName key, ScalarValue value) {
        dictionary.computeIfAbsent(group.getTypeReference(), k -> new HashMap<>()).put(key, value);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator templateIterator = dictionary.keySet().iterator();
        while (templateIterator.hasNext()) {
            Object type = templateIterator.next();
            builder.append("Dictionary: Type=" + type.toString());
            Map templateMap = (Map)dictionary.get(type);
            Iterator keyIterator = templateMap.keySet().iterator();
            while (keyIterator.hasNext()) {
                Object key = keyIterator.next();
                builder.append(key).append("=").append(templateMap.get(key)).append("\n");
            }
        }
        return builder.toString();
    }

}
