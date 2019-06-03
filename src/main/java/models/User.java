package models;

import javax.persistence.*;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String login;
    private String password;
    private String role;

    public User() {}

    public User(String name, String login, String password) {
        setName(name);
        setLogin(login);
        setPassword(password);
    }

    public void setId(int newId) {
        if(newId>0) {
            id = newId;
        } else {
            throw new IllegalArgumentException("Value <= 0");
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if(login!=null && !login.equals("")) {
            this.login = login;
        } else {
            throw new IllegalArgumentException("String is null or empty");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(login!=null && !login.equals("")) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("String is null or empty");
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = "ROLE_USER";
    }

    public int getId() {
        return id;
    }

    public void setName(String newName) {
        if(newName!=null && !newName.equals("")) {
            name = newName;
        } else {
            throw new IllegalArgumentException("String is null or empty");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\nid: " + id + " first name: " + name;
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof User) ) {
            return false;
        }
        User otherUser = (User) other;
        return this.id == otherUser.getId() &&
                this.name.equals(otherUser.getName());
    }
}
