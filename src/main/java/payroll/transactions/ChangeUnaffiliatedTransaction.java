package main.java.payroll.transactions;

import main.java.payroll.*;
import main.java.payroll.entities.Employee;
import main.java.payroll.entities.affiliations.Affiliation;
import main.java.payroll.entities.affiliations.NoAffiliation;
import main.java.payroll.entities.affiliations.UnionAffiliation;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {


    public ChangeUnaffiliatedTransaction(int empId) {
        super(empId);
    }

    @Override
    public Affiliation getAffiliation() {
        return new NoAffiliation();
    }

    @Override
    public void recordMembership(Employee employee) {
        Affiliation af = employee.getAffiliation();
        if (af instanceof UnionAffiliation) {
            UnionAffiliation ua = (UnionAffiliation) af;
            int memberId = ua.getMemberId();
            PayrollDatabase.instance.removeUnionMember(memberId);
        }
    }
}
