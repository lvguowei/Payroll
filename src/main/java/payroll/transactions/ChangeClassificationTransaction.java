package main.java.payroll.transactions;

import main.java.payroll.entities.Employee;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;

abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

    ChangeClassificationTransaction(int itsEmpId) {
        super(itsEmpId);
    }

    public abstract PaymentClassification getClassification();

    public abstract PaymentSchedule getSchedule();

    @Override
    public void change(Employee employee) {
        employee.setClassification(getClassification());
        employee.setSchedule(getSchedule());
    }
}
