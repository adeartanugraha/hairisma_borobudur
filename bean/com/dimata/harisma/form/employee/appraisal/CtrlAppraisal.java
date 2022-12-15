/* 
 * Ctrl Name  		:  CtrlAppraisal.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.employee.appraisal;

/* java package */ 

import com.dimata.harisma.entity.employee.appraisal.Appraisal;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisal;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;


public class CtrlAppraisal extends Control implements I_Language 
{
	public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
		{"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private Appraisal appraisal;
	private PstAppraisal pstAppraisal;
	private FrmAppraisal frmAppraisal;


	int language = LANGUAGE_DEFAULT;

	public CtrlAppraisal(HttpServletRequest request){
            msgString = "";
            appraisal = new Appraisal();
            try{
                    pstAppraisal = new PstAppraisal(0);
            }catch(Exception e){;}

            frmAppraisal = new FrmAppraisal(request,appraisal);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
                    case I_DBExceptionInfo.MULTIPLE_ID :
                            this.frmAppraisal.addError(frmAppraisal.FRM_FIELD_APP_MAIN_ID, resultText[language][RSLT_EST_CODE_EXIST] );
                            return resultText[language][RSLT_EST_CODE_EXIST];
                    default:
                            return resultText[language][RSLT_UNKNOWN_ERROR]; 
		}
	}

	private int getControlMsgId(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				return RSLT_EST_CODE_EXIST;
			default:
				return RSLT_UNKNOWN_ERROR;
		}
	}

	public int getLanguage(){ return language; }

	public void setLanguage(int language){ this.language = language; }

	public Appraisal getAppraisal() { return appraisal; } 

	public FrmAppraisal getForm() { return frmAppraisal; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidAppraisal){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidAppraisal != 0){
					try{
						appraisal = PstAppraisal.fetchExc(oidAppraisal);
					}catch(Exception exc){
					}
				}

				frmAppraisal.requestEntityObject(appraisal); 

                                //System.out.println("frmAppraisal.errorSize() : "+frmAppraisal.errorSize());
                                
				if(frmAppraisal.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
				if(appraisal.getOID()==0){
					try{
						long oid = pstAppraisal.insertExc(this.appraisal);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
				}else{
					try {
						long oid = pstAppraisal.updateExc(this.appraisal);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
				}
				break;

			case Command.EDIT :
				if (oidAppraisal != 0) {
					try {
						appraisal = PstAppraisal.fetchExc(oidAppraisal);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidAppraisal != 0) {
					try {
                                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                                            appraisal = PstAppraisal.fetchExc(oidAppraisal);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidAppraisal != 0){
					try{
						long oid = PstAppraisal.deleteExc(oidAppraisal);
						if(oid!=0){
							msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
							excCode = RSLT_OK;
						}else{
							msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
							excCode = RSLT_FORM_INCOMPLETE;
						}
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch(Exception exc){	
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			default :

		}
		return rsCode;
	}
}
