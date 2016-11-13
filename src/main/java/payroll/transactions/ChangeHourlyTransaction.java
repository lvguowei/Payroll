package main.java.payroll.transactions;

import main.java.payroll.entities.paymentclassifications.HourlyClassification;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;
import main.java.payroll.entities.paymentschedule.WeeklySchedule;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {

    private double itsHourlyRate;

    public ChangeHourlyTransaction(int empId, double rate) {
        super(empId);
        itsHourlyRate = rate;
    }

    @Override
    public PaymentClassification getClassification() {
        return new HourlyClassification(itsHourlyRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
