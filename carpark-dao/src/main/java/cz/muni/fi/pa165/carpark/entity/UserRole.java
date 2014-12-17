/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * User's role.
 *
 * @author Tomas Svoboda
 */
@Embeddable
public class UserRole implements Serializable
{

    @Column(length = 50, nullable = false)
    private String roleName;    

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.roleName);
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
        final UserRole other = (UserRole) obj;
        if (!Objects.equals(this.roleName, other.roleName))
        {
            return false;
        }

        return true;
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
