package main.java.payroll.transactions;

import main.java.payroll.entities.Employee;
import main.java.payroll.PayrollDatabase;

abstract class ChangeEmployeeTransaction implements Transaction {

    private int itsEmpId;

    public ChangeEmployeeTransaction(int empId) {
        this.itsEmpId = empId;
    }

    public abstract void change(Employee employee);

    @Override
    public void execute() {
        Employee e = PayrollDatabase.instance.getEmployee(itsEmpId);
        if (e != null) {
            change(e);
        }
    }
}
