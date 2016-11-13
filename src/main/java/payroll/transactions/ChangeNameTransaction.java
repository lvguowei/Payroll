package main.java.payroll.transactions;

import main.java.payroll.entities.Employee;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {

    private String itsName;

    public ChangeNameTransaction(int empId, String name) {
        super(empId);
        this.itsName = name;
    }

    @Override
    public void change(Employee employee) {
        employee.setName(itsName);
    }
}
