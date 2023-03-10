/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form;

import com.dimata.harisma.entity.employee.EmpRelvtDocPage;
import com.dimata.harisma.entity.employee.PstEmpRelvtDocPage;
import com.dimata.harisma.form.employee.FrmEmpRelvtDocPage;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class CtrlEmpRelvtDocPage extends Control implements I_Language {

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
    private EmpRelvtDocPage entEmpRelvtDocPage;
    private PstEmpRelvtDocPage pstEmpRelvtDocPage;
    private FrmEmpRelvtDocPage frmEmpRelvtDocPage;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpRelvtDocPage(HttpServletRequest request) {
        msgString = "";
        entEmpRelvtDocPage = new EmpRelvtDocPage();
        try {
            pstEmpRelvtDocPage = new PstEmpRelvtDocPage(0);
        } catch (Exception e) {;
        }
        frmEmpRelvtDocPage = new FrmEmpRelvtDocPage(request, entEmpRelvtDocPage);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpRelvtDocPage.addError(frmEmpRelvtDocPage.FRM_FIELD_EMP_RELVT_DOC_PAGE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }

    private int getControlMsgId(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public EmpRelvtDocPage getEmpRelvtDocPage() {
        return entEmpRelvtDocPage;
    }

    public FrmEmpRelvtDocPage getForm() {
        return frmEmpRelvtDocPage;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpRelvtDocPage) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidEmpRelvtDocPage != 0) {
                    try {
                        entEmpRelvtDocPage = PstEmpRelvtDocPage.fetchExc(oidEmpRelvtDocPage);
                    } catch (Exception exc) {
                    }
                }

                frmEmpRelvtDocPage.requestEntityObject(entEmpRelvtDocPage);

                if (frmEmpRelvtDocPage.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entEmpRelvtDocPage.getOID() == 0) {
                    try {
                        long oid = pstEmpRelvtDocPage.insertExc(this.entEmpRelvtDocPage);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {
                        long oid = pstEmpRelvtDocPage.updateExc(this.entEmpRelvtDocPage);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidEmpRelvtDocPage != 0) {
                    try {
                        entEmpRelvtDocPage = PstEmpRelvtDocPage.fetchExc(oidEmpRelvtDocPage);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmpRelvtDocPage != 0) {
                    try {
                        entEmpRelvtDocPage = PstEmpRelvtDocPage.fetchExc(oidEmpRelvtDocPage);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmpRelvtDocPage != 0) {
                    try {
                        long oid = PstEmpRelvtDocPage.deleteExc(oidEmpRelvtDocPage);
                        if (oid != 0) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            default:

        }
        return rsCode;
    }
}
