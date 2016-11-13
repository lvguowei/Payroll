package main.java.payroll.entities.paymentmethods;

import main.java.payroll.entities.Paycheck;

public class MailMethod implements PaymentMethod {

    private String itsAddress;

    public MailMethod(String address) {
        this.itsAddress = address;
    }

    public String getAddress() {
        return itsAddress;
    }

    @Override
    public void pay(Paycheck pc) {
        pc.setField("Disposition", "Mail");
    }
}
