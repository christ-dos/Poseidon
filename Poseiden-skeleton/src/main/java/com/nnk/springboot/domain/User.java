package com.nnk.springboot.domain;

import com.nnk.springboot.security.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Class that manage the entity {@link User}
 *
 * @author Christine Duarte
 */
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * An Integer that contain the id of the {@link User}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * A String containing username of User
     */
    @NotBlank(message = "Username is mandatory")
    private String username;

    /**
     * A String containing password of User
     */
    @ValidPassword
    private String password;

    /**
     * A String containing fullname of User
     */
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    /**
     * A String containing role of User
     */
    @NotBlank(message = "Role is mandatory")
    private String role;

    /**
     * getter to obtain id
     *
     * @return An Integer with the id of User
     */
    public Integer getId() {
        return id;
    }

    /**
     * setter for attribute id
     *
     * @param id An Integer with the id of User
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * getter to obtain  username
     *
     * @return A String with the username of User
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username
     *
     * @param username A String with the username of User
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter to obtain  password
     *
     * @return A String with the password of User
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for  password
     *
     * @param password A String with the password of User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter to obtain  fullname
     *
     * @return A String with the fullname of User
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * setter for fullname
     *
     * @param fullname A String with the fullname of User
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * getter to obtain  role
     *
     * @return A String with the role of User
     */
    public String getRole() {
        return role;
    }

    /**
     * setter for role
     *
     * @param role A String with the role of User
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Method toString
     *
     * @return a String of the object User
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
