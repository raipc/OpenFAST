/*
The contents of this file are subject to the Mozilla Public License
Version 1.1 (the "License"); you may not use this file except in
compliance with the License. You may obtain a copy of the License at
http://www.mozilla.org/MPL/

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
License for the specific language governing rights and limitations
under the License.

The Original Code is OpenFAST.

The Initial Developer of the Original Code is The LaSalle Technology
Group, LLC.  Portions created by The LaSalle Technology Group, LLC
are Copyright (C) The LaSalle Technology Group, LLC. All Rights Reserved.

Contributor(s): Jacob Northey <jacob@lasalletech.com>
                Craig Otis <cotis@lasalletech.com>
 */
package org.openfast.template.operator;

import java.util.Locale;

import org.openfast.Global;
import org.openfast.ScalarValue;
import org.openfast.error.FastConstants;
import org.openfast.template.Scalar;
import org.openfast.template.type.Type;

public enum Operator {
    NONE {
        public boolean usesDictionary() {
            return false;
        }

        public boolean shouldStoreValue(ScalarValue value) {
            return false;
        }
    },
    CONSTANT {
        public void validate(Scalar scalar) {
            if (scalar.getDefaultValue().isUndefined())
                Global.handleError(FastConstants.S4_NO_INITIAL_VALUE_FOR_CONST, "The field " + scalar
                    + " must have a default value defined.");
        }

        public boolean shouldStoreValue(ScalarValue value) {
            return false;
        }

        public boolean usesDictionary() {
            return false;
        }
    },
    DEFAULT {
        public void validate(Scalar scalar) {
            if (!scalar.isOptional() && scalar.getDefaultValue().isUndefined())
                Global.handleError(FastConstants.S5_NO_INITVAL_MNDTRY_DFALT, "The field " + scalar
                    + " must have a default value defined.");
        }

        public boolean shouldStoreValue(ScalarValue value) {
            return value != null;
        }

    },
    COPY {
        public OperatorCodec getCodec(Type type) {
            return OperatorCodec.COPY_ALL;
        }
    },
    INCREMENT,
    DELTA {
        public boolean shouldStoreValue(ScalarValue value) {
            return value != null;
        }
    },
    TAIL;

    public static Operator getOperator(String name) {
        for (Operator operator : Operator.values()) {
            if (operator.name().equalsIgnoreCase(name)) {
                return operator;
            }
        }
        throw new IllegalArgumentException("The operator \"" + name + "\" does not exist.");
    }

    public OperatorCodec getCodec(Type type) {
        return OperatorCodec.getCodec(this, type);
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.US);
    }

    public boolean shouldStoreValue(ScalarValue value) {
        return true;
    }

    public void validate(Scalar scalar) {
        //do nothing
    }

    public boolean usesDictionary() {
        return true;
    }
}
