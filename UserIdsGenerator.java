public class UserIdsGenerator {
    private static UserIdsGenerator generator;
    private int Id = 0;

    private UserIdsGenerator(){}

    public static UserIdsGenerator getInstance() {
        if (generator == null) {
            generator = new UserIdsGenerator();
        }

        return generator;
    }

    public int generateId() {
        Id++;
        return Id;
    }

    public int getId() {
        return Id;
    }
}