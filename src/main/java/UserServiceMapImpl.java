import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserServiceMapImpl implements IUserService {

    private Map<String, User> userMap;

    public UserServiceMapImpl() {
        userMap = new HashMap<String, User>();
    }

    public Collection<User> getUsers() {
        return userMap.values();
    }

    public User getUser(String username) {
        return userMap.get(username.toUpperCase());
    }

    public int addUser(User user) throws APIException {
        userMap.put(user.getLogin().getUsername().toUpperCase(), user);
        return userMap.size();
    }

    public User updateUser(String username, User user) throws APIException {
        userMap.replace(username, user);
        return userMap.get(username);
    }

    public User deleteUser(String username) throws APIException {
        return userMap.remove(username);
    }

    public boolean userExists(String username) {
        return userMap.containsKey(username);
    }

}
