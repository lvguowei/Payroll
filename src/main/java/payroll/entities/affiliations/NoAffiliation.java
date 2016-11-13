package main.java.payroll.entities.affiliations;

import main.java.payroll.entities.Paycheck;

public class NoAffiliation implements Affiliation {
    @Override
    public double calculateDeductions(Paycheck pc) {
        return 0;
    }
}
