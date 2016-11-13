package main.java.payroll.entities.paymentschedule;

import java.util.Calendar;

public class MonthlySchedule implements PaymentSchedule {

    @Override
    public boolean isPayday(Calendar date) {
        return isLastDayOfMonth(date);
    }

    @Override
    public Calendar getPayPeriodStartDate(Calendar payDate) {
        Calendar firstOfMonth = (Calendar) payDate.clone();
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        return firstOfMonth;
    }

    private boolean isLastDayOfMonth(Calendar date) {
        return getNextDay(date).get(Calendar.MONTH) != date.get(Calendar.MONTH);
    }

    private Calendar getNextDay(Calendar date) {
        Calendar nextDay = (Calendar) date.clone();
        nextDay.add(Calendar.DAY_OF_MONTH, 1);
        return nextDay;
    }
}
