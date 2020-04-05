package org.openfast.impl;

import java.io.InputStream;

import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageBlockReader;
import org.openfast.template.type.codec.ValuesCodecs;

public class CmeTcpReplayMessageBlockReader implements MessageBlockReader {
	CmeMessageBlockReader preambleReader = new CmeMessageBlockReader();
    long lengthIndicator = 0;

	public void messageRead(InputStream in, Message message) {
	}

	public boolean readBlock(InputStream in, Context context) {
        lengthIndicator = ValuesCodecs.UINT.decode(in, context.getDeserializationContext()).toLong();
        return preambleReader.readBlock(in, context);
	}

    public long getLastLengthIndicator() {
        return lengthIndicator;
    }
	
	public long getLastSeqNum() {
		return preambleReader.getLastSeqNum();
	}

	public long getLastSubId() {
		return preambleReader.getLastSubId();
	}

	@Override
	public String toString() {
		return "(" + getLastLengthIndicator() + "|" + getLastSeqNum() + "|" + getLastSubId() + ")";
	}
}

