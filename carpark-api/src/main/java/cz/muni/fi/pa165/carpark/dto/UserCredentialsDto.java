/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents user credentails.
 *
 * @author Tomas Svoboda
 */
public class UserCredentialsDto implements Serializable
{
    private Long userId;

    private String username;

    private String password;

    private Boolean enabled;

    private UserDto user;

    private UserRoleDto role;

    public UserCredentialsDto()
    {
    }

    public UserCredentialsDto(String username, String password, Boolean enabled, UserDto user, UserRoleDto role)
    {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.user = user;
        this.role = role;
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


    public UserDto getUser()
    {
        return user;
    }

    public void setUser(UserDto user)
    {
        this.user = user;
    }

    public UserRoleDto getRole()
    {
        return role;
    }

    public void setRole(UserRoleDto role)
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
        final UserCredentialsDto other = (UserCredentialsDto) obj;
        if (!Objects.equals(this.userId, other.userId))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "UserCredentials{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", enabled=" + enabled +  ", user=" + user + ", role=" + role + '}';
    }

}
