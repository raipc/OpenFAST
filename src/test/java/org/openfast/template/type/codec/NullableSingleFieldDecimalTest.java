package org.openfast.template.type.codec;

import org.openfast.test.OpenFastTestCase;

public class NullableSingleFieldDecimalTest extends OpenFastTestCase {

	public void testEncodeDecode() {
		assertEncodeDecode(null, "10000000", ValuesCodecs.NULLABLE_SF_SCALED_NUMBER);
	}

}
