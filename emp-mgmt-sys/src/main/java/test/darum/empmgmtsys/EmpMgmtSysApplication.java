package test.darum.empmgmtsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EmpMgmtSysApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmpMgmtSysApplication.class, args);
  }

}
