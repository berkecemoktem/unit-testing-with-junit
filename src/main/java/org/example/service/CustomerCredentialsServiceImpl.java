package org.example.service;

import org.example.model.CustomerCredentials;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerCredentialsServiceImpl implements CustomerCredentialsService{
    @Override
    public boolean isCustomerValid(CustomerCredentials customerCredentials) {
        String mail = customerCredentials.getMail();
        String password = customerCredentials.getPassword();

        if(password.toLowerCase().length() <= 10 && (mail.contains("@gmail.com") || mail.contains("@hotmail.com"))){
            return true;
        }
        return false;
    }

    @Override
    public void encryptCredentials(CustomerCredentials customerCredentials) {
        if (customerCredentials == null || customerCredentials.getPassword() == null) {
            throw new NullPointerException("Invalid customer credentials");
        }

        System.out.println("Original password: " + customerCredentials.getPassword().toString());

        String passwordToEncrypt = customerCredentials.getPassword();
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            md.update(passwordToEncrypt.getBytes());

            // Get the hashed bytes
            byte[] hashedBytes = md.digest();

            // Convert hashed bytes to hexadecimal format
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }

            // Set the encrypted password back to the customer credentials
            customerCredentials.setEncryptedPassword(hexStringBuilder.toString());
            System.out.println("Encrypted password: " + customerCredentials.getEncryptedPassword());
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception based on your requirements
            e.printStackTrace();
            // You might want to throw a custom exception or log the error
        }
    }

}
