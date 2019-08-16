import java.util.Collection;

public interface IUserService {

    public Collection<User> getUsers();

    public User getUser(String username);

    public int addUser(User user) throws APIException;

    public User updateUser(String username, User user) throws APIException;

    public User deleteUser(String username) throws APIException;

    public boolean userExists(String username);

}