/**
 * User: wardana
 * Date: Apr 8, 2004
 * Time: 8:48:43 AM
 * Version: 1.0
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.employee.Employee;        
import com.dimata.harisma.entity.employee.PstEmployee;
import java.util.Vector;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class PstPublicHolidays extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_PUBLIC_HOLIDAYS = "hr_public_holidays";//"HR_PUBLIC_HOLIDAYS";

    public static final int FLD_PUBLIC_HOLIDAY_ID = 0;
    public static final int FLD_HOLIDAY_DATE = 1;
    public static final int FLD_HOLIDAY_DESC = 2;
    public static final int FLD_HOLIDAY_STATUS = 3;
    public static final int FLD_HOLIDAY_DATE_TO = 4;
    public static final int FLD_DAY_LEN = 5;

    public static final String[] fieldNames = {
        "PUBLIC_HOLIDAY_ID",
        "HOLIDAY_DATE",
        "HOLIDAY_DESC",
        "HOLIDAY_STATUS",
        "HOLIDAY_DATE_TO",
        "DAY_LEN"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT
    };

    public static int STS_NONE = 0;
    public static int STS_NATIONAL = 1;
    public static int STS_BLACK_DAY = 2;
    public static int STS_YELLOW_DAY = 3;
    
/*    public static int STS_BUDHA = 2;
    public static int STS_HINDHU = 3;
    public static int STS_MOSLEM = 4;
    public static int STS_KATOLIK = 5;
    public static int STS_KHONGHUCU = 6;
    public static int STS_PROTESTAN = 7;

    public static String[] stHolidaySts = {
        "","National", "Budha", "Hindhu", "Moslem","Catholic","Khonchuchu", "Protestant"
    };*/
    
    public static String HINDU_STR = "Hindu";

    public static String[] stHolidaySts = {
        "-","National", "Black Day", "Yellow Day"
    }; 
       
    
    public PstPublicHolidays() {
    }

    public PstPublicHolidays(int i) throws DBException {
        super(new PstPublicHolidays());
    }

    public PstPublicHolidays(String sOid) throws DBException {
        super(new PstPublicHolidays(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public PstPublicHolidays(long lOid) throws DBException {
        super(new PstPublicHolidays(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_PUBLIC_HOLIDAYS;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPublicHolidays().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        PublicHolidays objPublicHolidays = fetchExc(ent.getOID());
        return objPublicHolidays.getOID();
    }

    public static PublicHolidays fetchExc(long oid) throws DBException {
        try {
            PublicHolidays objPublicHolidays = new PublicHolidays();
            PstPublicHolidays objPstPublicHolidays = new PstPublicHolidays(oid);
            objPublicHolidays.setOID(oid);

            objPublicHolidays.setDtHolidayDate(objPstPublicHolidays.getDate(FLD_HOLIDAY_DATE));
            objPublicHolidays.setDtHolidayDateTo(objPstPublicHolidays.getDate(FLD_HOLIDAY_DATE_TO));
            //objPublicHolidays.setDays(objPstPublicHolidays.getInt(FLD_DAY_LEN));
            objPublicHolidays.setStDesc(objPstPublicHolidays.getString(FLD_HOLIDAY_DESC));
            objPublicHolidays.setiHolidaySts(objPstPublicHolidays.getlong(FLD_HOLIDAY_STATUS));

            return objPublicHolidays;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPublicHolidays(0), DBException.UNKNOWN);
        }
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PublicHolidays) ent);
    }

    public static long updateExc(PublicHolidays objPublicHolidays) throws DBException {
        try {
            if (objPublicHolidays.getOID() != 0) {
                PstPublicHolidays objPstPublicHolidays = new PstPublicHolidays(objPublicHolidays.getOID());

                objPstPublicHolidays.setDate(FLD_HOLIDAY_DATE, objPublicHolidays.getDtHolidayDate());
                objPstPublicHolidays.setDate(FLD_HOLIDAY_DATE_TO, objPublicHolidays.getDtHolidayDateTo());
                objPstPublicHolidays.setInt(FLD_DAY_LEN, objPublicHolidays.getDays());
                objPstPublicHolidays.setString(FLD_HOLIDAY_DESC, objPublicHolidays.getStDesc());
                objPstPublicHolidays.setLong(FLD_HOLIDAY_STATUS, objPublicHolidays.getiHolidaySts());

                objPstPublicHolidays.update();
                return objPublicHolidays.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPublicHolidays(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPublicHolidays objPstPublicHolidays = new PstPublicHolidays(oid);
            objPstPublicHolidays.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPublicHolidays(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PublicHolidays) ent);
    }

    public static long insertExc(PublicHolidays objPublicHolidays) throws DBException {
        try {
            PstPublicHolidays objPstPublicHolidays = new PstPublicHolidays(0);

            objPstPublicHolidays.setDate(FLD_HOLIDAY_DATE, objPublicHolidays.getDtHolidayDate());
            objPstPublicHolidays.setDate(FLD_HOLIDAY_DATE_TO, objPublicHolidays.getDtHolidayDateTo());
            objPstPublicHolidays.setInt(FLD_DAY_LEN, objPublicHolidays.getDays());
            objPstPublicHolidays.setString(FLD_HOLIDAY_DESC, objPublicHolidays.getStDesc());
            objPstPublicHolidays.setLong(FLD_HOLIDAY_STATUS, objPublicHolidays.getiHolidaySts());

            objPstPublicHolidays.insert();
            objPublicHolidays.setOID(objPstPublicHolidays.getlong(FLD_PUBLIC_HOLIDAY_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPublicHolidays(0), DBException.UNKNOWN);
        }
        return objPublicHolidays.getOID();
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PUBLIC_HOLIDAYS;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            
            //System.out.println("SQL List PstPublicHolidays : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PublicHolidays objPublicHolidays = new PublicHolidays();
                resultToObject(rs, objPublicHolidays);
                lists.add(objPublicHolidays);
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
   
    
      public static Vector SelectYearNotEmpty() {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT(DATE_FORMAT(`holiday_date`, '%Y')) AS YEAR FROM " + TBL_PUBLIC_HOLIDAYS;
        
                sql = sql + " ORDER BY " + FLD_HOLIDAY_DATE;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                lists.add(rs.getString("YEAR"));
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
    
    //update by satrya 2012-10-09
   /**
    * Keterangan : mencari hari libur berdasarkan selected Date. untuk report
    * @param selectedDateFrom
    * @param selectedDateTo
    * @return 
    */
    public static  HolidaysTable getHolidaysTable (Date selectedDateFrom, Date selectedDateTo) {
        DBResultSet dbrs = null;
        //update by satrya 2012-10-15
    if(selectedDateFrom!=null && selectedDateTo!=null){
        if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                Date tempFromDate = selectedDateFrom;
                Date tempToDate = selectedDateTo;
                selectedDateFrom = tempToDate;
                selectedDateTo = tempFromDate;
            }
        HolidaysTable holidaysTable = new HolidaysTable();
        try {
                        String sql = " SELECT "
                        + " HH."+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_STATUS] +","
                        + " HH."+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE] +","
                        + " HH."+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE_TO] +","
                        + " HH."+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_PUBLIC_HOLIDAY_ID] +","
                        + " HH."+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DESC]        
                        + " FROM " + TBL_PUBLIC_HOLIDAYS + " AS HH ";
                       
            if (selectedDateFrom != null && selectedDateTo != null) {
                sql = sql + " WHERE (HH." + PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE]
                        + " >= " + "\"" + Formater.formatDate(selectedDateFrom, "yyyy-MM-dd") + "\"" 
                        +" AND " +" HH." + PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE_TO]
                        +" <= \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd") + "\"" 
                        +" ) ";
            }
                sql = sql + " ORDER BY "+fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE]+" ASC ";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                if(rs.getLong(fieldNames[FLD_HOLIDAY_STATUS])== (long) STS_NATIONAL){                    
                    //update by satrya 2013-03-07
                    //holidaysTable.addReligionHoliday(rs.getLong(fieldNames[FLD_HOLIDAY_STATUS]) ,rs.getDate(fieldNames[FLD_HOLIDAY_DATE]));
                    holidaysTable.addNationalHoliday(rs.getDate(fieldNames[FLD_HOLIDAY_DATE]), rs.getDate(fieldNames[FLD_HOLIDAY_DATE_TO]));
                    holidaysTable.addDescHolidayNational(rs.getDate(fieldNames[FLD_HOLIDAY_DATE]), rs.getDate(fieldNames[FLD_HOLIDAY_DATE_TO]),rs.getString(fieldNames[FLD_HOLIDAY_DESC]));
                  //update by satrya 2013-03-07  
                 //tidak kepakai karena untuk cuti bersama bisa di pakai jam"jaman   
                /*}else if(rs.getLong(fieldNames[FLD_HOLIDAY_STATUS])== (long) STS_BLACK_DAY || rs.getLong(fieldNames[FLD_HOLIDAY_STATUS])== (long) STS_YELLOW_DAY){
                    holidaysTable.addNationalHoliday(rs.getDate(fieldNames[FLD_HOLIDAY_DATE]), rs.getDate(fieldNames[FLD_HOLIDAY_DATE_TO]));
                }*/ 
                }else{
                    holidaysTable.addReligionHoliday(rs.getLong(fieldNames[FLD_HOLIDAY_STATUS]) ,rs.getDate(fieldNames[FLD_HOLIDAY_DATE]),rs.getDate(fieldNames[FLD_HOLIDAY_DATE_TO]));
                    //update by satrya 2013-03-07
                    //holidaysTable.addReligionHoliday(rs.getLong(fieldNames[FLD_HOLIDAY_STATUS]) ,rs.getDate(fieldNames[FLD_HOLIDAY_DATE_TO]));
                    holidaysTable.addDescHolidayReligion(rs.getLong(fieldNames[FLD_HOLIDAY_STATUS]) ,rs.getDate(fieldNames[FLD_HOLIDAY_DATE]) ,rs.getString(fieldNames[FLD_HOLIDAY_DESC]));
                }
                
            }
        } catch (Exception e) {
            System.out.println("Exception Holidays"+e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
         return holidaysTable;
    } return new HolidaysTable();
    }
    private static void resultToObject(ResultSet rs, PublicHolidays objPublicHolidays) {
        try {
            objPublicHolidays.setOID(rs.getLong(PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_PUBLIC_HOLIDAY_ID]));
            //objPublicHolidays.setDays(rs.getInt(PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_DAY_LEN]));
            objPublicHolidays.setDtHolidayDate(rs.getDate(PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE]));
            objPublicHolidays.setDtHolidayDateTo(rs.getDate(PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE_TO]));            
            objPublicHolidays.setStDesc(rs.getString(PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DESC]));
            objPublicHolidays.setiHolidaySts(rs.getLong(PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_STATUS]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkOID(long lHolidayId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PUBLIC_HOLIDAYS + " WHERE " +
						PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_PUBLIC_HOLIDAY_ID] + " = " + lHolidayId;

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) { result = true; }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return result;
		}
	}

    public static int getCount(String whereClause){
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT COUNT("+ PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_PUBLIC_HOLIDAY_ID] + ") FROM " + TBL_PUBLIC_HOLIDAYS;
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			int count = 0;
			while(rs.next()) { count = rs.getInt(1); }

			rs.close();
			return count;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}

    public static int findLimitStart( long oid, int recordToGet, String whereClause, String orderClause){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause);
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){
			  	   PublicHolidays objPublicHolidays = (PublicHolidays)list.get(ls);
				   if(oid == objPublicHolidays.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}

    public static int findLimitCommand(int start, int recordToGet, int vectSize){
		 int cmd = Command.LIST;
		 int mdl = vectSize % recordToGet;
		 vectSize = vectSize + (recordToGet - mdl);
		 if(start == 0)
			 cmd =  Command.FIRST;
		 else{
			 if(start == (vectSize-recordToGet))
				 cmd = Command.LAST;
			 else{
				 start = start + recordToGet;
				 if(start <= (vectSize - recordToGet)){
					 cmd = Command.NEXT;
					 System.out.println("next.......................");
				 }else{
					 start = start - recordToGet;
					 if(start > 0){
						 cmd = Command.PREV;
						 System.out.println("prev.......................");
					 }
				 }
			 }
		 }

		 return cmd;
	}

    public static Vector getHolidayByDate(Date dtHolidayDate){
        Vector vList = new Vector();
        PublicHolidays objPublicHolidays = new PublicHolidays();
        DBResultSet dbrs;
        String stSQL = " SELECT * FROM "+ TBL_PUBLIC_HOLIDAYS +
                       " WHERE "+ PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE] +
                       " = '" + Formater.formatDate(dtHolidayDate, "yyyy-MM-dd")+"'";
        //System.out.println("stSQL : "+stSQL);
        try{
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                objPublicHolidays = new PublicHolidays();
                resultToObject(rs, objPublicHolidays);
                vList.add(objPublicHolidays);
            }
        }
        catch(DBException dbe){
            dbe.printStackTrace();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return vList;
    }
