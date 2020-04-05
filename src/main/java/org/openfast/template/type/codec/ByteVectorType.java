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
/**
 *
 */
package org.openfast.template.type.codec;

import java.io.IOException;
import java.io.InputStream;

import org.openfast.ByteVectorValue;
import org.openfast.DeserializationContext;
import org.openfast.Global;
import org.openfast.IntegerValue;
import org.openfast.ScalarValue;
import org.openfast.error.FastConstants;

final class ByteVectorType extends TypeCodec {
    private static final long serialVersionUID = 1L;

    ByteVectorType() {}
    /**
     * Takes a ScalarValue object, and converts it to a byte array
     * 
     * @param value
     *            The ScalarValue to be encoded
     * @return Returns a byte array of the passed object
     */
    public byte[] encode(ScalarValue value) {
        byte[] bytes = value.getBytes();
        int lengthSize = IntegerCodec.getUnsignedIntegerSize(bytes.length);
        byte[] encoding = new byte[bytes.length + lengthSize];
        byte[] length = ValuesCodecs.UINT.encode(new IntegerValue(bytes.length));
        System.arraycopy(length, 0, encoding, 0, lengthSize);
        System.arraycopy(bytes, 0, encoding, lengthSize, bytes.length);
        return encoding;
    }
    /**
     * Reads in a stream of data and stores it to a ByteVectorValue object
     * 
     * @param in
     *            The InputStream to be decoded
     * @param deserializationContext
     * @return Returns a new ByteVectorValue object with the data stream as an
     *         array
     */
    public ScalarValue decode(InputStream in, DeserializationContext deserializationContext) {
        int length = (int) DecodeHelpers.decodeUInt(in);
        byte[] encoding = new byte[length];
        return decodeByteBuffer(encoding, length, in) ? new ByteVectorValue(encoding) : null;
    }

    /**
     * Decodes byte buffer from input stream and put the result into pre-allocated byte array
     * @param buffer byte array that will store the result
     * @param length expected size. Must be less than or equal to buffer.length
     * @param in InputStream which contains data to decode
     * @return false in case of error, true if successful
     */
    public static boolean decodeByteBuffer(byte[] buffer, int length, InputStream in) {
        for (int i = 0; i < length; i++) {
            try {
                int nextByte = in.read();
                if (nextByte < 0) {
                    Global.handleError(FastConstants.END_OF_STREAM, "The end of the input stream has been reached.");
                    return false; // short circuit if global error handler does not throw exception
                }
                buffer[i] = (byte) nextByte;
            } catch (IOException e) {
                Global.handleError(FastConstants.IO_ERROR, "An IO error has been encountered while decoding.", e);
                return false; // short circuit if global error handler does not throw exception
            }
        }
        return true;
    }

    public byte[] encodeValue(ScalarValue value) {
        throw new UnsupportedOperationException();
    }
    /**
     * @return Returns a new ByteVectorValue object with the passed value
     */
    public ScalarValue fromString(String value) {
        return new ByteVectorValue(value.getBytes());
    }
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass();
    }
}
