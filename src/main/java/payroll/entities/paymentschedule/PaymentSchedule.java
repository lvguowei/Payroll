package main.java.payroll.entities.paymentschedule;

import java.util.Calendar;

public interface PaymentSchedule {

    boolean isPayday(Calendar date);
    Calendar getPayPeriodStartDate(Calendar payPeriodEndDate);
}
