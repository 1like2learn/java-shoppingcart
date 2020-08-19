package com.lambdaschool.shoppingcart.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**Allows the connection of the role model and the User model.
 * The primary key for the table is a complex key composed of userid and roleid
 * */
@Entity
@Table(name = "userroles")
public class UserRoles extends Auditable implements Serializable {

    /**IDs**/
    @Id
    @ManyToOne
    @JoinColumn(name = "roleid")
    @JsonIgnoreProperties(value = "users")
    private Role role;

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "roles")
    private User user;

    /**Constructos**/
    public UserRoles() {
    }

    public UserRoles(
        Role role,
        User user) {
        this.role = role;
        this.user = user;
    }

    /**Getters and Setters**/
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRoles that = (UserRoles) o;
        return ((user == null) ? 0 : user.getUserid()) == ((that.user == null) ? 0 : that.user.getUserid()) &&
            ((role == null) ? 0 : role.getRoleid()) == ((that.role == null) ? 0 : that.role.getRoleid());
    }

    @Override
    public int hashCode() {
        return 37;
    }
}
