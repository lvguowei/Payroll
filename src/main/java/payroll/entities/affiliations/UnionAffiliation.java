package main.java.payroll.entities.affiliations;

import main.java.payroll.entities.Paycheck;
import main.java.payroll.entities.ServiceCharge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static main.java.payroll.entities.paymentclassifications.PaymentClassification.isInPayPeriod;

public class UnionAffiliation implements Affiliation {

    private double itsDues;
    private int itsMemberId;

    private List<ServiceCharge> itsServiceCharges;

    public UnionAffiliation(int memberId, double dues) {
        itsServiceCharges = new ArrayList<>();
        this.itsDues = dues;
        this.itsMemberId = memberId;
    }

    public void addServiceCharge(Calendar itsDate, double itsCharge) {
        itsServiceCharges.add(new ServiceCharge(itsCharge, itsDate));
    }

    public ServiceCharge getServiceCharge(Calendar date) {
        for (ServiceCharge sc : itsServiceCharges) {
            if (sc.getDate().equals(date)) {
                return sc;
            }
        }
        return null;
    }

    public double getDues() {
        return itsDues;
    }

    public int getMemberId() {
        return itsMemberId;
    }

    @Override
    public double calculateDeductions(Paycheck payCheck) {
        double result = 0;
        result += numberOfFridaysInPayPeriod(payCheck.getPayPeriodStart(), payCheck.getPayPeriodEndDate()) * itsDues;
        for (ServiceCharge serviceCharge : itsServiceCharges){
            if (isInPayPeriod(serviceCharge.getDate(), payCheck)){
                result += serviceCharge.getAmount();
            }
        }
        return result;
    }

    private int numberOfFridaysInPayPeriod(Calendar payPeriodStart, Calendar payPeriodEnd) {
        int numberOfFridays = 0;
        while (!payPeriodStart.after(payPeriodEnd)){
            if (payPeriodStart.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
                numberOfFridays++;
            }
            payPeriodStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        return numberOfFridays;
    }
}
