package test.java.payroll;

import main.java.payroll.*;
import main.java.payroll.entities.*;
import main.java.payroll.entities.affiliations.Affiliation;
import main.java.payroll.entities.affiliations.NoAffiliation;
import main.java.payroll.entities.affiliations.UnionAffiliation;
import main.java.payroll.entities.paymentclassifications.CommissionedClassification;
import main.java.payroll.entities.paymentclassifications.HourlyClassification;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentclassifications.SalariedClassification;
import main.java.payroll.entities.paymentmethods.DirectMethod;
import main.java.payroll.entities.paymentmethods.HoldMethod;
import main.java.payroll.entities.paymentmethods.MailMethod;
import main.java.payroll.entities.paymentmethods.PaymentMethod;
import main.java.payroll.entities.paymentschedule.BiweeklySchedule;
import main.java.payroll.entities.paymentschedule.MonthlySchedule;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;
import main.java.payroll.entities.paymentschedule.WeeklySchedule;
import main.java.payroll.transactions.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.lang.System.err;

public class PayrollTest {

    @Test
    public void testAddSalariedEmployee() {
        int empid = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empid, "Bob", "Home", 1000.00);
        t.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empid);
        Assert.assertEquals("Bob", e.getName());

        PaymentClassification pc = e.getClassification();
        SalariedClassification sc = (SalariedClassification) pc;
        Assert.assertNotNull(sc);

        Assert.assertEquals(1000.00, sc.getSalary(), 0.001);
        PaymentSchedule ps = e.getSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        Assert.assertNotNull(ms);

        PaymentMethod pm = e.getMethod();
        HoldMethod hm = (HoldMethod)pm;
        Assert.assertNotNull(hm);
    }

    @Test
    public void testAddHourlyEmployee() {
        int empid = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empid, "Bob", "Home", 80.00);
        t.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empid);
        Assert.assertEquals("Bob", e.getName());

        PaymentClassification pc = e.getClassification();
        HourlyClassification hc = (HourlyClassification) pc;
        Assert.assertNotNull(hc);

        Assert.assertEquals(80.00, hc.getRate(), 0.001);

        PaymentSchedule ps = e.getSchedule();
        WeeklySchedule ws = (WeeklySchedule) ps;
        Assert.assertNotNull(ws);

        PaymentMethod pm = e.getMethod();
        HoldMethod hm = (HoldMethod) pm;
        Assert.assertNotNull(hm);
    }

    @Test
    public void testAddCommissionedEmployee() {
        int empid = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empid, "Bob", "Home", 1000.00, 40.00);
        t.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empid);
        Assert.assertEquals("Bob", e.getName());

        PaymentClassification pc = e.getClassification();
        CommissionedClassification cc = (CommissionedClassification) pc;
        Assert.assertNotNull(cc);

        Assert.assertEquals(40.00, cc.getCommissionRate(), 0.001);

        PaymentSchedule ps = e.getSchedule();
        BiweeklySchedule bs = (BiweeklySchedule) ps;
        Assert.assertNotNull(bs);

        PaymentMethod pm = e.getMethod();
        HoldMethod hm = (HoldMethod) pm;
        Assert.assertNotNull(hm);
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        err.println("Test delete employee");
        int empid = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empid, "Lance", "Home", 2000.00, 3.2);
        t.execute();
        {
            Employee e = PayrollDatabase.instance.getEmployee(empid);
            Assert.assertNotNull(e);
        }
        DeleteEmployeeTransaction dt = new DeleteEmployeeTransaction(empid);
        dt.execute();
        {
            Employee e = PayrollDatabase.instance.getEmployee(empid);
            Assert.assertNull(e);
        }
    }

    @Test
    public void testTimeCardTransaction() throws Exception {
        err.println("TestTimeCardTransaction");

        int empId = 2;

        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        Calendar date = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);

        TimeCardTransaction tct = new TimeCardTransaction(empId, date, 8.0);
        tct.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);

        Assert.assertNotNull(e);
        PaymentClassification pc = e.getClassification();
        HourlyClassification hc = (HourlyClassification) pc;

        Assert.assertNotNull(hc);
        TimeCard tc = hc.getTimeCard(date);
        Assert.assertEquals(8.0, tc.getHours(), 0.001);
    }

    @Test

    public void testBadTimeCardTransaction() throws Exception {
        err.println("TestBadTimeCardTransaction");

        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2500, 3.2);
        t.execute();

        TimeCardTransaction tct = new TimeCardTransaction(empId, new GregorianCalendar(2001, Calendar.OCTOBER, 31), 8.0);

        try {
            tct.execute();
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }

    }

    @Test
    public void testSalesReceiptTransaction() throws Exception {
        err.println("TestSalesReceiptTransaction");

        int empId = 3;

        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2000.00, 3.5);
        t.execute();

        SalesReceiptTransaction srt = new SalesReceiptTransaction(new GregorianCalendar(2001, Calendar.OCTOBER, 2), 200.00, empId);
        srt.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);

        Assert.assertNotNull(e);
        PaymentClassification pc = e.getClassification();
        CommissionedClassification cc = (CommissionedClassification) pc;

        Assert.assertNotNull(cc);
        SalesReceipt sr = cc.getSalesReceipt(new GregorianCalendar(2001, Calendar.OCTOBER, 2));
        Assert.assertNotNull(sr);
        Assert.assertEquals(200.00, sr.getAmount(), 0.001);
    }

    @Test
    public void testBadSalesReceiptTransaction() throws Exception {
        err.println("TestBadSalesReceiptTransaction");

        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        Calendar saleDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 12);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(saleDate, 25000, empId);

        try {
            srt.execute();
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void testAddServiceCharge() throws Exception {
        err.println("TestAddServiceCharge");

        int empId = 2;

        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);

        Assert.assertNotNull(e);

        UnionAffiliation ua = new UnionAffiliation(9987, 12.22);

        e.setAffiliation(ua);

        int memberId = 89;
        PayrollDatabase.instance.addUnionMember(memberId, e);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, new GregorianCalendar(2001, Calendar.NOVEMBER, 1), 12.95);
        sct.execute();

        ServiceCharge sc = ua.getServiceCharge(new GregorianCalendar(2001, Calendar.NOVEMBER, 1));
        Assert.assertNotNull(sc);
        Assert.assertEquals(12.95, sc.getAmount(), 0.001);
    }

    @Test
    public void testChangeNameTransaction() throws Exception {
        err.println("TestChangeNameTransaction");
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();
        ChangeNameTransaction cnt = new ChangeNameTransaction(empId, "Bib");
        cnt.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);
        Assert.assertEquals("Bib", e.getName());
    }

    @Test
    public void testChangeAddressTransaction() throws Exception {
        err.println("TestChangeAddressTransaction");
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();
        ChangeAddressTransaction cat = new ChangeAddressTransaction(empId, "Work");
        cat.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);
        Assert.assertEquals("Work", e.getAddress());
    }

    @Test
    public void testChangeHourlyTransaction() throws Exception {
        err.println("TestChangeHourlyTransaction");
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2000.00, 3.2);
        t.execute();

        ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empId, 27.52);
        cht.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentClassification pc = e.getClassification();
        Assert.assertNotNull(pc);
        HourlyClassification hc = (HourlyClassification) pc;
        Assert.assertNotNull(hc);

        Assert.assertEquals(27.52, hc.getRate(), 0.001);
        PaymentSchedule ps = e.getSchedule();
        WeeklySchedule ws = (WeeklySchedule) ps;
        Assert.assertNotNull(ws);
    }

    @Test
    public void testChangeSalariedTransaction() throws Exception {
        err.println("TestChangeSalariedTransaction");
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2000.00, 3.2);
        t.execute();

        ChangeSalariedTransaction cst = new ChangeSalariedTransaction(empId, 2000.00);
        cst.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentClassification pc = e.getClassification();
        Assert.assertNotNull(pc);
        SalariedClassification sc = (SalariedClassification) pc;
        Assert.assertNotNull(sc);

        Assert.assertEquals(2000.00, sc.getSalary(), 0.001);
        PaymentSchedule ps = e.getSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        Assert.assertNotNull(ms);
    }

    @Test
    public void testChangeCommissionedTransaction() throws Exception {
        err.println("TestChangeCommissionedTransaction");
        int empId = 3;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Lance", "Home", 2000.00);
        t.execute();

        ChangeCommissionedTransaction cct = new ChangeCommissionedTransaction(empId, 2000.00, 3.5);
        cct.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentClassification pc = e.getClassification();
        Assert.assertNotNull(pc);
        CommissionedClassification cc = (CommissionedClassification) pc;
        Assert.assertNotNull(cc);

        Assert.assertEquals(2000.00, cc.getSalary(), 0.001);
        Assert.assertEquals(3.5, cc.getCommissionRate(), 0.001);
        PaymentSchedule ps = e.getSchedule();
        BiweeklySchedule bs = (BiweeklySchedule) ps;
        Assert.assertNotNull(bs);
    }

    @Test
    public void testChangeDirectTransaction() throws Exception {
        err.println("TestChangeDirectTransaction");
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "JANE", "Home", 900);
        t.execute();

        ChangeDirectTransaction cdt = new ChangeDirectTransaction(empId, "bank", "999");
        cdt.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentMethod pm = e.getMethod();
        DirectMethod dm = (DirectMethod) pm;

        Assert.assertNotNull(dm);
        Assert.assertEquals("bank", dm.getBank());
        Assert.assertEquals("999", dm.getAccount());
    }

    @Test
    public void testChangeMailTransaction() throws Exception {
        err.println("TestChangeMailTransaction");
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "JANE", "Home", 900);
        t.execute();

        ChangeMailTransaction cmt = new ChangeMailTransaction(empId, "address");
        cmt.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentMethod pm = e.getMethod();
        MailMethod mm = (MailMethod) pm;

        Assert.assertNotNull(mm);
        Assert.assertEquals("address", mm.getAddress());
    }

    @Test
    public void testChangeHoldTransaction() throws Exception {
        err.println("TestChangeHoldTransaction");
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "JANE", "Home", 900);
        t.execute();

        ChangeHoldTransaction cht = new ChangeHoldTransaction(empId);
        cht.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentMethod pm = e.getMethod();
        HoldMethod hm = (HoldMethod) pm;

        Assert.assertNotNull(hm);
    }

    @Test
    public void testChangeMemberTransaction() throws Exception {
        err.println("TestChangeMemberTransaction");
        int empId = 2;
        int memberId = 4433;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 99.42);
        cmt.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);
        Affiliation af = e.getAffiliation();
        Assert.assertNotNull(af);
        UnionAffiliation uf = (UnionAffiliation) af;
        Assert.assertNotNull(uf);
        Assert.assertEquals(99.42, uf.getDues(), 0.001);
        Employee member = PayrollDatabase.instance.getUnionMember(memberId);
        Assert.assertNotNull(member);
        Assert.assertTrue(e == member);
    }

    @Test
    public void testChangeUnaffiliatedTransaction() throws Exception {
        err.println("TestChangeUnaffiliatedTransaction");
        int empId = 2;
        int memberId = 4433;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 200.00);
        cmt.execute();

        ChangeUnaffiliatedTransaction cut = new ChangeUnaffiliatedTransaction(empId);
        cut.execute();

        Employee e = PayrollDatabase.instance.getEmployee(empId);
        Assert.assertNotNull(e);
        Affiliation af = e.getAffiliation();
        NoAffiliation na = (NoAffiliation) af;
        Assert.assertNotNull(na);

        Assert.assertNull(PayrollDatabase.instance.getUnionMember(memberId));
    }

    @Test
    public void testPaySingleSalariedEmployee() throws Exception {
        err.println("TestPaySingleSalariedEmployee");

        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();

        Calendar payDay = new GregorianCalendar(2001, Calendar.NOVEMBER, 30);
        PaydayTransaction pt = new PaydayTransaction(payDay);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(pc.getPayPeriodEndDate(), payDay);
        Assert.assertEquals(1000.00, pc.getGrossPay(), 0.001);
        Assert.assertTrue("Hold".equals(pc.getField("Disposition")));
        Assert.assertEquals(0.0, pc.getDeductions(), 0.001);
        Assert.assertEquals(1000.00, pc.getNetPay(), 0.001);
    }

    @Test
    public void testPaySingleSalariedEmployeeOnWrongDate() throws Exception {
        err.println("TestPaySingleSalariedEmployeeOnWrongDate");
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 2000.00);
        t.execute();
        Calendar date = new GregorianCalendar(2001, Calendar.NOVEMBER, 29);
        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPayMultipleSalariedEmployee() throws Exception {
        err.println("TestPayMultipleSalariedEmployees");

        AddSalariedEmployee t1 = new AddSalariedEmployee(1, "Bob", "Home", 1000.00);
        AddSalariedEmployee t2 = new AddSalariedEmployee(2, "Bill", "Home", 2000.00);
        AddSalariedEmployee t3 = new AddSalariedEmployee(3, "Joan", "Home", 9000.00);
        t1.execute();
        t2.execute();
        t3.execute();

        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 30);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Assert.assertEquals(3, pt.getPayCheckCount());
        validatePayCheck(pt, 1, payDate, 1000.00);
        validatePayCheck(pt, 2, payDate, 2000.00);
        validatePayCheck(pt, 3, payDate, 9000.00);

    }

    private void validatePayCheck(PaydayTransaction pt, int empId, Calendar payDate, double pay) {
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);

        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(pay, pc.getGrossPay(), 0.001);
        Assert.assertEquals(0.0, pc.getDeductions(), 0.001);
        Assert.assertEquals(pay, pc.getNetPay(), 0.001);
    }

    @Test
    public void testPaySingleHourlyEmployeeNoTimeCards() throws Exception {
        err.println("TestPaySingleHourlyEmployeeNoTimeCards");
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 20.00);
        t.execute();

        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9); // Friday
        Assert.assertEquals(Calendar.FRIDAY, payDate.get(Calendar.DAY_OF_WEEK));
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc.getPayPeriodEndDate().equals(payDate));
        Assert.assertEquals(0.0, pc.getGrossPay(), 0.001);
        Assert.assertTrue("Hold".equals(pc.getField("Disposition")));
        Assert.assertEquals(0.0 - 0.0, pc.getDeductions(), 0.001);
        Assert.assertEquals(0.0, pc.getNetPay(), 0.001);
    }

    @Test
    public void testPaySingleHourlyEmployeeOneTimeCard() throws Exception {
        err.println("TestPaySingleHourlyEmployeeOneTimeCard");
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);

        TimeCardTransaction tc = new TimeCardTransaction(empId, payDate, 2.0);
        tc.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc.getPayPeriodEndDate().equals(payDate));
        Assert.assertEquals(30.5, pc.getGrossPay(), 0.001);
        Assert.assertTrue("Hold".equals(pc.getField("Disposition")));
        Assert.assertEquals(30.5 - 30.5, pc.getDeductions(), 0.001);
        Assert.assertEquals(30.5, pc.getNetPay(), 0.001);
    }

    @Test
    public void testPaySingleHourlyEmployeeOvertimeOneTimeCard() throws Exception {
        err.println("TestPaySingleHourlyEmployeeOvertimeOneTimeCard");

        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        Calendar payday = new GregorianCalendar(2001, Calendar.NOVEMBER, 9); // Friday
        TimeCardTransaction tc = new TimeCardTransaction(empId, payday, 9.0);
        tc.execute();

        PaydayTransaction pt = new PaydayTransaction(payday);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc.getPayPeriodEndDate().equals(payday));
        Assert.assertEquals((8 + 1.5) * 15.25, pc.getGrossPay(), 0.001);
        Assert.assertTrue("Hold".equals(pc.getField("Disposition")));
        Assert.assertEquals((8 + 1.5) * 15.25 - (8 + 1.5) * 15.25, pc.getDeductions(), 0.001);
        Assert.assertEquals((8 + 1.5) * 15.25, pc.getNetPay(), 0.001);

    }

    @Test
    public void TestPaySingleHourlyEmployeeOnWrongDate() throws Exception {
        err.println("TestPaySingleHourlyEmployeeOnWrongDate");

        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Hobb", "Home", 15.25);
        t.execute();

        Calendar payDay = new GregorianCalendar(2001, Calendar.NOVEMBER, 8); // Thursday

        TimeCardTransaction tc = new TimeCardTransaction(empId, payDay, 9.0);
        tc.execute();

        PaydayTransaction pt = new PaydayTransaction(payDay);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);

    }

    @Test
    public void testPaySingleHourlyEmployeeTwoTimeCards() throws Exception {
        err.println("TestPaySingleHourlyEmployeeTwoTimeCards");

        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Hou", "Home", 15.25);
        t.execute();

        Calendar payday = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);

        TimeCardTransaction tc = new TimeCardTransaction(empId, payday, 2.0);
        tc.execute();
        TimeCardTransaction tc2 = new TimeCardTransaction(empId, new GregorianCalendar(2001, Calendar.NOVEMBER, 8), 5.0);
        tc2.execute();

        PaydayTransaction pt = new PaydayTransaction(payday);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc.getPayPeriodEndDate().equals(payday));
        Assert.assertEquals(7 * 15.25, pc.getGrossPay(), 0.001);
        Assert.assertTrue("Hold".equals(pc.getField("Disposition")));
        Assert.assertEquals(7 * 15.25 - 7 * 15.25, pc.getDeductions(), 0.001);
        Assert.assertEquals(7 * 15.25, pc.getNetPay(), 0.001);

    }

    @Test
    public void testPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods() throws Exception {
        err.println("TestPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods");

        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        Calendar payday = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        Calendar dateInPrevPayPeriod = new GregorianCalendar(2001, Calendar.NOVEMBER, 2);

        TimeCardTransaction tc = new TimeCardTransaction(empId, payday, 2.0);
        tc.execute();
        TimeCardTransaction tc2 = new TimeCardTransaction(empId, dateInPrevPayPeriod, 2.0);
        tc2.execute();

        PaydayTransaction pt = new PaydayTransaction(payday);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc.getPayPeriodEndDate().equals(payday));
        Assert.assertEquals(2 * 15.25, pc.getGrossPay(), 0.001);
        Assert.assertTrue("Hold".equals(pc.getField("Disposition")));
        Assert.assertEquals(2 * 15.25 - 2 * 15.25, pc.getDeductions(), 0.001);
        Assert.assertEquals(2 * 15.25, pc.getNetPay(), 0.001);
    }

    @Test
    public void testPaySingleCommissionedEmployeeNoSalesReceipts() throws Exception {
        err.println("TestPaySingleCommissionedEmployeeNoSalesReceipts");

        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2500, 3.2);
        t.execute();

        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePayCheck(pt, empId, payDate, 2500.00);
    }

    @Test
    public void testPaySingleCommissionedEmployeeOneSalesReceipt() throws Exception {
        err.println("TestPaySingleCommissionedEmployeeOneSalesReceipt");

        int empId = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2500, 0.032);
        t.execute();

        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9); // Friday
        SalesReceiptTransaction srt = new SalesReceiptTransaction(payDate, 13000.00, empId);
        srt.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePayCheck(pt, empId, payDate, 2500 + 0.032 * 13000);
    }

    @Test
    public void testPaySingleCommissionedEmployeeTwoSalesReceipts() throws Exception {
        err.println("TestPaySingleCommissionedEmployeeTwoSalesReceipts");

        int empId = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2500, 0.032);
        t.execute();

        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9); // Friday
        SalesReceiptTransaction srt = new SalesReceiptTransaction(payDate, 13000.00, empId);
        srt.execute();

        Calendar date = new GregorianCalendar(2001, Calendar.NOVEMBER, 8);
        SalesReceiptTransaction srt2 = new SalesReceiptTransaction(date, 24000.00, empId);
        srt2.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePayCheck(pt, empId, payDate, 2500 + (0.032 * (13000 + 24000)));
    }

    @Test
    public void testPaySingleCommissionedEmployeeWrongDate() throws Exception {
        err.println("TestPaySingleCommissionedEmployeeWrongDate");

        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 25000, 0.032);
        t.execute();

        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 16);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(payDate, 13000.00, empId);
        srt.execute();

        SalesReceiptTransaction srt2 = new SalesReceiptTransaction(new GregorianCalendar(2001, Calendar.NOVEMBER, 15), 24000.00, empId);
        srt2.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPaySingleCommissionedEmployeeSpanMultiplePayPeriods() throws Exception {
        err.println("TestPaySingleCommissionedEmployeeSpanMultiplePayPeriods");

        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2500, 0.032);
        t.execute();

        // Previous pay period
        Calendar earlyDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);

        // Biweekly Friday
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 23);

        // Next pay period
        Calendar lateDate = new GregorianCalendar(2001, Calendar.DECEMBER, 7);

        SalesReceiptTransaction srt = new SalesReceiptTransaction(payDate, 13000.00, empId);
        srt.execute();

        SalesReceiptTransaction srt1 = new SalesReceiptTransaction(earlyDate, 24000.00, empId);
        srt1.execute();

        SalesReceiptTransaction srt2 = new SalesReceiptTransaction(lateDate, 15000.00, empId);
        srt2.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePayCheck(pt, empId, payDate, 2500.00 + 0.032 * 13000);

    }

    @Test
    public void testSalariedUnionMemberDues() throws Exception {
        err.println("TestSalariedUnionMemberDues");

        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();

        int memberId = 7788;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 30);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(1000.00, pc.getGrossPay(), 0.001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));

        int fridays = 5; // Fridays in Nov, 2001
        Assert.assertEquals(fridays * 9.42, pc.getDeductions(), 0.001);
        Assert.assertEquals(1000.00 - (fridays * 9.42), pc.getNetPay(), 0.001);
    }

    @Test
    public void testHourlyUnionMemberDues() throws Exception {
        err.println("TestHourlyUnionMemberDues");

        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 15.24);
        t.execute();

        int memberId = 3344;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        TimeCardTransaction tct = new TimeCardTransaction(empId, payDate, 8.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);

        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(8 * 15.24, pc.getGrossPay(), 0.001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(9.42, pc.getDeductions(), 0.001);
        Assert.assertEquals(8 * 15.24 - 9.42, pc.getNetPay(), 0.001);
    }

    @Test
    public void testCommissionedUnionMemberDues() throws Exception {
        err.println("TestCommissionedUnionMemberDues");
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2500, 0.032);
        t.execute();

        int memberId = 3344;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(2500.00, pc.getGrossPay(), 0.001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(2 * 9.42, pc.getDeductions(), 0.001);
        Assert.assertEquals(2500.00 - (2 * 9.42), pc.getNetPay(), 0.001);
    }

    @Test
    public void testHourlyUnionMemberServiceCharge() throws Exception {
        err.println("TestHourlyUnionMemberServiceCharge");

        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 15.24);
        t.execute();

        int memberId = 9989;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        Calendar payDay = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId,payDay, 19.42);
        sct.execute();

        TimeCardTransaction tct = new TimeCardTransaction(empId, payDay, 8.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDay);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(pc.getPayPeriodEndDate(), payDay);
        Assert.assertEquals(8 * 15.24, pc.getGrossPay(), 0.001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(9.42 + 19.42, pc.getDeductions(), 0.001);
    }

    @Test
    public void testServiceChargesSpanningMultiplePayPeriods() throws Exception {
        err.println("TestServiceChargesSpanningMultiplePayPeriods");

        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 15.24);
        t.execute();

        int memberId = 9987;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        Calendar earlyDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 2); // prev Friday
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        Calendar lateDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 16); // next Friday

        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();

        ServiceChargeTransaction sctEarly = new ServiceChargeTransaction(memberId, earlyDate, 100.00);
        sctEarly.execute();

        ServiceChargeTransaction sctLate = new ServiceChargeTransaction(memberId, lateDate, 200.00);
        sctLate.execute();

        TimeCardTransaction tct = new TimeCardTransaction(empId, payDate, 8.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(pc.getPayPeriodEndDate(), payDate);

        Assert.assertEquals(8 * 15.24, pc.getGrossPay(), 0.001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(9.42 + 19.42, pc.getDeductions(), 0.001);
        Assert.assertEquals(8 * 15.24 - 9.42 - 19.42, pc.getNetPay(), 0.001);
    }
}
