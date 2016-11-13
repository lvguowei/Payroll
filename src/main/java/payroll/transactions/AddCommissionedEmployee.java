package main.java.payroll.transactions;

import main.java.payroll.entities.paymentclassifications.CommissionedClassification;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentschedule.BiweeklySchedule;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;

public class AddCommissionedEmployee extends AddEmployeeTransaction {

    private double salary;
    private double commissionRate;

    public AddCommissionedEmployee(int empid, String name, String address, double salary, double commissionRate) {
        super(empid, name, address);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    PaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }
}
