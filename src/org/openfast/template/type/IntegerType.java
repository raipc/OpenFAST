/**
 * 
 */
package org.openfast.template.type;

import java.util.HashMap;
import java.util.Map;

import org.openfast.Global;
import org.openfast.IntegerValue;
import org.openfast.ScalarValue;
import org.openfast.error.FastConstants;
import org.openfast.template.LongValue;
import org.openfast.template.type.codec.TypeCodec;
import org.openfast.util.Util;

public abstract class IntegerType extends SimpleType {
	protected static final Map MAX_VALUES = new HashMap();
	protected static final Map MIN_VALUES = new HashMap();
	static {
		MIN_VALUES.put(new Integer(8), new Byte(Byte.MIN_VALUE));
		MIN_VALUES.put(new Integer(16), new Short(Short.MIN_VALUE));
		MIN_VALUES.put(new Integer(32), new Integer(Integer.MIN_VALUE));
		MIN_VALUES.put(new Integer(64), new Long(Long.MIN_VALUE));
		
		MAX_VALUES.put(new Integer(8), new Byte(Byte.MAX_VALUE));
		MAX_VALUES.put(new Integer(16), new Short(Short.MAX_VALUE));
		MAX_VALUES.put(new Integer(32), new Integer(Integer.MAX_VALUE));
		MAX_VALUES.put(new Integer(64), new Long(Long.MAX_VALUE));
	}
	
	/**
	 * 
	 * @param numberBits The number of bits as an integer
	 * @return Returns a long of the max value of the passed integer
	 */
	protected static long getMax(int numberBits) {
		return ((Number) MAX_VALUES.get(new Integer(numberBits))).longValue();
	}
	
	/**
	 * 
	 * @param numberBits The number of bits as an integer
	 * @return Returns a long of the min value of the passed integer
	 */
	protected static long getMin(int numberBits) {
		return ((Number) MIN_VALUES.get(new Integer(numberBits))).longValue();
	}
	
	protected final long minValue;
	protected final long maxValue;

	public IntegerType(String typeName, long minValue, long maxValue, TypeCodec codec, TypeCodec nullableCodec) {
		super(typeName, codec, nullableCodec);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	/**
	 * @param value
	 * @return 
	 */
	protected ScalarValue getVal(String value) {
		long longValue;
		try {
			longValue = Long.parseLong(value);
		} catch (NumberFormatException e) {
			Global.handleError(FastConstants.S3_INITIAL_VALUE_INCOMP, "The value \"" + value + "\" is not compatable with type " + this);
			return null;
		}
		
        if (Util.isBiggerThanInt(longValue)) {
            return new LongValue(longValue);
        }

        return new IntegerValue((int) longValue);
	}

	/**
	 * @return Returns a default value
	 */
    public ScalarValue getDefaultValue() {
        return new IntegerValue(0);
    }

    /**
     * @param previousValue The previous value of the Field, used in 
	 * determining the corresponding field value for the current
	 * message being decoded.
     * @return Returns true if the passed value is an instance of an integer or long
     */
	public boolean isValueOf(ScalarValue previousValue) {
		return previousValue instanceof IntegerValue || previousValue instanceof LongValue;
	}
	
	/**
	 * Validates the passed ScalarValue, if fails, throws error.
	 * @param value The ScalarValue object to be validated
	 * 
	 */
	public void validateValue(ScalarValue value) {
		if (value == null || value.isUndefined()) return;
		if (value.toLong() > maxValue || value.toLong() < minValue) {
			Global.handleError(FastConstants.D2_INT_OUT_OF_RANGE, "The value " + value + " is out of range for type " + this);
		}
	}
}