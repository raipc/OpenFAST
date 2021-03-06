package org.openfast.template.type.codec;

import org.openfast.test.OpenFastTestCase;

public class NullableByteVectorTest extends OpenFastTestCase {

	public void testEncoding() {
		assertEncodeDecode(null, "10000000", ValuesCodecs.NULLABLE_BYTE_VECTOR_TYPE);
		assertEncodeDecode(byt(new byte[] { 0x00 }), "10000010 00000000", ValuesCodecs.NULLABLE_BYTE_VECTOR_TYPE);
		assertEncodeDecode(byt(new byte[] { 0x00, 0x7F }), "10000011 00000000 01111111", ValuesCodecs.NULLABLE_BYTE_VECTOR_TYPE);
	}
	
}
