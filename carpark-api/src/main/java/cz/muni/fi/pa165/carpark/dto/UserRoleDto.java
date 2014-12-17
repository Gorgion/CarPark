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
    private String roleName;

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
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
