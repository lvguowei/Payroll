package main.java.payroll.transactions;

import main.java.payroll.*;
import main.java.payroll.entities.Employee;
import main.java.payroll.entities.affiliations.Affiliation;
import main.java.payroll.entities.affiliations.UnionAffiliation;

import java.util.Calendar;

public class ServiceChargeTransaction implements Transaction {

    private int itsMemberId;
    private Calendar itsDate;
    private double itsCharge;

    public ServiceChargeTransaction(int memberId, Calendar date, double charge) {
        this.itsMemberId = memberId;
        this.itsDate = date;
        this.itsCharge = charge;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.instance.getUnionMember(itsMemberId);
        Affiliation af = e.getAffiliation();
        if (af instanceof UnionAffiliation) {
            UnionAffiliation ua = (UnionAffiliation) af;
            ua.addServiceCharge(itsDate, itsCharge);
        }
    }

}
