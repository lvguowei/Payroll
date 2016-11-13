package main.java.payroll.entities.affiliations;

import main.java.payroll.entities.Paycheck;

public interface Affiliation {
    double calculateDeductions(Paycheck pc);
}
