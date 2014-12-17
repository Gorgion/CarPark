/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Represents user credentails.
 *
 * @author Tomas Svoboda
 */
@Entity
public class UserCredentials implements Serializable
{
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", length = 80)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Embedded
    private UserRole role;

    public UserCredentials()
    {
    }

    public UserCredentials(String username, String password, Boolean enabled, User user, UserRole role)
    {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.user = user;
        this.role = role;
    }

    @PrePersist
    public void prePersist()
    {       
        if (isEnabled() == null)
        {
            setEnabled(true);
        }
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public UserRole getRole()
    {
        return role;
    }

    public void setRole(UserRole role)
    {
        this.role = role;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.userId);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final UserCredentials other = (UserCredentials) obj;
        if (!Objects.equals(this.userId, other.userId))
        {
            return false;
        }
        return true;
    }
}
