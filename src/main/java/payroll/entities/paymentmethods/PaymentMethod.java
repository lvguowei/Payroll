package main.java.payroll.entities.paymentmethods;

import main.java.payroll.entities.Paycheck;

public interface PaymentMethod {
    void pay(Paycheck pc);
}
