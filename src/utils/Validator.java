package utils;

public class Validator {
    public enum AttributeName {
        TYPE,
        USE
    }

    public enum ValueType {
        NUMBER,
        LETTERS,
        EMAIL,
        PHONE,
        DATE,
        TIME,
        DEFAULT
    }

    public enum UseType {
        REQUIRED,
        DEFAULT
    }

    public boolean validateAttribute(String attributeName, String attributeValue) {
        validateAttributeName(attributeName);

        AttributeName attribute = AttributeName.valueOf(attributeName);

        switch(attribute) {
            case TYPE:
                return validateValueType(attributeValue);
            case USE:
                return validateUseType(attributeValue);
        }
        return false;
    }

    private boolean validateAttributeName(String attributeName) {
        try {
            AttributeName.valueOf(attributeName);

            return true;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("INVALID ATTRIBUTE NAME: " + attributeName);
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
            throw new IllegalArgumentException("INVALID USE TYPE: " + type);
        }
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
            throw new IllegalArgumentException("INVALID VALUE: " + value);
        }
    }

    private boolean validateValueType(String type) {
        try {
            ValueType.valueOf(type);

            return true;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("INVALID VALUE TYPE: " + type);
        }
    }

    private boolean validateUseType(String type) {
        try {
            UseType.valueOf(type);

            return true;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("INVALID USE TYPE: " + type);
        }
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
