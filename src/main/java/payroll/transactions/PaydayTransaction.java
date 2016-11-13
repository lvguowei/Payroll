package main.java.payroll.transactions;

import main.java.payroll.PayrollDatabase;
import main.java.payroll.entities.Employee;
import main.java.payroll.entities.Paycheck;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PaydayTransaction implements Transaction {

    private Calendar itsPayDate;
    private Map<Integer, Paycheck> itsPaychecks;

    public PaydayTransaction(Calendar payDate) {
        this.itsPayDate = payDate;
        itsPaychecks = new HashMap<>();
    }

    public Paycheck getPaycheck(int empId) {
        return itsPaychecks.get(empId);
    }

    public int getPayCheckCount() {
        return itsPaychecks.size();
    }

    @Override
    public void execute() {
        Set<Integer> empIds = PayrollDatabase.instance.getAllEmployeeIds();
        for (int id : empIds) {
            Employee e = PayrollDatabase.instance.getEmployee(id);
            if (e.isPayDate(itsPayDate)) {
                Paycheck pc = new Paycheck(e.getPayPeriodStartDay(itsPayDate), itsPayDate);
                itsPaychecks.put(id, pc);
                e.payday(pc);
            }
        }
    }
}
