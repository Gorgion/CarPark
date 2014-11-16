/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

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

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private UserDto user;

    private Set<UserRoleDto> roles;

    public UserCredentialsDto()
    {
    }

    public UserCredentialsDto(String username, String password, Boolean enabled, UserDto user, Set<UserRoleDto> roles)
    {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.user = user;
        this.roles = roles;
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

    public Boolean isAccountNonExpired()
    {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired)
    {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean isAccountNonLocked()
    {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked)
    {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean isCredentialsNonExpired()
    {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired)
    {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public UserDto getUser()
    {
        return user;
    }

    public void setUser(UserDto user)
    {
        this.user = user;
    }

    public Set<UserRoleDto> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<UserRoleDto> roles)
    {
        this.roles = roles;
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
        return "UserCredentials{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", enabled=" + enabled + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired=" + credentialsNonExpired + ", user=" + user + ", roles=" + roles + '}';
    }

}
