/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.service.leavedp;

import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.LLStockManagement;
import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.attendance.PstLLStockManagement;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.leave.LeaveAlClosing;
import com.dimata.harisma.entity.leave.LeaveAlClosingList;
import com.dimata.harisma.entity.leave.LeaveAlClosingNoStockList;
import com.dimata.harisma.entity.leave.LeaveLlClosingList;
import com.dimata.harisma.entity.leave.LlClosingSelected;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.session.leave.LeaveConfigHR;
import com.dimata.harisma.session.leave.SessLeaveClosing;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Formater;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Gunadi
 */
public class AutomaticAlClosing implements Runnable {
    
    int i = 0;
    //update by satrya 2013-02-25
    private Date startDate=null;
     private boolean running = false;
     private long sleepMs = 86400000;
    public AutomaticAlClosing() {
    }
    
    public void run() 
    {
        try {

            this.setRunning(true);

            I_Leave leaveConfig = null;

            try {
                leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }

            Vector vectValExtend = leaveConfig != null ? leaveConfig.getAlValExtend() : new Vector();
            Vector vectListExtend = leaveConfig != null ? leaveConfig.getAlKeyExtend() : new Vector();

            int i = 0;
            while (this.running) {
                i++;
                
                alClosing(leaveConfig);
                
                try {
                    Thread.sleep(this.getSleepMs());
                } catch (Exception exc) {
                    System.out.println(exc);
                } finally {
                }
            }
            this.running = false;


        } catch (Exception exc) {
            System.out.println(">>> Exception on AutomaticAlClosing service :((");
        }
    }
    
