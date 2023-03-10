/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

/**
 *
 * @author Hendra Putu
 */
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

public class PstEmpCustomField extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_EMP_CUSTOM_FIELD = "hr_emp_custom_field";
    public static final int FLD_EMP_CUSTOM_FIELD_ID = 0;
    public static final int FLD_DATA_NUMBER = 1;
    public static final int FLD_DATA_TEXT = 2;
    public static final int FLD_DATA_DATE = 3;
    public static final int FLD_CUSTOM_FIELD_ID = 4;
    public static final int FLD_EMPLOYEE_ID = 5;

    public static String[] fieldNames = {
        "EMP_CUSTOM_FIELD_ID",
        "DATA_NUMBER",
        "DATA_TEXT",
        "DATA_DATE",
        "CUSTOM_FIELD_ID",
        "EMPLOYEE_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstEmpCustomField() {
    }

    public PstEmpCustomField(int i) throws DBException {
        super(new PstEmpCustomField());
    }

    public PstEmpCustomField(String sOid) throws DBException {
        super(new PstEmpCustomField(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpCustomField(long lOid) throws DBException {
        super(new PstEmpCustomField(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_EMP_CUSTOM_FIELD;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpCustomField().getClass().getName();
    }

    public static EmpCustomField fetchExc(long oid) throws DBException {
        try {
            EmpCustomField entEmpCustomField = new EmpCustomField();
            PstEmpCustomField pstEmpCustomField = new PstEmpCustomField(oid);
            entEmpCustomField.setOID(oid);
            entEmpCustomField.setDataNumber(pstEmpCustomField.getdouble(FLD_DATA_NUMBER));
            entEmpCustomField.setDataText(pstEmpCustomField.getString(FLD_DATA_TEXT));
            entEmpCustomField.setDataDate(pstEmpCustomField.getDate(FLD_DATA_DATE));
            entEmpCustomField.setCustomFieldId(pstEmpCustomField.getLong(FLD_CUSTOM_FIELD_ID));
            entEmpCustomField.setEmployeeId(pstEmpCustomField.getLong(FLD_EMPLOYEE_ID));
            return entEmpCustomField;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpCustomField(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        EmpCustomField entEmpCustomField = fetchExc(entity.getOID());
        entity = (Entity) entEmpCustomField;
        return entEmpCustomField.getOID();
    }

    public static synchronized long updateExc(EmpCustomField entEmpCustomField) throws DBException {
        try {
            if (entEmpCustomField.getOID() != 0) {
                PstEmpCustomField pstEmpCustomField = new PstEmpCustomField(entEmpCustomField.getOID());
                pstEmpCustomField.setDouble(FLD_DATA_NUMBER, entEmpCustomField.getDataNumber());
                pstEmpCustomField.setString(FLD_DATA_TEXT, entEmpCustomField.getDataText());
                pstEmpCustomField.setDate(FLD_DATA_DATE, entEmpCustomField.getDataDate());
                pstEmpCustomField.setLong(FLD_CUSTOM_FIELD_ID, entEmpCustomField.getCustomFieldId());
                pstEmpCustomField.setLong(FLD_EMPLOYEE_ID, entEmpCustomField.getEmployeeId());
                pstEmpCustomField.update();
                return entEmpCustomField.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpCustomField(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((EmpCustomField) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstEmpCustomField pstEmpCustomField = new PstEmpCustomField(oid);
            pstEmpCustomField.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpCustomField(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(EmpCustomField entEmpCustomField) throws DBException {
        try {
            PstEmpCustomField pstEmpCustomField = new PstEmpCustomField(0);
            pstEmpCustomField.setDouble(FLD_DATA_NUMBER, entEmpCustomField.getDataNumber());
            pstEmpCustomField.setString(FLD_DATA_TEXT, entEmpCustomField.getDataText());
            pstEmpCustomField.setDate(FLD_DATA_DATE, entEmpCustomField.getDataDate());
            pstEmpCustomField.setLong(FLD_CUSTOM_FIELD_ID, entEmpCustomField.getCustomFieldId());
            pstEmpCustomField.setLong(FLD_EMPLOYEE_ID, entEmpCustomField.getEmployeeId());
            pstEmpCustomField.insert();
            entEmpCustomField.setOID(pstEmpCustomField.getLong(FLD_EMP_CUSTOM_FIELD_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpCustomField(0), DBException.UNKNOWN);
        }
        return entEmpCustomField.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((EmpCustomField) entity);
    }

    public static void resultToObject(ResultSet rs, EmpCustomField entEmpCustomField) {
        try {
            entEmpCustomField.setOID(rs.getLong(PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_EMP_CUSTOM_FIELD_ID]));
            entEmpCustomField.setDataNumber(rs.getDouble(PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_DATA_NUMBER]));
            entEmpCustomField.setDataText(rs.getString(PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_DATA_TEXT]));
            entEmpCustomField.setDataDate(rs.getDate(PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_DATA_DATE]));
            entEmpCustomField.setCustomFieldId(rs.getLong(PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_CUSTOM_FIELD_ID]));
            entEmpCustomField.setEmployeeId(rs.getLong(PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_EMPLOYEE_ID]));
        } catch (Exception e) {
        }
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_EMP_CUSTOM_FIELD;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                EmpCustomField entEmpCustomField = new EmpCustomField();
                resultToObject(rs, entEmpCustomField);
                lists.add(entEmpCustomField);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static boolean checkOID(long entEmpCustomFieldId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_EMP_CUSTOM_FIELD + " WHERE "
                    + PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_EMP_CUSTOM_FIELD_ID] + " = " + entEmpCustomFieldId;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_EMP_CUSTOM_FIELD_ID] + ") FROM " + TBL_EMP_CUSTOM_FIELD;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    EmpCustomField entEmpCustomField = (EmpCustomField) list.get(ls);
                    if (oid == entEmpCustomField.getOID()) {
                        found = true;
                    }
                }
            }
        }
        if ((start >= size) && (size > 0)) {
            start = start - recordToGet;
        }
        return start;
    }

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + (recordToGet - mdl);
        if (start == 0) {
            cmd = Command.FIRST;
        } else {
            if (start == (vectSize - recordToGet)) {
                cmd = Command.LAST;
            } else {
                start = start + recordToGet;
                if (start <= (vectSize - recordToGet)) {
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }
        return cmd;
    }
}
