package main.java.payroll.transactions;

import main.java.payroll.entities.affiliations.Affiliation;
import main.java.payroll.entities.Employee;

abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {

    ChangeAffiliationTransaction(int itsEmpId) {
        super(itsEmpId);
    }

    public abstract Affiliation getAffiliation();
    public abstract void recordMembership(Employee employee);

    @Override
    public void change(Employee employee) {
        recordMembership(employee);
        employee.setAffiliation(getAffiliation());
    }
}
