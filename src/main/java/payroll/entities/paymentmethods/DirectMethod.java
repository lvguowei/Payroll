package main.java.payroll.entities.paymentmethods;

import main.java.payroll.entities.Paycheck;

public class DirectMethod implements PaymentMethod {

    private String itsBank;
    private String itsAccount;

    public DirectMethod(String bank, String account) {
        this.itsBank = bank;
        this.itsAccount = account;
    }

    public String getBank() {
        return itsBank;
    }

    public String getAccount() {
        return itsAccount;
    }

    @Override
    public void pay(Paycheck pc) {
        pc.setField("Disposition", "Direct");
    }
}
