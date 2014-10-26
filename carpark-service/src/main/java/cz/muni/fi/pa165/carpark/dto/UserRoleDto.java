/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * User's role.
 *
 * @author Tomas Svoboda
 */

public class UserRoleDto implements Serializable
{
    private Long id;
    private String roleName;
    private UserCredentialsDto userCredentials;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public UserCredentialsDto getUserCredentials()
    {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentialsDto userCredentials)
    {
        this.userCredentials = userCredentials;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final UserRoleDto other = (UserRoleDto) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Role{" + "id=" + id + ", roleName=" + roleName + ", user=" + userCredentials + '}';
    }

    public static enum RoleType
    {
        ADMIN("ROLE_ADMIN"),
        MANAGER("ROLE_MANAGER"),
        USER("ROLE_USER");

        private final String roleName;

        private RoleType(String roleName)
        {
            this.roleName = roleName;
        }

        @Override
        public String toString()
        {
            return roleName;
        }
    }
}
