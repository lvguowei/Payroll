package main.java.payroll.transactions;

import main.java.payroll.entities.Employee;

public class ChangeAddressTransaction extends ChangeEmployeeTransaction {

    private String itsAddress;

    public ChangeAddressTransaction(int itsEmpId, String itsAddress) {
        super(itsEmpId);
        this.itsAddress = itsAddress;
    }

    @Override
    public void change(Employee employee) {
        employee.setAddress(itsAddress);
    }
}
