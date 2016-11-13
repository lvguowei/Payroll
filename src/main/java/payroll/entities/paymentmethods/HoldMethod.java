package main.java.payroll.entities.paymentmethods;

import main.java.payroll.entities.Paycheck;

public class HoldMethod implements PaymentMethod {

    @Override
    public void pay(Paycheck pc) {
        pc.setField("Disposition", "Hold");
    }
}
