package com.manvendra;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Override
//    public void run(String... args) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode json = objectMapper.createObjectNode();
//        json.put("username", "manvendra1097");
//        json.put("password", "abcd");
//
//        // Convert the JSON object to a string
//        String plainText = json.toString();
//
//        // Encrypt the plain text
//        String encryptedCredentials = aesUtil.encrypt(plainText);
//
//        System.out.println("Encrypted Credentials: " + encryptedCredentials);
//    }
}