/**
 * create by satrya 2013-07-01
 * mencari hari raya nyepi
 * @param dtHolidayDate
 * @return 
 */
    public static Vector getHolidayByDateAndNameNyepi(Date dtHolidayDate){
        Vector vList = new Vector();
        PublicHolidays objPublicHolidays = new PublicHolidays();
        String Nyepi = "nyepi";
        DBResultSet dbrs;
        String stSQL = " SELECT * FROM "+ TBL_PUBLIC_HOLIDAYS +
                       " WHERE "+ PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE] +
                       " = '" + Formater.formatDate(dtHolidayDate, "yyyy-MM-dd")+"' AND " +
                       PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DESC]+" LIKE \"%"+Nyepi+"%\"";
       // System.out.println("stSQL : "+stSQL);
        try{
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                objPublicHolidays = new PublicHolidays();
                resultToObject(rs, objPublicHolidays);
                vList.add(objPublicHolidays);
            }
        }
        catch(DBException dbe){
            dbe.printStackTrace();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return vList;
    }
    public static Vector getHolidayByDate(Date dtHolidayDate, int type){
        Vector vList = new Vector();
        PublicHolidays objPublicHolidays = new PublicHolidays();
        DBResultSet dbrs;
        String stSQL = " SELECT * FROM "+ TBL_PUBLIC_HOLIDAYS +
                       " WHERE "+ PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE] +
                       " = '" + Formater.formatDate(dtHolidayDate, "yyyy-MM-dd")+"'";
        
        stSQL = stSQL + " AND "+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_STATUS]+"="+type;
                    /*
                    if(type!=-1){
                        stSQL = stSQL + " AND ("+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_STATUS]+"="+PstPublicHolidays.STS_BOTH+
                            " OR "+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_STATUS]+"="+type+")";
                    }else{
                        stSQL = stSQL + " AND "+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_STATUS]+"="+PstPublicHolidays.STS_BOTH;
                    }
                     * */
        System.out.println("stSQL : "+stSQL);
        try{
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                objPublicHolidays = new PublicHolidays();
                resultToObject(rs, objPublicHolidays);
                vList.add(objPublicHolidays);
            }
        }
        catch(DBException dbe){
            dbe.printStackTrace();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return vList;
    }

    /**
     * @desc mengecek apakah employee mendapat public holiday pada tgl tertentu
     * @param employee Employee
     * @param date Date
     * @return boolean
     */
    public static boolean isPublicHoliday(Employee employee, Date date){
        boolean status = false;
        //Apakah tgl tersebut merupakan hari libur?
        Vector vPublicHolidays = new Vector();
        vPublicHolidays = PstPublicHolidays.getHolidayByDate(date);
        if(vPublicHolidays.size()>0){
            for(int i=0;i<vPublicHolidays.size();i++){
                PublicHolidays publicHolidays = new PublicHolidays();
                publicHolidays = (PublicHolidays)vPublicHolidays.get(i);
                if(publicHolidays.getiHolidaySts() == 1 || publicHolidays.getiHolidaySts() == employee.getReligionId()){
                    return true;
                }
            }
        }
        return status;
    }
    
    public static Vector listPublicHolidayUnion(Date startDate, Date toDate){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "(SELECT "
                            + "PH."+fieldNames[FLD_PUBLIC_HOLIDAY_ID]+", "
                            + "PH."+fieldNames[FLD_HOLIDAY_DATE]+", "
                            + "PH."+fieldNames[FLD_HOLIDAY_DESC]+", "
                            + "PH."+fieldNames[FLD_HOLIDAY_STATUS]+", "
                            + "PH."+fieldNames[FLD_HOLIDAY_DATE_TO]+", "
                            + "PH."+fieldNames[FLD_DAY_LEN]+" "
                        + "FROM "+TBL_PUBLIC_HOLIDAYS+" AS PH "
                        + "WHERE PH."+fieldNames[FLD_HOLIDAY_DATE]+" BETWEEN '"+
                            Formater.formatDate(startDate, "yyyy-MM-dd")+"' AND '"+Formater.formatDate(toDate, "yyyy-MM-dd")+"')"
                        + "UNION "
                        + "(SELECT "
                        + "PH."+fieldNames[FLD_PUBLIC_HOLIDAY_ID]+", "
                            + "PH."+fieldNames[FLD_HOLIDAY_DATE_TO]+" AS "+fieldNames[FLD_HOLIDAY_DATE]+", "
                            + "PH."+fieldNames[FLD_HOLIDAY_DESC]+", "
                            + "PH."+fieldNames[FLD_HOLIDAY_STATUS]+", "
                            + "PH."+fieldNames[FLD_HOLIDAY_DATE_TO]+", "
                            + "PH."+fieldNames[FLD_DAY_LEN]+" "
                        + "FROM "+TBL_PUBLIC_HOLIDAYS+" AS PH "
                        + "WHERE PH."+fieldNames[FLD_HOLIDAY_DATE_TO]+" BETWEEN '"+
                            Formater.formatDate(startDate, "yyyy-MM-dd")+"' AND '"+Formater.formatDate(toDate, "yyyy-MM-dd")+"') "
                        + "ORDER BY "+fieldNames[FLD_HOLIDAY_DATE];
            dbrs = DBHandler.execQueryResult(sql);
            System.out.println(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PublicHolidays objPublicHolidays = new PublicHolidays();
                resultToObject(rs, objPublicHolidays);
                lists.add(objPublicHolidays);
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
    
//add by eri yudi 20200904 untuk analisa dp
       public static PublicHolidays getPublicHolidaysByDate(Date dtHolidayDate, long empOid) {
/* 512 */     PublicHolidays objPublicHolidays = new PublicHolidays();
/*     */ 
/*     */     
/* 515 */     Employee employee = new Employee();
/*     */     
/*     */     try {
/* 518 */       employee = PstEmployee.fetchExc(empOid);
/* 519 */     } catch (Exception e) {}
/*     */     
/* 521 */     String stSQL = " SELECT * FROM hr_public_holidays WHERE " + fieldNames[1] + " = '" + Formater.formatDate(dtHolidayDate, "yyyy-MM-dd") + "' " + " AND " + fieldNames[3] + " IN (" + employee.getReligionId() + ",1)";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 527 */       DBResultSet dbrs = DBHandler.execQueryResult(stSQL);
/* 528 */       ResultSet rs = dbrs.getResultSet();
/* 529 */       while (rs.next()) {
/* 530 */         resultToObject(rs, objPublicHolidays);
/*     */       }
/*     */     }
/* 533 */     catch (DBException dbe) {
/* 534 */       dbe.printStackTrace();
/*     */     }
/* 536 */     catch (SQLException sqle) {
/* 537 */       sqle.printStackTrace();
/*     */     } 
/* 539 */     return objPublicHolidays;
/*     */   }
}