    public void alClosing(I_Leave leaveConfig) {
        Date today = new Date();
        Vector resultSearch = new Vector();
        Vector result = new Vector();
        Vector resultClose = new Vector();
        
        Vector listEmployee = SessLeaveClosing.listEmpEligibleLeave();
        if (listEmployee.size()>0){
            for (int i = 0; i < listEmployee.size(); i++){
                try {
                    Employee emp = (Employee) listEmployee.get(i);
                    Date commDate = emp.getCommencingDate();
                    Calendar calNext = Calendar.getInstance();
                    Calendar calExp = Calendar.getInstance();
                    Calendar calToday = Calendar.getInstance();
                    
                    
                    /* cek yg recontract dulu */
                    Vector listAlReCon = PstAlStockManagement.list(0, 0, PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+"="+emp.getOID()
                                +" AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"=0 AND "
                                + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] +" <= '"+Formater.formatDate(commDate, "yyyy-MM-dd")+"'", "");
                    for (int a = 0; a < listAlReCon.size(); a++){
                        AlStockManagement alStockManagement = (AlStockManagement) listAlReCon.get(a);
                        try {
                            alStockManagement.setAlStatus(1);
                            PstAlStockManagement.updateExc(alStockManagement);
                        } catch (Exception exc){}
                    }
                    
                    Vector listLLReCon = PstLLStockManagement.list(0, 0, PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+"="+emp.getOID()
                            +" AND "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+"=0 AND "
                            + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED_DATE] +" <= '"+Formater.formatDate(commDate, "yyyy-MM-dd")+"'", "");
                    for (int ll = 0; ll < listLLReCon.size(); ll++){
                        LLStockManagement lLStockManagement = (LLStockManagement) listLLReCon.get(ll);
                        try {
                            lLStockManagement.setLLStatus(1);
                            PstLLStockManagement.updateExc(lLStockManagement);
                        } catch (Exception exc){}
                    }
                    
                    
                    calToday.setTime(new Date());
                    Vector listLL = PstLLStockManagement.list(0, 0, PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+"="+emp.getOID()
                            +" AND "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+"=0", "");
                    if (listLL.size()>0 && emp.getCompanyId() == 504404575327187914L){
                        LLStockManagement lLStockManagement = (LLStockManagement) listLL.get(0);
                        Calendar calCommencing = Calendar.getInstance();
                        calCommencing.setTime(lLStockManagement.getEntitledDate());
                        int diffYear = calToday.get(Calendar.YEAR) - calCommencing.get(Calendar.YEAR);
                        int diffMonth = diffYear * 12 + calToday.get(Calendar.MONTH) - calCommencing.get(Calendar.MONTH);

                        if (calToday.get(Calendar.DAY_OF_MONTH) > calCommencing.get(Calendar.DAY_OF_MONTH)){
                                diffMonth += 1;
                        }

                        if (diffMonth >= 12){
                            if (lLStockManagement.getEntitle2() == 0){
                                calNext.setTime(lLStockManagement.getEntitledDate());
                                calNext.add(Calendar.MONTH, 12);
                                lLStockManagement.setEntitle2(22);
                                lLStockManagement.setEntitleDate2(calNext.getTime());
                                calNext.add(Calendar.MONTH, 12);
                                lLStockManagement.setExpiredDate2(calNext.getTime());
                                lLStockManagement.setQtyUsed(22);
                                try {
                                    PstLLStockManagement.updateExc(lLStockManagement);
                                } catch (Exception exc){

                                }
                            } else {
                                calExp.setTime(lLStockManagement.getExpiredDate2());
                                if (calExp.before(calToday)){
                                    lLStockManagement.setLLStatus(1);
                                    try {
                                        PstLLStockManagement.updateExc(lLStockManagement);
                                    } catch (Exception exc){}


                                    Vector listAL = PstAlStockManagement.list(0, 0, PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+"="+emp.getOID()
                                        +" AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"=0", "");
                                    if (listAL.size()>0){
                                        for (int x=0; x < listAL.size(); x++){
                                            try {
                                                AlStockManagement alStock = (AlStockManagement) listAL.get(x);
                                                alStock.setAlStatus(1);
                                                PstAlStockManagement.updateExc(alStock);
                                            } catch (Exception exc){}
                                        }
                                    }
                                    calNext.setTime(lLStockManagement.getEntitleDate2());
                                    calNext.add(Calendar.MONTH, 12);
                                    calExp.setTime(calNext.getTime());
                                    calExp.add(Calendar.MONTH, 12);
                                    AlStockManagement alStockManagement = new AlStockManagement();
                                    alStockManagement.setEmployeeId(emp.getOID());
                                    alStockManagement.setEntitleDate(calNext.getTime());
                                    alStockManagement.setDtOwningDate(calNext.getTime());
                                    alStockManagement.setRecordDate(new Date());
                                    alStockManagement.setEntitled(12);
                                    alStockManagement.setExpiredDate(calExp.getTime());
                                    alStockManagement.setAlQty(12);
                                    try {
                                        PstAlStockManagement.insertExc(alStockManagement);
                                    } catch (Exception exc){}
                                }
                            }
                        }

                    } else {
                        Calendar calCommencing = Calendar.getInstance();
                        calCommencing.setTime(commDate);
                        double diffYear = calToday.get(Calendar.YEAR) - calCommencing.get(Calendar.YEAR);
                        if (calCommencing.get(Calendar.MONTH) > calToday.get(Calendar.MONTH) || 
                            (calCommencing.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) && calCommencing.get(Calendar.DATE) > calToday.get(Calendar.DATE))) {
                            diffYear--;
                        }

                        double mod = (diffYear - 1.0) / 6.0;
                        //Vector listLLAll = PstLLStockManagement.list(0, 0, PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+"="+emp.getOID(), "");
                        /* check apa ini LL pertama atau bukan*/
                        if (diffYear == 7 && emp.getCompanyId() == 504404575327187914L){
                            Calendar calEntitled = Calendar.getInstance();
                            Calendar calExpired = Calendar.getInstance();
                            calEntitled.setTime(commDate);
                            calEntitled.add(Calendar.YEAR, 7);
                            calExpired.setTime(calEntitled.getTime());
                            calExpired.add(Calendar.YEAR, 1);
                            LLStockManagement lLStockManagement = new LLStockManagement();
                            lLStockManagement.setDtOwningDate(calEntitled.getTime());
                            lLStockManagement.setEntitledDate(calEntitled.getTime());
                            lLStockManagement.setEmployeeId(emp.getOID());
                            lLStockManagement.setEntitle2(0);
                            lLStockManagement.setEntitled(22);
                            lLStockManagement.setExpiredDate(calExpired.getTime());
                            lLStockManagement.setLLQty(22);
                            lLStockManagement.setLLStatus(0);
                            lLStockManagement.setQtyResidue(22);
                            lLStockManagement.setRecordDate(new Date());
                            try {
                                PstLLStockManagement.insertExc(lLStockManagement);
                            } catch (Exception exc){}
                            Vector listAL = PstAlStockManagement.list(0, 0, PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+"="+emp.getOID()
                                +" AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"=0", "");
                            if (listAL.size()>0){
                                for (int x=0; x < listAL.size(); x++){
                                    try {
                                        AlStockManagement alStock = (AlStockManagement) listAL.get(x);
                                        alStock.setAlStatus(1);
                                        PstAlStockManagement.updateExc(alStock);
                                    } catch (Exception exc){}
                                }
                            }
                        } else if (mod % 1 == 0 && diffYear > 7 && emp.getCompanyId() == 504404575327187914L){ //check apa ini LL kedua dan seterusnya
                            Calendar calEntitled = Calendar.getInstance();
                            Calendar calExpired = Calendar.getInstance();
                            calEntitled.setTime(commDate);
                            int periodKe = (int) mod * 6;
                            calEntitled.add(Calendar.YEAR, periodKe+1);
                            calExpired.setTime(calEntitled.getTime());
                            calExpired.add(Calendar.YEAR, 1);
                            LLStockManagement lLStockManagement = new LLStockManagement();
                            lLStockManagement.setDtOwningDate(calEntitled.getTime());
                            lLStockManagement.setEntitledDate(calEntitled.getTime());
                            lLStockManagement.setEmployeeId(emp.getOID());
                            lLStockManagement.setEntitle2(0);
                            lLStockManagement.setEntitled(22);
                            lLStockManagement.setExpiredDate(calExpired.getTime());
                            lLStockManagement.setLLQty(22);
                            lLStockManagement.setLLStatus(0);
                            lLStockManagement.setQtyResidue(22);
                            lLStockManagement.setRecordDate(new Date());
                            try {
                                PstLLStockManagement.insertExc(lLStockManagement);
                            } catch (Exception exc){}
                            Vector listAL = PstAlStockManagement.list(0, 0, PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+"="+emp.getOID()
                                +" AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"=0", "");
                            if (listAL.size()>0){
                                for (int x=0; x < listAL.size(); x++){
                                    try {
                                        AlStockManagement alStock = (AlStockManagement) listAL.get(x);
                                        alStock.setAlStatus(1);
                                        PstAlStockManagement.updateExc(alStock);
                                    } catch (Exception exc){}
                                }
                            }
                        } else { //sisanya berarti AL
                            Vector listAL = PstAlStockManagement.list(0, 0, PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+"="+emp.getOID()
                                +" AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"=0", "");
                            if (listAL.size()>0){
                                AlStockManagement alStock = (AlStockManagement) listAL.get(0);
                                Calendar calAL = Calendar.getInstance();
                                calAL.setTime(alStock.getExpiredDate());
                                if (calAL.before(calToday)){
                                    alStock.setAlStatus(1);
                                    try {
                                        PstAlStockManagement.updateExc(alStock);
                                    } catch (Exception exc){}
                                    alStock = new AlStockManagement();
                                    alStock.setEmployeeId(emp.getOID());
                                    alStock.setEntitleDate(calAL.getTime());
                                    alStock.setDtOwningDate(calAL.getTime());
                                    alStock.setRecordDate(new Date());
                                    alStock.setEntitled(12);
                                    calExp.setTime(calAL.getTime());
                                    calExp.add(Calendar.YEAR, 1);
                                    alStock.setExpiredDate(calExp.getTime());
                                    alStock.setAlQty(12);
                                    try {
                                        PstAlStockManagement.insertExc(alStock);
                                    } catch (Exception exc){}
                                }
                            } else {
                                calNext.setTime(calCommencing.getTime());
                                calNext.add(Calendar.MONTH, 12);
                                if (calNext.before(calToday) || calNext.equals(calToday)){
                                    calExp.setTime(calNext.getTime());
                                    calExp.add(Calendar.MONTH, 12);
                                    AlStockManagement alStockManagement = new AlStockManagement();
                                    alStockManagement.setEmployeeId(emp.getOID());
                                    alStockManagement.setEntitleDate(calNext.getTime());
                                    alStockManagement.setDtOwningDate(calNext.getTime());
                                    alStockManagement.setRecordDate(new Date());
                                    alStockManagement.setEntitled(12);
                                    alStockManagement.setExpiredDate(calExp.getTime());
                                    alStockManagement.setAlQty(12);
                                    try {
                                        PstAlStockManagement.insertExc(alStockManagement);
                                    } catch (Exception exc){}
                                }
                            }
                        }

                    }
                } catch (Exception exc){}
            }
        }
    }
    
    public LeaveAlClosing closeAL(long alStockId, long employeeId, int extended,
            float dataExtended, String entitleDate, String commencingDate){
        
        LeaveAlClosing leaveAlClosing = new LeaveAlClosing();
        if (employeeId != 0){
            leaveAlClosing.setStockId(alStockId);
            leaveAlClosing.setEmployeeId(employeeId);
            leaveAlClosing.setExpiredDate(extended);
            leaveAlClosing.setExtended(0);
            leaveAlClosing.setCommencingDate(commencingDate);
            leaveAlClosing.setEntitledDate(entitleDate);
            leaveAlClosing.setStatus(1);
        }
        return leaveAlClosing;
    }
    
    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    /**
     * @return the sleepMs
     */
    public long getSleepMs() {
        return sleepMs;
    }

    /**
     * @param sleepMs the sleepMs to set
     */
    public void setSleepMs(long sleepMs) {
        this.sleepMs = sleepMs;
    }
    
}
