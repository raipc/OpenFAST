package org.openfast.template.type.codec;

import java.io.IOException;
import java.io.InputStream;

import org.openfast.Global;
import org.openfast.error.FastConstants;

public class DecodeHelpers {
	private DecodeHelpers(){}


	public static long decodeInt(InputStream in, ErrorContext errorContext) {
	    errorContext.hasError = false;
	    long value = 0;
	    try {
	        int byt = in.read();
	        if (byt < 0) {
	            Global.handleError(FastConstants.END_OF_STREAM, "The end of the input stream has been reached.");
	            errorContext.hasError = true;
	            return -1; // short circuit if global error handler does not throw exception
	        }
	        if ((byt & 0x40) > 0) {
	            value = -1;
	        }
	        value = (value << 7) | (byt & 0x7f);
	        while ((byt & 0x80) == 0) {
	            byt = in.read();
	            if (byt < 0) {
	                Global.handleError(FastConstants.END_OF_STREAM, "The end of the input stream has been reached.");
	                return -1; // short circuit if global error handler does not throw exception
	            }
	            value = (value << 7) | (byt & 0x7f);
	        }
	    } catch (IOException e) {
	        Global.handleError(FastConstants.IO_ERROR, "A IO error has been encountered while decoding.", e);
	        errorContext.hasError = true;
	        return -1; // short circuit if global error handler does not throw exception
	    }
	    return value;
	}

	public static long decodeUInt(InputStream in) {
	    long value = 0;
	    int byt;
	    try {
	        do {
	            byt = in.read();
	            if (byt < 0) {
	                Global.handleError(FastConstants.END_OF_STREAM, "The end of the input stream has been reached.");
	                return -1; // short circuit if global error handler does not throw exception
	            }
	            value = (value << 7) | (byt & 0x7f);
	        } while ((byt & 0x80) == 0);
	    } catch (IOException e) {
	        Global.handleError(FastConstants.IO_ERROR, "A IO error has been encountered while decoding.", e);
	        return -1; // short circuit if global error handler does not throw exception
	    }
	    return value;
	}
}
