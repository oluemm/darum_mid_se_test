package test.darum.empmgmtsys.employee.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateEmployeeDto {

  @NotNull(message = "First name is required")
  private String firstName;
  private String lastName;

  @NotNull(message = "Email is required")
  @Email(message = "Provide a valid email address")
  private String email;
  
  @NotNull(message = "User's status is required")
  private String status;
}
