package org.openfast;

import org.openfast.template.type.codec.ErrorContext;
import org.openfast.util.PatchableByteArrayOutputStream;

public class DeserializationContext {
	private final PatchableByteArrayOutputStream buffer = new PatchableByteArrayOutputStream();
	private final ErrorContext errorContext = new ErrorContext();

	public PatchableByteArrayOutputStream getBuffer() {
		buffer.reset();
		return buffer;
	}

	public ErrorContext getErrorContext() {
		return errorContext;
	}
}
