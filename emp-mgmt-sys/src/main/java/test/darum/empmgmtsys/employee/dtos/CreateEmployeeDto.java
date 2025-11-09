package test.darum.empmgmtsys.employee.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateEmployeeDto {
  @NotNull
  private String firstName;
  private String lastName;
  @NotNull
  private String email;
  @NotNull
  private String status;
}