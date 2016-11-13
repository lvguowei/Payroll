package main.java.payroll.entities.paymentclassifications;

import main.java.payroll.entities.Paycheck;

import java.util.Calendar;

public abstract class PaymentClassification {

    public abstract double calculatePay(Paycheck pc);

    public static boolean isInPayPeriod(Calendar date, Paycheck payCheck) {
        Calendar payPeriodStart = payCheck.getPayPeriodStart();
        Calendar payPeriodEnd = payCheck.getPayPeriodEndDate();
        return date.equals(payPeriodEnd) || date.equals(payPeriodStart) ||
                (date.after(payPeriodStart) && date.before(payPeriodEnd));
    }
}
