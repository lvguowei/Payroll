package main.java.payroll.entities.paymentclassifications;

import main.java.payroll.entities.Paycheck;
import main.java.payroll.entities.TimeCard;

import java.util.*;

public class HourlyClassification extends PaymentClassification {

    private Map<Calendar, TimeCard> itsTimeCards;

    private double itsRate;

    public HourlyClassification(double hourlyRate) {
        this.itsRate = hourlyRate;
        itsTimeCards = new HashMap<>();
    }

    public double getRate() {
        return itsRate;
    }

    public void addTimeCard(TimeCard timeCard) {
        itsTimeCards.put(timeCard.getDate(), timeCard);
    }

    public TimeCard getTimeCard(Calendar date) {
        return itsTimeCards.get(date);
    }

    @Override
    public double calculatePay(Paycheck pc) {
        double totalPay = 0;
        for(TimeCard timeCard: itsTimeCards.values()){
            if(isInPayPeriod(timeCard.getDate(), pc)){
                totalPay += calculatePayForTimeCard(timeCard);
            }
        }
        return totalPay;
    }

    private double calculatePayForTimeCard(TimeCard timeCard) {
        double hours = timeCard.getHours();
        double overtime = Math.max(0.0, hours-8.0);
        double straightTime = hours - overtime;
        return straightTime * itsRate + overtime * itsRate * 1.5;
    }
}
