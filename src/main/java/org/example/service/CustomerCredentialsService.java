package org.example.service;

import org.example.model.CustomerCredentials;

public interface CustomerCredentialsService {
      boolean isCustomerValid(CustomerCredentials customerCredentials);
      void encryptCredentials(CustomerCredentials customerCredentials);
}
