package main.java.payroll.transactions;

import main.java.payroll.PayrollDatabase;

public class DeleteEmployeeTransaction implements Transaction {

    private int employId;

    public DeleteEmployeeTransaction(int employId) {
        this.employId = employId;
    }

    @Override
    public void execute() {
        PayrollDatabase.instance.deleteEmployee(employId);
    }
}
