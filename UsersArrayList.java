public class UsersArrayList implements UserList {
    private int size;
    private User[] users;
    private int numberOfUsers;

    public UsersArrayList() {
        size = 10;
        users = new User[10];
        numberOfUsers = 0;
    }

    public void addUser(User user){
        if (numberOfUsers < size - 1) {
            users[numberOfUsers] = user;
            numberOfUsers++;
        }
        else {
            User[] bufUsers = new User[size * 2];
            for (int i = 0; i <= numberOfUsers; i++)
                bufUsers[i] = users[i];
            users = bufUsers;
            size = size * 2;
            users[numberOfUsers] = user;
            numberOfUsers++;
        }
    }

    public User retrieveUserById(int id){
        for (int i = 0; i <= numberOfUsers; i++){
            if (users[i].getIdentifier() == id)
                return(users[i]);
        }
        throw new UserNotFoundException("No user with specified id");
    }

    public User retrieveUserByIndex(int index){
        if (index > numberOfUsers)
            throw new UserNotFoundException("No user with specified index");
        return(users[index]);
    }

    public int retrieveNumberOfUsers(){
        return(numberOfUsers);
    }

    public User[] getUsersArray() {
        return users;
    }
}
