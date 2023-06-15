package utils;

import java.util.HashMap;

public class Validator {
    // if add new attribute, please add it to last position
    public enum AttributeName {
        TYPE,
        USE,
        REF,
        FORMAT
    }

    // if add new value type, please add it to last position
    public enum ValueType {
        DEFAULT,
        NUMBER,
        LETTERS,
        EMAIL,
        PHONE,
        DATE,
        TIME
    }

    // if add new use type, please add it to last position
    public enum UseType {
        DEFAULT,
        REQUIRED
    }

    // if add new format type, please add it to last position
    public enum FormatType {
        FALSE,
        TRUE
    }

    public boolean validateAttribute(String attributeName, String attributeValue) {
        AttributeName attribute;
        try {
            attribute = AttributeName.valueOf(attributeName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid attribute name: " + attributeName);
        }

        switch (attribute) {
            case TYPE:
                return validateValueType(attributeValue);
            case USE:
                return validateUseType(attributeValue);
            case REF:
                return validateRef(attributeValue);
            case FORMAT:
                return validateFormatType(attributeValue);
            default:
                return false;
        }
    }

    public boolean isRequired(String type) {
        try {
            UseType useType = UseType.valueOf(type);

            switch (useType) {
                case REQUIRED:
                    return true;
                default:
                    return false;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid use type: " + type);
        }
    }

    public boolean isHost(String host) {
        return host.matches(
                "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$")
                || host.matches(
                        "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}");
    }

    public boolean isPort(String port) {
        return port.matches("^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
    }

    public boolean validateValue(String type, String value) {
        if (value.isBlank()) {
            return true;
        }

        try {
            ValueType valueType = ValueType.valueOf(type);

            switch (valueType) {
                case NUMBER:
                    return isValidNumber(value);
                case LETTERS:
                    return isValidLetters(value);
                case EMAIL:
                    return isValidEmail(value);
                case PHONE:
                    return isValidPhone(value);
                case DATE:
                    return isValidDate(value);
                case TIME:
                    return isValidTime(value);
                default:
                    return true;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value: " + value);
        }
    }

    public boolean validateRefArray(HashMap<String, Integer> headers, String[] rowData) {
        if (!CurrentValues.Attributes.get("REF").equals("[]")) {
            String[] listRef = CurrentValues.Attributes.get("REF").split(";");
            for (int i = 0; i < listRef.length; i++) {
                String key = "";
                String value = "";

                String[] ref = listRef[i].split("=", -1);
                key = ref[0];

                if (!headers.containsKey(key)) {
                    throw new IllegalArgumentException("Invalid references: " + key);
                }

                if (ref.length > 1) {
                    value = ref[1];
                    String data = rowData[headers.get(key)] == null || rowData[headers.get(key)].isBlank() ? "" : rowData[headers.get(key)];
                    if (data.equals(value)) {
                        return true;
                    }
                    return false;
                }

                if (rowData[headers.get(key)] == null || rowData[headers.get(key)].isBlank()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateValueType(String type) {
        try {
            ValueType.valueOf(type);

            return true;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value type: " + type);
        }
    }

    private boolean validateUseType(String type) {
        try {
            UseType.valueOf(type);

            return true;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid use type: " + type);
        }
    }

    private boolean validateFormatType(String type) {
        try {
            FormatType.valueOf(type);

            return true;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid format type: " + type);
        }
    }

    private boolean validateRef(String ref) {
        if (ref.matches("^(\\w+\\={0,1}\\w*\\;)+$")) {
            return true;
        }

        throw new IllegalArgumentException("Invalid references format: " + ref);
    }

    private boolean isValidNumber(String value) {
        return value.matches("^[0-9]+$");
    }

    private boolean isValidLetters(String value) {
        return value.matches("^[a-zA-Z]+( [a-zA-Z]+)*$");
    }

    private boolean isValidEmail(String value) {
        return value.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    private boolean isValidPhone(String value) {
        return value.matches("^0[0-9]{9,10}$");
    }

    private boolean isValidDate(String value) {
        return value.matches("^19[0-9]{2}|2[0-9]{3}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$");
    }

    private boolean isValidTime(String value) {
        return value.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
    }

}
