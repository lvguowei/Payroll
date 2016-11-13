package main.java.payroll.transactions;

import main.java.payroll.entities.paymentmethods.HoldMethod;
import main.java.payroll.entities.paymentmethods.PaymentMethod;

public class ChangeHoldTransaction extends ChangeMethodTransaction {

    public ChangeHoldTransaction(int itsEmpId) {
        super(itsEmpId);
    }

    @Override
    public PaymentMethod getMethod() {
        return new HoldMethod();
    }
}
