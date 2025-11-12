package test.darum.empmgmtsys.department.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import test.darum.empmgmtsys.common.constants.Errors;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateDepartmentDto {
  @NotNull(message = Errors.REQUIRED)
  private String name;

  @NotNull(message = Errors.REQUIRED)
  @Email(message = "Provide a valid email address")
  private String managerEmail;

}
