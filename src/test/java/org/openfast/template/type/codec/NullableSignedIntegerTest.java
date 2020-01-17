package org.openfast.template.type.codec;

import org.openfast.test.OpenFastTestCase;

public class NullableSignedIntegerTest extends OpenFastTestCase {

	public void testEncodeDecode() {
		assertEncodeDecode(null, "10000000", ValuesCodecs.NULLABLE_INTEGER);
		assertEncodeDecode(i(0), "10000001", ValuesCodecs.NULLABLE_INTEGER);
		assertEncodeDecode(i(638), "00000100 11111111", ValuesCodecs.NULLABLE_INTEGER);
		assertEncodeDecode(i(-2147483648), "01111000 00000000 00000000 00000000 10000000", ValuesCodecs.NULLABLE_INTEGER);
		assertEncodeDecode(i(-17), "11101111", ValuesCodecs.NULLABLE_INTEGER);
		assertEncodeDecode(i(547), "00000100 10100100", ValuesCodecs.NULLABLE_INTEGER);
		assertEncodeDecode(i(-5), "11111011", ValuesCodecs.NULLABLE_INTEGER);
		assertEncodeDecode(i(124322), "00000111 01001011 10100011", ValuesCodecs.NULLABLE_INTEGER);
	}

}
