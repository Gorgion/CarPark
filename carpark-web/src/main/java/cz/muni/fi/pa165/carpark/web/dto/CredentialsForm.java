/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.dto;

import cz.muni.fi.pa165.carpark.dto.UserRoleDto;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * UserForm dto.
 *
 * @author Tomas Svoboda
 */
public class CredentialsForm
{        
    private String username;    
    
    @NotNull
    private UserRoleDto.RoleType role;

    public UserRoleDto.RoleType getRole()
    {
        return role;
    }

    public void setRole(UserRoleDto.RoleType role)
    {
        this.role = role;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
