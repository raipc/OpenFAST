package org.openfast.util;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class PatchableByteArrayOutputStream extends ByteArrayOutputStream {
	/**
	 * Returns the internal buffer. Size may be greater than actual data
	 * @return internal byte array
	 */
	public byte[] getRawArray() {
		return buf;
	}

	public synchronized byte getValueAt(int position) {
		if (position < 0 || position >= count) {
			throw new IllegalArgumentException("Position must be in interval [0, " + (count - 1) + "]");
		}
		return buf[position];
	}

	public synchronized void setValueAt(byte value, int position) {
		if (position < 0 || position >= count) {
			throw new IllegalArgumentException("Position must be in interval [0, " + (count - 1) + "]");
		}
		buf[position] = value;
	}

	public synchronized void ensureCapacity(int capacity) {
		int oldCapacity = buf.length;
		if (capacity > oldCapacity) {
			int newCapacity = Math.max(Math.min(oldCapacity << 1, Integer.MAX_VALUE - 8), capacity);
			buf = Arrays.copyOf(buf, newCapacity);
		}
	}
}
