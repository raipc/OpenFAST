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
import java.util.Map;

import org.openfast.template.Group;


public class TemplateDictionary implements Dictionary {
    protected Map<Group, Map<QName, ScalarValue>> table = new HashMap<>();

    public ScalarValue lookup(Group template, QName key, QName applicationType) {
        Map<QName, ScalarValue> map = table.get(template);
        return map != null ? map.getOrDefault(key, ScalarValue.UNDEFINED) : ScalarValue.UNDEFINED;
    }

    public void reset() {
        table.clear();
    }

    public void store(Group group, QName applicationType, QName key, ScalarValue valueToEncode) {
        table.computeIfAbsent(group, k -> new HashMap<>()).put(key, valueToEncode);
    }

    public String toString() {
        if (table.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Group, Map<QName, ScalarValue>> entry : table.entrySet()) {
            builder.append("Dictionary: Template=").append(entry.getKey());
            Map<QName, ScalarValue> templateMap = entry.getValue();
            for (Map.Entry<QName, ScalarValue> templateMapEntry : templateMap.entrySet()) {
                builder.append(templateMapEntry.getKey()).append("=").append(templateMapEntry.getValue()).append("\n");
            }
        }
        return builder.toString();
    }
}
