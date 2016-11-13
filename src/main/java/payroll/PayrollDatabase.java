package main.java.payroll;

import main.java.payroll.entities.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PayrollDatabase {

    public static PayrollDatabase instance = new PayrollDatabase();

    private Map<Integer, Employee> itsEmployees;
    private Map<Integer, Employee> itsUnionMembers;

    private PayrollDatabase() {
        itsEmployees = new HashMap<>();
        itsUnionMembers = new HashMap<>();
    }

    public Employee getEmployee(int empId) {return itsEmployees.get(empId);}

    public void clear() {itsEmployees.clear();}

    public void addEmployee(int empid, Employee employee) {itsEmployees.put(empid, employee);}

    public void deleteEmployee(int employId) {itsEmployees.remove(employId);}

    public Employee getUnionMember(int memberId) {
        return itsUnionMembers.get(memberId);
    }

    public void addUnionMember(int memberId, Employee e) {
        itsUnionMembers.put(memberId, e);
    }

    public void removeUnionMember(int memberId) {
        itsUnionMembers.remove(memberId);
    }

    public Set<Integer> getAllEmployeeIds() {
        return itsEmployees.keySet();
    }
}
