package com.lambdaschool.shoppingcart.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/** This model allows us to create roles like Admin or
 * Data and increases the usefulness of the User model
 * */
//spring framework knows to connect it to the roles table
@Entity
@Table(name = "roles")
public class Role extends Auditable{

    /**Fields**/
    @Id
    private long roleid;

    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoles> users = new HashSet<>();

    /**Constructors**/
    public Role() {
    }

    public Role(String name, long roleid){
        this.roleid = roleid;
        this.name = name;
    }

    /**Getters and Setters**/
    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRoles> getUsers() {
        return users;
    }

    public void setUsers(Set<UserRoles> users) {
        this.users = users;
    }
}
