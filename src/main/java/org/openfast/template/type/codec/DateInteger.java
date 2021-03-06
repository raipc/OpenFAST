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
package org.openfast.template.type.codec;

import java.io.InputStream;
import java.util.Date;

import org.openfast.DateValue;
import org.openfast.DeserializationContext;
import org.openfast.IntegerValue;
import org.openfast.ScalarValue;
import org.openfast.util.Util;

public class DateInteger extends TypeCodec {
    private static final long serialVersionUID = 1L;

    public ScalarValue decode(InputStream in, DeserializationContext deserializationContext) {
        long longValue = ((ScalarValue) ValuesCodecs.UINT.decode(in, deserializationContext)).toLong();
        int year = (int) (longValue / 10000);
        int month = (int) ((longValue - (year * 10000)) / 100);
        int day = (int) (longValue % 100);
        return new DateValue(Util.date(year, month, day));
    }
    public byte[] encodeValue(ScalarValue value) {
        Date date = ((DateValue) value).value;
        int intValue = Util.dateToInt(date);
        return ValuesCodecs.UINT.encode(new IntegerValue(intValue));
    }
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass();
    }
}
