package main.java.payroll.transactions;

import main.java.payroll.entities.affiliations.Affiliation;
import main.java.payroll.entities.Employee;
import main.java.payroll.PayrollDatabase;
import main.java.payroll.entities.affiliations.UnionAffiliation;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction {

    private int itsMemberId;
    private double itsDues;

    public ChangeMemberTransaction(int empId, int memberId, double dues) {
        super(empId);
        this.itsMemberId = memberId;
        this.itsDues = dues;
    }

    @Override
    public Affiliation getAffiliation() {
        return new UnionAffiliation(itsMemberId, itsDues);
    }

    @Override
    public void recordMembership(Employee employee) {
        PayrollDatabase.instance.addUnionMember(itsMemberId, employee);
    }
}
