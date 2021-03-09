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

import java.math.BigDecimal;
import java.math.BigInteger;
import org.openfast.error.FastConstants;

public class DecimalValue extends NumericValue {
    private static final long serialVersionUID = 1L;
    private static final ThreadLocal<StringBuilder> sb = ThreadLocal.withInitial(StringBuilder::new);

    public final int exponent;
    public final long mantissa;

    public DecimalValue(double value) {
        if (value == 0.0) {
            this.exponent = 0;
            this.mantissa = 0;

            return;
        }

        BigDecimal decimalValue = BigDecimal.valueOf(value);
        int exponent = decimalValue.scale();
        long mantissa = decimalValue.unscaledValue().longValue();

        while (((mantissa % 10) == 0) && (mantissa != 0)) {
            mantissa /= 10;
            exponent -= 1;
        }

        this.mantissa = mantissa;
        this.exponent = -exponent;
    }

    public DecimalValue(long mantissa, int exponent) {
        this.mantissa = mantissa;
        this.exponent = exponent;
    }

    public DecimalValue(BigDecimal bigDecimal) {
        this.mantissa = bigDecimal.unscaledValue().longValue();
        this.exponent = -1 * bigDecimal.scale();
    }

    @Override
    public NumericValue increment() {
        return null;
    }

    @Override
    public NumericValue decrement() {
        return null;
    }

    @Override
    public Object toObject() {
        return toBigDecimal();
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof DecimalValue)) {
            return false;
        }

        return equals((DecimalValue) obj);
    }

    public boolean equals(DecimalValue other) {
        return other.mantissa == this.mantissa && other.exponent == this.exponent;
    }

    @Override
    public NumericValue subtract(NumericValue subtrahend) {
        return new DecimalValue(toBigDecimal().subtract(((DecimalValue)subtrahend).toBigDecimal()));
    }

    @Override
    public NumericValue add(NumericValue addend) {
        return new DecimalValue(toBigDecimal().add(((DecimalValue)addend).toBigDecimal()));
    }

    @Override
    public String serialize() {
        return toString();
    }

    @Override
    public boolean equals(int value) {
        return false;
    }

    @Override
    public long toLong() {
        if (exponent < 0)
            Global.handleError(FastConstants.R5_DECIMAL_CANT_CONVERT_TO_INT, "");
        return (getValue());
    }

    @Override
    public int toInt() {
        long value = getValue();
        if (exponent < 0 || (value) > Integer.MAX_VALUE)
            Global.handleError(FastConstants.R5_DECIMAL_CANT_CONVERT_TO_INT, "");
        return (int) (value);
    }

    @Override
    public short toShort() {
        long value = getValue();
        if (exponent < 0 || (value) > Short.MAX_VALUE)
            Global.handleError(FastConstants.R5_DECIMAL_CANT_CONVERT_TO_INT, "");
        return (short) (value);
    }

    @Override
    public byte toByte() {
        long value = getValue();
        if (exponent < 0 || (value) > Byte.MAX_VALUE)
            Global.handleError(FastConstants.R5_DECIMAL_CANT_CONVERT_TO_INT, "");
        return (byte) (value);
    }

    private long getValue() {
        return mantissa * ((long) Math.pow(10, exponent));
    }

    /**
     * The double value should be rounded using a given precision by users of this method.
     */
    @Override
    public double toDouble() {
        return mantissa * Math.pow(10.0, exponent);
    }

    @Override
    public BigDecimal toBigDecimal() {
        return new BigDecimal(BigInteger.valueOf(mantissa), -exponent);
    }

    @Override
    public String toString() {
//        return "Not Calculated";
//        return toBigDecimal().toPlainString();
        final StringBuilder s = sb.get();
        s.setLength(0);
        appendValue(s, null);
        return s.toString();
    }

    @Override
    public int hashCode() {
        return exponent * 37 + (int) mantissa;
    }

    @Override
    public void appendValue(StringBuilder sb, String nullValue) {
        appendDecimal(mantissa, exponent, sb);
    }

    private static void appendDecimal(long mantissa, int exponent, StringBuilder sb) {
        if (mantissa == 0) {
            sb.append('0');
        } else if (mantissa < 0) {
            appendDecimal(-mantissa, exponent, sb.append('-'));
        } else if (exponent == 0) {
            sb.append(mantissa);
        } else if (exponent > 0) {
            sb.append(mantissa);
            for (int i = 0; i < exponent; i++) {
                sb.append('0');
            }
        } else {
            appendFloatingPointNumber(mantissa, exponent, sb);
        }
    }

    private static void appendFloatingPointNumber(long mantissa, int shift, StringBuilder sb) {
        int digits = numberOfDigits(mantissa);
        int fpLocation = digits + shift;
        if (fpLocation <= 0) {
            sb.append("0.");
            for (int i = -fpLocation; i > 0 ; i--) {
                sb.append('0');
            }
            sb.append(mantissa);
        } else {
            final int offset = sb.length();
            sb.ensureCapacity(digits + 1);
            sb.append(mantissa);
            sb.insert(offset + fpLocation, '.');
        }
    }

    private static int numberOfDigits (long n) {
        // Guessing 4 digit numbers will be more probable.
        // They are set in the first branch.
        if (n < 10000L) { // from 1 to 4
            if (n < 100L) { // 1 or 2
                if (n < 10L) {
                    return 1;
                } else {
                    return 2;
                }
            } else { // 3 or 4
                if (n < 1000L) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else  { // from 5 a 20 (albeit longs can't have more than 18 or 19)
            if (n < 1000000000000L) { // from 5 to 12
                if (n < 100000000L) { // from 5 to 8
                    if (n < 1000000L) { // 5 or 6
                        if (n < 100000L) {
                            return 5;
                        } else {
                            return 6;
                        }
                    } else { // 7 u 8
                        if (n < 10000000L) {
                            return 7;
                        } else {
                            return 8;
                        }
                    }
                } else { // from 9 to 12
                    if (n < 10000000000L) { // 9 or 10
                        if (n < 1000000000L) {
                            return 9;
                        } else {
                            return 10;
                        }
                    } else { // 11 or 12
                        if (n < 100000000000L) {
                            return 11;
                        } else {
                            return 12;
                        }
                    }
                }
            } else { // from 13 to ... (18 or 20)
                if (n < 10000000000000000L) { // from 13 to 16
                    if (n < 100000000000000L) { // 13 or 14
                        if (n < 10000000000000L) {
                            return 13;
                        } else {
                            return 14;
                        }
                    } else { // 15 or 16
                        if (n < 1000000000000000L) {
                            return 15;
                        } else {
                            return 16;
                        }
                    }
                } else { // from 17 to ...¿20?
                    if (n < 1000000000000000000L) { // 17 or 18
                        if (n < 100000000000000000L) {
                            return 17;
                        } else {
                            return 18;
                        }
                    } else { // 19? Can it be?
                        // 10000000000000000000L is'nt a valid long.
                        return 19;
                    }
                }
            }
        }
    }
}
