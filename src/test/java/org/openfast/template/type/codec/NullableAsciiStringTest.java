package org.openfast.template.type.codec;

import org.openfast.DeserializationContext;
import org.openfast.error.FastConstants;
import org.openfast.error.FastException;
import org.openfast.test.OpenFastTestCase;

public class NullableAsciiStringTest extends OpenFastTestCase {

	public void testEncodeDecode() {  
		assertEncodeDecode(null, "10000000", ValuesCodecs.NULLABLE_ASCII);
		assertEncodeDecode(string(""), "00000000 10000000", ValuesCodecs.NULLABLE_ASCII);
	}
	
	public void testOverlong() {
		try {
			ValuesCodecs.NULLABLE_ASCII.decode(bitStream("00000000 11000001"), new DeserializationContext());
			fail();
		} catch (FastException e) {
			assertEquals(FastConstants.R9_STRING_OVERLONG, e.getCode());
		}
	}
}
