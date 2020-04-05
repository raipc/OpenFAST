package org.openfast.template.type.codec;

import org.openfast.DeserializationContext;
import org.openfast.error.FastConstants;
import org.openfast.error.FastException;
import org.openfast.template.operator.Operator;
import org.openfast.template.type.Type;
import org.openfast.test.OpenFastTestCase;

public class AsciiStringTest extends OpenFastTestCase {

	public void testDecodeEmptyString() {
		TypeCodec coder = Type.ASCII.getCodec(Operator.NONE, false);
		
		assertEquals(string(""), coder.decode(bitStream("10000000"), new DeserializationContext()));
		assertEquals(string("\u0000"), coder.decode(bitStream("00000000 10000000"), new DeserializationContext()));
		
		try {
			coder.decode(bitStream("00000000 11000001"), new DeserializationContext());
			fail();
		} catch (FastException e) {
			assertEquals(FastConstants.R9_STRING_OVERLONG, e.getCode());
		}
	}
}
