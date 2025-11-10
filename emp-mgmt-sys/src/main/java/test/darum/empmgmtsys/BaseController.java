package test.darum.empmgmtsys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RefreshScope
public class BaseController {
  @Value("${app.name}")
  private String appName;
  @Value("${spring.mvc.servlet.path}")
  private String basePath;

  @GetMapping("configs")
  public String getAppName() {
    return "Application name: " + appName;
  }

  @GetMapping("/")
  public RedirectView index() {

    return new RedirectView(basePath + "/swagger-ui/index.html");
  }
}
