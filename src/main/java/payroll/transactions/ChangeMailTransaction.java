package main.java.payroll.transactions;

import main.java.payroll.entities.paymentmethods.MailMethod;
import main.java.payroll.entities.paymentmethods.PaymentMethod;

public class ChangeMailTransaction extends ChangeMethodTransaction {

    private String itsAddress;

    public ChangeMailTransaction(int empId, String address) {
        super(empId);
        this.itsAddress = address;
    }

    @Override
    public PaymentMethod getMethod() {
        return new MailMethod(itsAddress);
    }
}
