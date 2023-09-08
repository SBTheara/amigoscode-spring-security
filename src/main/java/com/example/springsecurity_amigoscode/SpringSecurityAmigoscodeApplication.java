package com.example.springsecurity_amigoscode;
import com.example.springsecurity_amigoscode.entity.Role;
import com.example.springsecurity_amigoscode.entity.User;
import com.example.springsecurity_amigoscode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class SpringSecurityAmigoscodeApplication{

  @Autowired private UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityAmigoscodeApplication.class, args);
  }
  @Bean
    CommandLineRunner commandLineRunner (){
      return args -> {
        userRepository.save(new User(0,"theara","sambath","thearaholsiht@gmail.com","123", Role.USER));
      };
  }
}
