/* Generated by Together */
package com.dimata.harisma.form.search;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.search.*;

public class FrmSrcMedicalRecord extends FRMHandler implements I_FRMInterface, I_FRMType {

    private SrcMedicalRecord srcMedicalRecord;
    public static final String FRM_NAME_SRCMEDIACALRECORD = "FRM_NAME_SRCMEDIACALRECORD";
    public static final int FRM_FIELD_DESEASE_TYPE_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_END_DATE = 2;
    public static final int FRM_FIELD_MEDICAL_TYPE_ID = 3;
    public static final int FRM_FIELD_ORDER_BY = 4;
    public static final int FRM_FIELD_START_DATE = 5;
    public static final int FRM_FIELD_DEPARTMENT_ID = 6;
    public static final int FRM_FIELD_MEDICAL_CASE_ID = 7;
    public static String[] fieldNames = {
        "FRM_FIELD_DESEASE_TYPE_ID", "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_END_DATE", "FRM_FIELD_MEDICAL_TYPE_ID",
        "FRM_FIELD_ORDER_BY", "FRM_FIELD_START_DATE",
        "FRM_FIELD_DEPARTMENT_ID", "FRM_FIELD_MEDICAL_CASE_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_LONG,
        TYPE_DATE, TYPE_LONG,
        TYPE_INT, TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG
    };

    public FrmSrcMedicalRecord() {
    }

    public FrmSrcMedicalRecord(SrcMedicalRecord srcMedicalRecord) {
        this.srcMedicalRecord = srcMedicalRecord;
    }

    public FrmSrcMedicalRecord(HttpServletRequest request, SrcMedicalRecord srcMedicalRecord) {
        super(new FrmSrcMedicalRecord(srcMedicalRecord), request);
        this.srcMedicalRecord = srcMedicalRecord;
    }

    public String getFormName() {
        return FRM_NAME_SRCMEDIACALRECORD;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public SrcMedicalRecord getEntityObject() {
        return srcMedicalRecord;
    }

    public void requestEntityObject(SrcMedicalRecord srcMedicalRecord) {
        try {
            this.requestParam();
            srcMedicalRecord.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            srcMedicalRecord.setDiseaseTypeId(getLong(FRM_FIELD_DESEASE_TYPE_ID));
            srcMedicalRecord.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            srcMedicalRecord.setEndDate(getDate(FRM_FIELD_END_DATE));
            srcMedicalRecord.setStartDate(getDate(FRM_FIELD_START_DATE));
            srcMedicalRecord.setMedicalTypeId(getLong(FRM_FIELD_MEDICAL_TYPE_ID));
            srcMedicalRecord.setOrderBy(getInt(FRM_FIELD_ORDER_BY));
            srcMedicalRecord.setMedicalCaseId(getLong(FRM_FIELD_MEDICAL_CASE_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
