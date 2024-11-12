package com.example.CustomMapper;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.IDToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class CustomProtocolMapper extends AbstractOIDCProtocolMapper 
        implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {


    public static final String PROVIDER_ID = "custom-protocol-mapper";

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    static {
        OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
        OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, CustomProtocolMapper.class);
    }

    @Override
    public String getDisplayCategory() {
        return "Token Mapper";
    }

    @Override
    public String getDisplayType() {
        return "Custom Token Mapper";
    }

    @Override
    public String getHelpText() {
        return "Adds credential details to the claim";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    protected void setClaim(IDToken token, ProtocolMapperModel mappingModel,
                            UserSessionModel userSession, KeycloakSession keycloakSession,
                            ClientSessionContext clientSessionCtx) {

        // String userId = userSession.getUser().getId();
        String claimValue = fetchCredentialDetails(userSession.getUser().getId(), userSession.getUser().getEmail());

        if (claimValue != null) {
            OIDCAttributeMapperHelper.mapClaim(token, mappingModel, claimValue);
        } else {
            System.out.println("No credential found for user: ");
        }
    }

    private String fetchCredentialDetails(String id, String email) {
        String url = "http://localhost:8082/api/credentials/extract-active";
        HttpClient client = HttpClient.newHttpClient();
        String json = String.format("{\"userEmail\":\"%s\"}", email);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: " + response.body()); // Debug response
            
            if (response.statusCode() != 200) {
                System.out.println("Failed to fetch credentials: " + response.statusCode());
                return null; // Handle non-200 responses
            }

            // Directly return the string response
            String encodedValue = response.body(); // Use the response string as the claim value
            return encodedValue.isEmpty() ? null : encodedValue;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            return null; // Return a default value or handle the error
        }
    }
}











// generating random number
// package com.example.CustomMapper;

// import org.keycloak.models.ClientSessionContext;
// import org.keycloak.models.KeycloakSession;
// import org.keycloak.models.ProtocolMapperModel;
// import org.keycloak.models.UserSessionModel;
// import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
// import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
// import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
// import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
// import org.keycloak.provider.ProviderConfigProperty;
// import org.keycloak.representations.IDToken;
// import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// public class CustomProtocolMapper extends AbstractOIDCProtocolMapper
//         implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {

//     public static final String PROVIDER_ID = "random-number-protocol-mapper";

//     private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

//     static {
//         OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
//         OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, CustomProtocolMapper.class);
//     }

//     @Override
//     public String getDisplayCategory() {
//         return "Token Mapper";
//     }

//     @Override
//     public String getDisplayType() {
//         return "Random Number Token Mapper";
//     }

//     @Override
//     public String getHelpText() {
//         return "Adds a random number to the claim.";
//     }

//     @Override
//     public List<ProviderConfigProperty> getConfigProperties() {
//         return configProperties;
//     }

//     @Override
//     public String getId() {
//         return PROVIDER_ID;
//     }

//     @Override
//     protected void setClaim(IDToken token, ProtocolMapperModel mappingModel,
//                             UserSessionModel userSession, KeycloakSession keycloakSession,
//                             ClientSessionContext clientSessionCtx) {
//         Random random = new Random();
//         int randomNumber = random.nextInt(1000000); // Generate a random number
//         OIDCAttributeMapperHelper.mapClaim(token, mappingModel, String.valueOf(randomNumber));
//     }
// }





// encrypting the random number
// package com.example.CustomMapper;

// import org.keycloak.models.ClientSessionContext;
// import org.keycloak.models.KeycloakSession;
// import org.keycloak.models.ProtocolMapperModel;
// import org.keycloak.models.UserSessionModel;
// import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
// import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
// import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
// import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
// import org.keycloak.provider.ProviderConfigProperty;
// import org.keycloak.representations.IDToken;
// import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;

// import javax.crypto.Cipher;
// import javax.crypto.KeyGenerator;
// import javax.crypto.SecretKey;
// import javax.crypto.spec.IvParameterSpec;
// import java.security.SecureRandom;
// import java.util.ArrayList;
// import java.util.Base64;
// import java.util.List;
// import java.util.Random;

// public class RandomNumberProtocolMapper extends AbstractOIDCProtocolMapper
//         implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {

//     public static final String PROVIDER_ID = "random-number-protocol-mapper";
//     private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();
//     private static final int AES_KEY_SIZE = 256;

//     static {
//         OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
//         OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, RandomNumberProtocolMapper.class);
//     }

//     @Override
//     public String getDisplayCategory() {
//         return "Token Mapper";
//     }

//     @Override
//     public String getDisplayType() {
//         return "Random Number Token Mapper";
//     }

//     @Override
//     public String getHelpText() {
//         return "Adds an encrypted random number to the claim.";
//     }

//     @Override
//     public List<ProviderConfigProperty> getConfigProperties() {
//         return configProperties;
//     }

//     @Override
//     public String getId() {
//         return PROVIDER_ID;
//     }

//     @Override
//     protected void setClaim(IDToken token, ProtocolMapperModel mappingModel,
//                             UserSessionModel userSession, KeycloakSession keycloakSession,
//                             ClientSessionContext clientSessionCtx) {
//         try {
//             // Generate a random number
//             Random random = new Random();
//             int randomNumber = random.nextInt(1000000); // Generate a random number

//             // Generate a random AES key
//             SecretKey secretKey = generateAESKey();
//             // Encrypt the random number
//             String encryptedRandomNumber = encrypt(String.valueOf(randomNumber), secretKey);

//             // Optionally, store the key securely if needed for decryption later
//             // For demonstration purposes, we'll skip key storage

//             // Add the encrypted random number to the token
//             OIDCAttributeMapperHelper.mapClaim(token, mappingModel, encryptedRandomNumber);
//         } catch (Exception e) {
//             e.printStackTrace();
//             // Handle exceptions appropriately
//         }
//     }

//     private SecretKey generateAESKey() throws Exception {
//         KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//         keyGenerator.init(AES_KEY_SIZE);
//         return keyGenerator.generateKey();
//     }

//     private String encrypt(String data, SecretKey secretKey) throws Exception {
//         // Generate a random IV
//         byte[] iv = new byte[16];
//         new SecureRandom().nextBytes(iv);
//         IvParameterSpec ivParams = new IvParameterSpec(iv);

//         // Create cipher instance
//         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//         cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);

//         // Encrypt the data
//         byte[] encryptedData = cipher.doFinal(data.getBytes());

//         // Combine IV and encrypted data for transmission
//         byte[] combined = new byte[iv.length + encryptedData.length];
//         System.arraycopy(iv, 0, combined, 0, iv.length);
//         System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);

//         // Return Base64-encoded string
//         return Base64.getEncoder().encodeToString(combined);
//     }
// }



