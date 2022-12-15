/* 
 * Form Name  	:  FrmRecrIllness.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.recruitment;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.recruitment.*;

public class FrmRecrIllness extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrIllness recrIllness;

	public static final String FRM_NAME_RECRILLNESS		=  "FRM_NAME_RECRILLNESS" ;

	public static final int FRM_FIELD_RECR_ILLNESS_ID			=  0 ;
	public static final int FRM_FIELD_ILLNESS			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_ILLNESS_ID",  "FRM_FIELD_ILLNESS"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmRecrIllness(){
	}
	public FrmRecrIllness(RecrIllness recrIllness){
		this.recrIllness = recrIllness;
	}

	public FrmRecrIllness(HttpServletRequest request, RecrIllness recrIllness){
		super(new FrmRecrIllness(recrIllness), request);
		this.recrIllness = recrIllness;
	}

	public String getFormName() { return FRM_NAME_RECRILLNESS; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrIllness getEntityObject(){ return recrIllness; }

	public void requestEntityObject(RecrIllness recrIllness) {
		try{
			this.requestParam();
			recrIllness.setIllness(getString(FRM_FIELD_ILLNESS));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
