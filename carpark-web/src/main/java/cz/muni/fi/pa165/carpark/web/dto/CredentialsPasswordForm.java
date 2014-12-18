/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.dto;

import cz.muni.fi.pa165.carpark.validation.PasswordsNotEqual;
import org.hibernate.validator.constraints.NotBlank;

/**
 * UserForm dto.
 *
 * @author Tomas Svoboda
 */
@PasswordsNotEqual(passwordFieldName = "password", passwordVerificationFieldName = "confirmPassword")
public class CredentialsPasswordForm
{            
    @NotBlank
    private String password;    

    @NotBlank
    private String confirmPassword;  
    
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }
}
