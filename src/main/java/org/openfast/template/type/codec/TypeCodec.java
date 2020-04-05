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
import java.io.Serializable;

import org.openfast.DeserializationContext;
import org.openfast.ScalarValue;

public abstract class TypeCodec implements Serializable {
    private static final long serialVersionUID = 1L;
    protected static final byte STOP_BIT = (byte) 0x80;
    static final byte[] NULL_VALUE_ENCODING = new byte[] { STOP_BIT };

    public abstract byte[] encodeValue(ScalarValue value);

    public abstract ScalarValue decode(InputStream in, DeserializationContext deserializationContext);

    /**
     * Template Method to encode the passed object, the actual encoding is done
     * in the encodeValue() method overridden in sub-classes.
     * <p>
     * <b>Note</b>: The final SBIT is set in this method, not in encodeValue().
     * </p>
     * 
     * @param value
     *            The ScalarValue object to be encoded
     * @return Returns an encoded byte array with an added stop bit at the end
     */
    public byte[] encode(ScalarValue value) {
        byte[] encoding = encodeValue(value);
        encoding[encoding.length - 1] |= STOP_BIT;
        return encoding;
    }

    /**
     * 
     * @return Returns false
     */
    public boolean isNullable() {
        return false;
    }
}
