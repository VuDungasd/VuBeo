package com.ptit.graduation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class GraduationProjectBeApplication {

  public static void main(String[] args) {

    SpringApplication.run(GraduationProjectBeApplication.class, args);

  }

}
