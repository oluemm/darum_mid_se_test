package test.darum.empmgmtsys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {
  @Value("${app.name}")
  private String appName;
  
  @GetMapping("get-config")
  public String getAppName() {
    return "Application name: " + appName;
  }
}
