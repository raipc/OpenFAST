package org.openfast.template.type.codec;

public final class ValuesCodecs {
	// Codec Definitions
	public static final UnsignedInteger UINT = new UnsignedInteger();
	public static final SignedInteger INTEGER = new SignedInteger();
	public static final AsciiString ASCII = new AsciiString();
	public static final UnicodeString UNICODE = new UnicodeString();
	public static final BitVectorType BIT_VECTOR = new BitVectorType();
	public static final ByteVectorType BYTE_VECTOR = new ByteVectorType();
	public static final SingleFieldDecimal SF_SCALED_NUMBER = new SingleFieldDecimal();
	public static final StringDelta STRING_DELTA = new StringDelta();
	public static final NullableUnsignedInteger NULLABLE_UNSIGNED_INTEGER = new NullableUnsignedInteger();
	public static final NullableSignedInteger NULLABLE_INTEGER = new NullableSignedInteger();
	public static final NullableAsciiString NULLABLE_ASCII = new NullableAsciiString();
	public static final NullableUnicodeString NULLABLE_UNICODE = new NullableUnicodeString();
	public static final NullableByteVector NULLABLE_BYTE_VECTOR_TYPE = new NullableByteVector();
	public static final NullableSingleFieldDecimal NULLABLE_SF_SCALED_NUMBER = new NullableSingleFieldDecimal();
	public static final NullableStringDelta NULLABLE_STRING_DELTA = new NullableStringDelta();
	// DATE CODECS
	public static final DateString DATE_STRING = new DateString("yyyyMMdd");
	public static final DateInteger DATE_INTEGER = new DateInteger();
	public static final DateString TIMESTAMP_STRING = new DateString("yyyyMMddhhmmssSSS");
	public static final TimestampInteger TIMESTAMP_INTEGER = new TimestampInteger();
	public static final EpochTimestamp EPOCH_TIMESTAMP = new EpochTimestamp();
	public static final DateString TIME_STRING = new DateString("hhmmssSSS");
	public static final TimeInteger TIME_INTEGER = new TimeInteger();
	public static final MillisecondsSinceMidnight TIME_IN_MS = new MillisecondsSinceMidnight();
}
