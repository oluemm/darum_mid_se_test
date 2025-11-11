package test.darum.empmgmtsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
public class EmpMgmtSysApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmpMgmtSysApplication.class, args);
  }

}
