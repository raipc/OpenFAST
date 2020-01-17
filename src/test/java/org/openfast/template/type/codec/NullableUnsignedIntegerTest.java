package org.openfast.template.type.codec;

import org.openfast.test.OpenFastTestCase;

public class NullableUnsignedIntegerTest extends OpenFastTestCase {
    public void testEncodeDecode() {
        assertEncodeDecode(null, "10000000", ValuesCodecs.NULLABLE_UNSIGNED_INTEGER);
        assertEncodeDecode(i(126), "11111111", ValuesCodecs.NULLABLE_UNSIGNED_INTEGER);
        assertEncodeDecode(i(16382), "01111111 11111111", ValuesCodecs.NULLABLE_UNSIGNED_INTEGER);
        assertEncodeDecode(i(7), "10001000", ValuesCodecs.NULLABLE_UNSIGNED_INTEGER);
        assertEncodeDecode(i(0), "10000001", ValuesCodecs.NULLABLE_UNSIGNED_INTEGER);
        assertEncodeDecode(i(1), "10000010", ValuesCodecs.NULLABLE_UNSIGNED_INTEGER);
        assertEncodeDecode(i(942755), "00111001 01000101 10100100", ValuesCodecs.NULLABLE_UNSIGNED_INTEGER);
    }
}
