package ca.wjpg.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * TODO: insert comment
 *
 * @author craiger
 *         Date: 11-11-23
 *         Time: 6:41 PM
 */
@XmlRootElement(name="users")
public class UserList {
    private List<User> users;

    public UserList() {}
    public UserList(List<User> users) {
        this.users = users;
    }
    
    @XmlElement(name="user")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
