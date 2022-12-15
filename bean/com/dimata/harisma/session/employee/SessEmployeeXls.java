/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.employee;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.FamilyMember;
import com.dimata.harisma.entity.employee.PstEmpEducation;
import com.dimata.harisma.entity.employee.PstEmployee;
import static com.dimata.harisma.entity.employee.PstEmployee.TBL_HR_EMPLOYEE;
import static com.dimata.harisma.entity.employee.PstEmployee.resultToObject;
import com.dimata.harisma.entity.employee.PstFamilyMember;
import com.dimata.harisma.entity.masterdata.EmployeeCompetency;
import com.dimata.harisma.entity.masterdata.PstEmployeeCompetency;
import com.dimata.harisma.entity.masterdata.PstFamRelation;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author gndiw
 */
public class SessEmployeeXls {
    
    public static int getMaxFamilyMember(String whereClause){
        int max = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT MAX(CNT) FROM ( SELECT COUNT("+PstFamilyMember.fieldNames[PstFamilyMember.FLD_FAMILY_MEMBER_ID]+") AS CNT FROM " + PstFamilyMember.TBL_HR_FAMILY_MEMBER +" "
                    + "INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" ON "+PstFamilyMember.TBL_HR_FAMILY_MEMBER+"."+PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]
                    + " = "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            
            sql += " GROUP BY "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") AS DATA";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                max = rs.getInt(1);
            }
            rs.close();
            return max;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return max;
    }
    
    public static int getMaxCompetency(String whereClause){
        int max = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT MAX(CNT) FROM ( SELECT COUNT("+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_COMP_ID]+") AS CNT FROM " + PstEmployeeCompetency.TBL_EMPLOYEE_COMPETENCY +" "
                    + "INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" ON "+PstEmployeeCompetency.TBL_EMPLOYEE_COMPETENCY+"."+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]
                    + " = "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            
            sql += " GROUP BY "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") AS DATA";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                max = rs.getInt(1);
            }
            rs.close();
            return max;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return max;
    }
    
    public static int getMaxEducation(String whereClause){
        int max = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT MAX(CNT) FROM ( SELECT COUNT("+PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID]+") AS CNT FROM " + PstEmpEducation.TBL_HR_EMP_EDUCATION +" "
                    + "INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" ON "+PstEmpEducation.TBL_HR_EMP_EDUCATION+"."+PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]
                    + " = "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            
            sql += " GROUP BY "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") AS DATA";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                max = rs.getInt(1);
            }
            rs.close();
            return max;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return max;
    }
    
    public static HashMap<Long, Vector> getListEmployee(String whereClause){
        HashMap<Long, Vector> mapEmployee = new HashMap<Long, Vector>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+PstFamilyMember.TBL_HR_FAMILY_MEMBER+".* FROM " + PstFamilyMember.TBL_HR_FAMILY_MEMBER +" "
                    + "INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" ON "+PstFamilyMember.TBL_HR_FAMILY_MEMBER+"."+PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]
                    + " = "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                FamilyMember familymember = new FamilyMember();
                resultToObjectFamily(rs, familymember);
                Vector listData = (Vector) mapEmployee.get(familymember.getEmployeeId());
                if (listData == null){
                    listData = new Vector();
                }
                listData.add(familymember);
                mapEmployee.put(familymember.getEmployeeId(), listData);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        
        return mapEmployee;
    }
    
    public static HashMap<Long, Vector> getListCompetency(String whereClause){
        HashMap<Long, Vector> mapEmployee = new HashMap<Long, Vector>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+PstEmployeeCompetency.TBL_EMPLOYEE_COMPETENCY+".* FROM " + PstEmployeeCompetency.TBL_EMPLOYEE_COMPETENCY +" "
                    + "INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" ON "+PstEmployeeCompetency.TBL_EMPLOYEE_COMPETENCY+"."+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]
                    + " = "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                EmployeeCompetency employeeCompetency = new EmployeeCompetency();
                resultToObjectCompetency(rs, employeeCompetency);
                Vector listData = (Vector) mapEmployee.get(employeeCompetency.getEmployeeId());
                if (listData == null){
                    listData = new Vector();
                }
                listData.add(employeeCompetency);
                mapEmployee.put(employeeCompetency.getEmployeeId(), listData);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        
        return mapEmployee;
    }
    
    
    private static void resultToObjectFamily(ResultSet rs, FamilyMember familymember) {
        try {
            familymember.setOID(rs.getLong(PstFamilyMember.fieldNames[PstFamilyMember.FLD_FAMILY_MEMBER_ID]));
            familymember.setEmployeeId(rs.getLong(PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]));
            familymember.setFullName(rs.getString(PstFamilyMember.fieldNames[PstFamilyMember.FLD_FULL_NAME]));
            familymember.setRelationship(rs.getString(PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELATIONSHIP]));
            familymember.setBirthDate(rs.getDate(PstFamilyMember.fieldNames[PstFamilyMember.FLD_BIRTH_DATE]));
            familymember.setJob(rs.getString(PstFamilyMember.fieldNames[PstFamilyMember.FLD_JOB]));
            familymember.setAddress(rs.getString(PstFamilyMember.fieldNames[PstFamilyMember.FLD_ADDRESS]));
            familymember.setGuaranteed(rs.getBoolean(PstFamilyMember.fieldNames[PstFamilyMember.FLD_GUARANTEED]));
            familymember.setIgnoreBirth(rs.getBoolean(PstFamilyMember.fieldNames[PstFamilyMember.FLD_IGNORE_BIRTH]));
            familymember.setEducationId(rs.getLong(PstFamilyMember.fieldNames[PstFamilyMember.FLD_EDUCATION_ID]));
            familymember.setReligionId(rs.getLong(PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELIGION_ID]));
            familymember.setSex(rs.getInt(PstFamilyMember.fieldNames[PstFamilyMember.FLD_SEX]));

        } catch (Exception e) {
        }
    }
    
    public static void resultToObjectCompetency(ResultSet rs, EmployeeCompetency entEmployeeCompetency) {
        try {
            entEmployeeCompetency.setOID(rs.getLong(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_COMP_ID]));
            entEmployeeCompetency.setEmployeeId(rs.getLong(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]));
            entEmployeeCompetency.setCompetencyId(rs.getLong(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_COMPETENCY_ID]));
            entEmployeeCompetency.setLevelValue(rs.getFloat(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_LEVEL_VALUE]));
            entEmployeeCompetency.setSpecialAchievement(rs.getString(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_SPECIAL_ACHIEVEMENT]));
            entEmployeeCompetency.setDateOfAchvmt(rs.getDate(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_DATE_OF_ACHVMT]));
            entEmployeeCompetency.setHistory(rs.getInt(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_HISTORY]));
            entEmployeeCompetency.setProviderId(rs.getLong(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_PROVIDER_ID]));
        } catch (Exception e) {
        }
    }
    
}
