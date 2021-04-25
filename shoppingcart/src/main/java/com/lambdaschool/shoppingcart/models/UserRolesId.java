package com.lambdaschool.shoppingcart.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**Creates a type for the UserRoles primary key**/
@Embeddable
public class UserRolesId implements Serializable {

    /**Fields**/
    private long user;
    private long role;

    /**Constructor**/
    public UserRolesId() {
    }

    /**Getters and Setters**/
    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getRole() {
        return role;
    }

    public void setRole(long role) {
        this.role = role;
    }

    /**Equals and HashCode**/
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        UserRolesId that = (UserRolesId) o;
        return user == that.user &&
            role == that.role;
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
