package test.darum.empmgmtsys.employee.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import test.darum.empmgmtsys.common.constants.Errors;

@Getter
@Setter
@AllArgsConstructor
public class CreateEmployeeDto {

  @NotNull(message = Errors.REQUIRED)
  private String firstName;
  private String lastName;

  @NotNull(message = Errors.REQUIRED)
  @Email(message = "Provide a valid email address")
  private String email;
  
  @NotNull(message = Errors.REQUIRED)
  private String status;
}
