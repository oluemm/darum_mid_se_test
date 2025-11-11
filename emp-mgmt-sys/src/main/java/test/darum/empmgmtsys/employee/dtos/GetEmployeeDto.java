package test.darum.empmgmtsys.employee.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetEmployeeDto {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}