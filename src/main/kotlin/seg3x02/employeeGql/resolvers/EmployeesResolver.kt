package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.UUID

@Controller
class EmployeesResolver(
    private val employeeRepository: EmployeesRepository
) {
	@QueryMapping
    fun employees(): List<Employee> {
        return employeeRepository.findAll()
    }

    @MutationMapping
    fun newEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput): Employee {
        if (input.name == null || input.dateOfBirth == null || input.city == null || input.salary == null) {
            throw Exception("Invalid input")
        }

        val employee = Employee(input.name, input.dateOfBirth, input.city, input.salary, input.gender, input.email)
        employee.id = UUID.randomUUID().toString()
        employeeRepository.save(employee)
        return employee
    }
}
