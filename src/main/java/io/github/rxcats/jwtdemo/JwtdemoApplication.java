package io.github.rxcats.jwtdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class JwtdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtdemoApplication.class, args);
    }

    @Autowired
    private JwtService jwtService;

    @Bean
    public CommandLineRunner runner() {
        return (args) -> {

            String token = jwtService.createToken("user1@rxcats.github.io");
            log.info("created token:{}", token);

            String userId = jwtService.getUserId(token);
            log.info("decoded userId:{}", userId);

            boolean verifyToken = jwtService.verifyToken(token, userId);
            log.info("verifyToken:{}", verifyToken);

            boolean verifyToken2 = jwtService.verifyToken(token, userId + "a");
            log.info("verifyToken2:{}", verifyToken2);

        };
    }

}
