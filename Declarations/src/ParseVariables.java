import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseVariables {
    static void parseVariables(List<String> lines) {
        Pattern pattern = Pattern.compile("(\\w+)\\s+(\\w+)(?:\\s*=\\s*(.*?))?;");
        Map<String, String> defaultValues = getDefaultValues();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String type = matcher.group(1);
                String name = matcher.group(2);
                String value = matcher.group(3);

                if (value == null) {
                    value = defaultValues.getOrDefault(type, "object instance");
                }// end if

                System.out.printf("[%s] Line: %d, Type: %s, Value: %s%n", name, i + 1, type, value);
            }// end if
        }// end for
    }// end method

    private static java.util.Map<String, String> getDefaultValues() {
        java.util.Map<String, String> defaults = new HashMap<>();
        defaults.put("byte", "0");
        defaults.put("short", "0");
        defaults.put("int", "0");
        defaults.put("long", "0");
        defaults.put("float", "0.0");
        defaults.put("double", "0.0");
        defaults.put("boolean", "false");
        defaults.put("char", "null");
        defaults.put("String", "null");
        defaults.put("Scanner", "null");
        return defaults;
    }// end map
}// end class
