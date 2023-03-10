
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 

/* package java */ 
import java.io.*
;
import java.sql.*
;import java.util.*
;import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.masterdata.*; 

public class PstResignedReason extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_RESIGNED_REASON = "hr_resigned_reason";//"HR_RESIGNED_REASON";

	public static final  int FLD_RESIGNED_REASON_ID = 0;
	public static final  int FLD_RESIGNED_REASON = 1;
        public static final  int FLD_RESIGNED_CODE = 2;

	public static final  String[] fieldNames = {
		"RESIGNED_REASON_ID",
		"RESIGNED_REASON",
                "RESIGNED_CODE"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_STRING
	 }; 

	public PstResignedReason(){
	}

	public PstResignedReason(int i) throws DBException { 
		super(new PstResignedReason()); 
	}

	public PstResignedReason(String sOid) throws DBException { 
		super(new PstResignedReason(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstResignedReason(long lOid) throws DBException { 
		super(new PstResignedReason(0)); 
		String sOid = "0"; 
		try { 
			sOid = String.valueOf(lOid); 
		}catch(Exception e) { 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	} 

	public int getFieldSize(){ 
		return fieldNames.length; 
	}

	public String getTableName(){ 
		return TBL_HR_RESIGNED_REASON;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstResignedReason().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		ResignedReason resignedreason = fetchExc(ent.getOID()); 
		ent = (Entity)resignedreason; 
		return resignedreason.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((ResignedReason) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((ResignedReason) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static ResignedReason fetchExc(long oid) throws DBException{ 
		try{ 
			ResignedReason resignedreason = new ResignedReason();
			PstResignedReason pstResignedReason = new PstResignedReason(oid); 
			resignedreason.setOID(oid);

			resignedreason.setResignedReason(pstResignedReason.getString(FLD_RESIGNED_REASON));
                        resignedreason.setResignedCode(pstResignedReason.getString(FLD_RESIGNED_CODE));

			return resignedreason; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstResignedReason(0),DBException.UNKNOWN); 
		} 
	}

        
        public static String GetResignedReasonName(long oid) throws DBException{ 
                String name = ""; 
		try{ 
			ResignedReason resignedreason = new ResignedReason();
			resignedreason = fetchExc(oid);
                        name = resignedreason.getResignedReason();
			return name; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstResignedReason(0),DBException.UNKNOWN); 
		} 
	}

        
	public static long insertExc(ResignedReason resignedreason) throws DBException{ 
		try{ 
			PstResignedReason pstResignedReason = new PstResignedReason(0);

			pstResignedReason.setString(FLD_RESIGNED_REASON, resignedreason.getResignedReason());
                        pstResignedReason.setString(FLD_RESIGNED_CODE, resignedreason.getResignedCode());

			pstResignedReason.insert(); 
			resignedreason.setOID(pstResignedReason.getlong(FLD_RESIGNED_REASON_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstResignedReason(0),DBException.UNKNOWN); 
		}
		return resignedreason.getOID();
	}

	public static long updateExc(ResignedReason resignedreason) throws DBException{ 
		try{ 
			if(resignedreason.getOID() != 0){ 
				PstResignedReason pstResignedReason = new PstResignedReason(resignedreason.getOID());

				pstResignedReason.setString(FLD_RESIGNED_REASON, resignedreason.getResignedReason());
                                pstResignedReason.setString(FLD_RESIGNED_CODE, resignedreason.getResignedCode());

				pstResignedReason.update(); 
				return resignedreason.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstResignedReason(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstResignedReason pstResignedReason = new PstResignedReason(oid);
			pstResignedReason.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstResignedReason(0),DBException.UNKNOWN); 
		}
		return oid;
	}

	public static Vector listAll(){ 
		return list(0, 500, "",""); 
	}

	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_RESIGNED_REASON; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				ResignedReason resignedreason = new ResignedReason();
				resultToObject(rs, resignedreason);
				lists.add(resignedreason);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return new Vector();
	}

	private static void resultToObject(ResultSet rs, ResignedReason resignedreason){
		try{
			resignedreason.setOID(rs.getLong(PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON_ID]));
			resignedreason.setResignedReason(rs.getString(PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON]));
                        resignedreason.setResignedCode(rs.getString(PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_CODE]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long resignedReasonId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_RESIGNED_REASON + " WHERE " + 
						PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON_ID] + " = " + resignedReasonId;

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
			String sql = "SELECT COUNT("+ PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON_ID] + ") FROM " + TBL_HR_RESIGNED_REASON;
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


	/* This method used to find current data */
	public static int findLimitStart( long oid, int recordToGet, String whereClause, String orderClause){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   ResignedReason resignedreason = (ResignedReason)list.get(ls);
				   if(oid == resignedreason.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
	/* This method used to find command where current data */
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
}
