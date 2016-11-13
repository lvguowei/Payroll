package main.java.payroll.transactions;

import main.java.payroll.*;
import main.java.payroll.entities.Employee;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentmethods.HoldMethod;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;

abstract class AddEmployeeTransaction implements Transaction {

    private int itsEmpid;
    private String itsAddress;
    private String itsName;

    AddEmployeeTransaction(int empid, String name, String address) {
        this.itsEmpid = empid;
        this.itsAddress = address;
        this.itsName = name;
    }

    abstract PaymentClassification getClassification();

    abstract PaymentSchedule getSchedule();

    @Override
    public void execute() {
        Employee e = new Employee(itsEmpid, itsName, itsAddress);
        e.setClassification(getClassification());
        e.setSchedule(getSchedule());
        e.setMethod(new HoldMethod());
        PayrollDatabase.instance.addEmployee(itsEmpid, e);
    }
}
