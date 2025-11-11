package test.darum.empmgmtsys.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.darum.empmgmtsys.common.exceptions.NotFoundException;
import test.darum.empmgmtsys.common.exceptions.ResourceConflictException;
import test.darum.empmgmtsys.employee.Employee;
import test.darum.empmgmtsys.employee.EmployeeMapper;
import test.darum.empmgmtsys.employee.EmployeeRepository;
import test.darum.empmgmtsys.employee.EmployeeService;
import test.darum.empmgmtsys.employee.dtos.CreateEmployeeDto;
import test.darum.empmgmtsys.employee.dtos.GetEmployeeDto;
import test.darum.empmgmtsys.employee.dtos.UpdateEmployeeDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private EmployeeMapper mapper;

  @InjectMocks
  private EmployeeService employeeService;

  private final UUID employeeUuid = UUID.fromString("12345678-1234-1234-1234-1234567890ab");
  private final String employeeId = employeeUuid.toString();
  private Employee employee;
  private GetEmployeeDto getEmployeeDto;

  @BeforeEach
  void setUp() {
    // Setup common objects for testing
    employee = new Employee();
    employee.setId(employeeUuid);
    employee.setEmail("test@example.com");

    getEmployeeDto = new GetEmployeeDto(employeeUuid, "Test", "User", "test@example.com", "ACTIVE",
                                        LocalDateTime.now(), LocalDateTime.now());
  }

  @Test
  void create_ShouldSucceed_WhenEmployeeDoesNotExist() {
    // Arrange
    CreateEmployeeDto createDto = new CreateEmployeeDto("Test", null, "new@example.com", "User");
    Employee newEmployee = new Employee();
    Employee savedEmployee = employee;

    when(employeeRepository.findByEmail(createDto.getEmail())).thenReturn(null);
    when(mapper.toEntity(createDto)).thenReturn(newEmployee);
    when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
    when(mapper.toDto(savedEmployee)).thenReturn(getEmployeeDto);

    // Act
    GetEmployeeDto result = employeeService.create(createDto);

    // Assert
    assertNotNull(result);
    assertEquals(getEmployeeDto.getId(), result.getId());
    verify(employeeRepository, times(1)).findByEmail(createDto.getEmail());
    verify(employeeRepository, times(1)).save(any(Employee.class));
    verify(mapper, times(1)).toEntity(createDto);
    verify(mapper, times(1)).toDto(savedEmployee);
  }

  @Test
  void create_ShouldThrowResourceConflictException_WhenEmployeeAlreadyExists() {
    // Arrange
    CreateEmployeeDto createDto = new CreateEmployeeDto("Test", "User", "test@example.com", "ACTIVE");
    when(employeeRepository.findByEmail(createDto.getEmail())).thenReturn(employee);

    // Act & Assert
    assertThrows(ResourceConflictException.class, () -> employeeService.create(createDto));
    verify(employeeRepository, times(1)).findByEmail(createDto.getEmail());
    verify(employeeRepository, never()).save(any(Employee.class));
  }

  @Test
  void update_ShouldSucceed_WhenEmployeeExists() {
    // Arrange
    UpdateEmployeeDto updateDto = new UpdateEmployeeDto("Updated", "User", "updated@example.com", "ACTIVE");

    when(employeeRepository.findById(employeeUuid)).thenReturn(Optional.of(employee));
    when(employeeRepository.save(employee)).thenReturn(employee);
    when(mapper.toDto(employee)).thenReturn(getEmployeeDto);

    // Act
    GetEmployeeDto result = employeeService.update(employeeId, updateDto);

    // Assert
    assertNotNull(result);
    verify(employeeRepository, times(1)).findById(employeeUuid);
    verify(mapper, times(1)).update(updateDto, employee);
    verify(employeeRepository, times(1)).save(employee);
    verify(mapper, times(1)).toDto(employee);
  }

  @Test
  void update_ShouldThrowNotFoundException_WhenEmployeeDoesNotExist() {
    // Arrange
    UpdateEmployeeDto updateDto = new UpdateEmployeeDto("Updated", "User", "updated@example.com", "ACTIVE");
    when(employeeRepository.findById(employeeUuid)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NotFoundException.class, () -> employeeService.update(employeeId, updateDto));
    verify(employeeRepository, times(1)).findById(employeeUuid);
    verify(employeeRepository, never()).save(any(Employee.class));
  }

  @Test
  void update_ShouldThrowNotFoundException_WhenInvalidIdFormat() {
    // Arrange
    String invalidId = "not-a-uuid";
    UpdateEmployeeDto updateDto = new UpdateEmployeeDto("Updated", "User", "updated@example.com", "ACTIVE");

    // Act & Assert
    assertThrows(NotFoundException.class, () -> employeeService.update(invalidId, updateDto));
    verify(employeeRepository, never()).findById(any(UUID.class));
  }

  @Test
  void delete_ShouldSucceed_WhenEmployeeExists() {
    // Arrange
    when(employeeRepository.findById(employeeUuid)).thenReturn(Optional.of(employee));
    doNothing().when(employeeRepository).delete(employee);

    // Act
    employeeService.delete(employeeId);

    // Assert
    verify(employeeRepository, times(1)).findById(employeeUuid);
    verify(employeeRepository, times(1)).delete(employee);
  }

  @Test
  void delete_ShouldThrowNotFoundException_WhenEmployeeDoesNotExist() {
    // Arrange
    when(employeeRepository.findById(employeeUuid)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NotFoundException.class, () -> employeeService.delete(employeeId));
    verify(employeeRepository, times(1)).findById(employeeUuid);
    verify(employeeRepository, never()).delete(any(Employee.class));
  }

  @Test
  void delete_ShouldThrowNotFoundException_WhenInvalidIdFormat() {
    // Arrange
    String invalidId = "not-a-uuid";

    // Act & Assert
    assertThrows(NotFoundException.class, () -> employeeService.delete(invalidId));
    verify(employeeRepository, never()).findById(any(UUID.class));
  }

  @Test
  void findById_ShouldReturnEmployeeDto_WhenEmployeeExists() {
    // Arrange
    when(employeeRepository.findById(employeeUuid)).thenReturn(Optional.of(employee));
    when(mapper.toDto(employee)).thenReturn(getEmployeeDto);

    // Act
    GetEmployeeDto result = employeeService.findById(employeeId);

    // Assert
    assertNotNull(result);
    assertEquals(getEmployeeDto.getId(), result.getId());
    verify(employeeRepository, times(1)).findById(employeeUuid);
    verify(mapper, times(1)).toDto(employee);
  }

  @Test
  void findById_ShouldThrowNotFoundException_WhenEmployeeDoesNotExist() {
    // Arrange
    when(employeeRepository.findById(employeeUuid)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NotFoundException.class, () -> employeeService.findById(employeeId));
    verify(employeeRepository, times(1)).findById(employeeUuid);
    verify(mapper, never()).toDto(any(Employee.class));
  }

  @Test
  void findById_ShouldThrowNotFoundException_WhenInvalidIdFormat() {
    // Arrange
    String invalidId = "not-a-uuid";

    // Act & Assert
    assertThrows(NotFoundException.class, () -> employeeService.findById(invalidId));
    verify(employeeRepository, never()).findById(any(UUID.class));
  }

  @Test
  void getEmployees_ShouldReturnListOfEmployeeDtos() {
    // Arrange
    Employee employee2 = new Employee();
    employee2.setId(UUID.randomUUID());
    GetEmployeeDto getEmployeeDto2 = new GetEmployeeDto(employee2.getId(), "Jane", "Doe", "jane@example.com", "ACTIVE"
        , LocalDateTime.now(), LocalDateTime.now());

    List<Employee> employeeList = Arrays.asList(employee, employee2);

    when(employeeRepository.findAll()).thenReturn(employeeList);
    when(mapper.toDto(employee)).thenReturn(getEmployeeDto);
    when(mapper.toDto(employee2)).thenReturn(getEmployeeDto2);

    // Act
    List<GetEmployeeDto> result = employeeService.getEmployees();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(getEmployeeDto.getId(), result.get(0).getId());
    assertEquals(getEmployeeDto2.getId(), result.get(1).getId());
    verify(employeeRepository, times(1)).findAll();
    verify(mapper, times(2)).toDto(any(Employee.class));
  }

  @Test
  void getEmployees_ShouldReturnEmptyList_WhenNoEmployeesExist() {
    // Arrange
    List<Employee> employeeList = List.of();
    when(employeeRepository.findAll()).thenReturn(employeeList);

    // Act
    List<GetEmployeeDto> result = employeeService.getEmployees();

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(employeeRepository, times(1)).findAll();
    verify(mapper, never()).toDto(any(Employee.class));
  }
}