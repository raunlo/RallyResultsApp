package com.ralohmus.rallyresults.core.service;

import com.ralohmus.rallyresults.core.domain.api.key.ApiKey;
import com.ralohmus.rallyresults.core.service.ports.application.ApiKeyPort;
import com.ralohmus.rallyresults.core.service.ports.persistence.ApiKeyPersistencePort;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;


@Service
public class ApiKeyService extends BaseService<ApiKey, ApiKeyPersistencePort> implements ApiKeyPort {

    ApiKeyService(ApiKeyPersistencePort apiKeyPersistencePort) {
        super(apiKeyPersistencePort);
    }

    @Override
    public ApiKey save(ApiKey apiKey) {
        if(apiKey.getValue() != null) {
            return super.save(apiKey);
        } else {
            return super.save(apiKey.setValue(generateKey()));
        }
    }

   private String generateKey(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            var secretKey = keyGenerator.generateKey();
            byte[] encoded = secretKey.getEncoded();
            return DatatypeConverter.printHexBinary(encoded).toLowerCase();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
   }
}
