package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class CustomerCredentials {
    public String mail;
    public String password;
    public String encryptedPassword;

    public CustomerCredentials(String mail, String password){
        this.mail = mail;
        this.password = password;
    }
}
