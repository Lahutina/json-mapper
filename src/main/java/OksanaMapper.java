import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class OksanaMapper {
    public static <T> T readFromString(String rawString, Class<T> userClass) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        T object = userClass.getDeclaredConstructor().newInstance();
        Field[] classFields = userClass.getDeclaredFields();
        Map<String, Object> parsedJsonMap = parseJsonToMap(rawString);

        for (Field field : classFields) {
            if (parsedJsonMap.containsKey(field.getName())) {
                Object value = parsedJsonMap.get(field.getName());
                field.setAccessible(true);
                field.set(object, value);
            }
        }
        return object;
    }

    private static Map<String, Object> parseJsonToMap(String json) {
        Map<String, Object> jsonMap = new HashMap<>();

        json = json.trim();
        json = json.substring(1, json.length() - 1);
        String[] keyValuePairs = json.split(",");

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");

            String key = keyValue[0].trim().replaceAll("\"", "");
            Object value = parseValue(keyValue[1].trim());

            jsonMap.put(key, value);
        }
        return jsonMap;
    }

    private static Object parseValue(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        } else if (value.equals("true") || value.equals("false")) {
            return Boolean.valueOf(value);
        } else {
            if (value.contains(".")) {
                return Double.valueOf(value);
            } else {
                return Integer.valueOf(value);
            }
        }
    }
}
