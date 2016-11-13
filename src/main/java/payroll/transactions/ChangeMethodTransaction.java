package main.java.payroll.transactions;

import main.java.payroll.entities.Employee;
import main.java.payroll.entities.paymentmethods.PaymentMethod;

abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {

    ChangeMethodTransaction(int empId) {
        super(empId);
    }

    public abstract PaymentMethod getMethod();

    @Override
    public void change(Employee employee) {
        employee.setMethod(getMethod());
    }
}
