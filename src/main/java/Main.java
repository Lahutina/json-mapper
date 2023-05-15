import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String rawString = """
                {
                  "name": "Serhii",
                  "year": 29,
                  "isAdmin": false,
                  "workHours": 29.12
                }
                                """;

        User user = OksanaMapper.readFromString(rawString, User.class);
        System.out.println(user);
    }
}

class User {
    private String name;
    private int year;
    private boolean isAdmin;

    private double workHours;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", isAdmin=" + isAdmin +
                ", workHours=" + workHours +
                '}';
    }
}
