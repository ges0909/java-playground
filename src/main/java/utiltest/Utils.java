package utiltest;

class Utils {
    public static String camelToUpperAndUnderscore(String camel) {
        StringBuffer underscore = new StringBuffer();
        for (char ch : camel.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                underscore.append('_');
            } else if (Character.isDigit(ch)) {
                underscore.append('_').append(ch).append('_');
            } else {
                underscore.append(Character.toUpperCase(ch));
            }
        }
        return underscore.toString();
    }
}