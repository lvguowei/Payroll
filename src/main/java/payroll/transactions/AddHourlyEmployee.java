package main.java.payroll.transactions;

import main.java.payroll.entities.paymentclassifications.HourlyClassification;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;
import main.java.payroll.entities.paymentschedule.WeeklySchedule;

public class AddHourlyEmployee extends AddEmployeeTransaction {

    private double hourlyRate;

    public AddHourlyEmployee(int empid, String name, String address, double hourlyRate) {
        super(empid, name, address);
        this.hourlyRate = hourlyRate;
    }

    @Override
    PaymentClassification getClassification() {
        return new HourlyClassification(hourlyRate);
    }

    @Override
    PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
