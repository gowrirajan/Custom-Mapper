// package com.example.CustomMapper.mapper;

// import org.keycloak.models.KeycloakSession;
// import org.keycloak.models.UserModel;
// import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
// import org.keycloak.protocol.oidc.mappers.ProtocolMapper;
// import org.keycloak.provider.ProviderConfigProperty;
// import org.keycloak.representations.AccessToken;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// public class CustomMapper extends AbstractOIDCProtocolMapper {

//     @Override
//     public String getId() {
//         return "custom-random-number-mapper"; // Unique ID for your mapper
//     }

//     @Override
//     public String getDisplayCategory() {
//         return "Custom Mappers"; // Category in Keycloak
//     }

//     @Override
//     public String getDisplayType() {
//         return "Random Number Mapper"; // Display name in Keycloak
//     }

//     @Override
//     public String getHelpText() {
//         return "Generates a random number and adds it to the ID token."; // Help text
//     }

//     @Override
//     public void transformIdToken(KeycloakSession session, AccessToken token, UserModel user) {
//         Random random = new Random();
//         int randomNumber = random.nextInt(100000); // Generate a random number
//         token.getOtherClaims().put("random_number", String.valueOf(randomNumber)); // Add it to the ID token
//     }

//     @Override
//     public List<ProviderConfigProperty> getConfigProperties() {
//         return new ArrayList<>(); // Return an empty list if there are no additional config properties
//     }
// }
