package test.darum.empmgmtsys.department.dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UpdateDepartmentDto {
  private UUID id;
  private String name;

  @Email(message = "Provide a valid email address")
  private String managerEmail;
}
