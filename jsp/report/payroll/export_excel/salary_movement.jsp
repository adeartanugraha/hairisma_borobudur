<%-- 
    Document   : salary_movement
    Created on : Dec 13, 2018, 3:54:32 PM
    Author     : IanRizky
--%>
<%@page import="com.dimata.harisma.entity.payroll.SalaryLevelDetail"%>
<%@page import="com.dimata.harisma.entity.payroll.PstSalaryLevelDetail"%>
<%@page import="com.dimata.harisma.entity.payroll.SalaryLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.PstSalaryLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.Value_Mapping"%>
<%@page import="com.dimata.harisma.entity.payroll.PstValue_Mapping"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%@page import="com.dimata.harisma.entity.payroll.PayGeneral"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ include file = "../../../main/javainit.jsp" %>
<%!
       public static String listStringSearch [][]= {
                //period name
                    {
                    "JAN 15",
                    "JAN15",
                    "FEB15",
                    "MAR15",
                    "APR15",
                    "Mei15",
                    "Juni15",
                    "July15",
                    "AUG15",
                    "AUG 2015",
                    "SEP15",
                    "OCT15",
                    "NOV15",
                    "DEC15",
                    "JAN16",
                    "FEB16",
                    "MAR16",
                    "APRIL16",
                    "May16",
                    "Mei16",
                    "JUN16",
                    "JUL16",
                    "AUG16",
                    "SEP16",
                    "OCT16",
                    "NOV16",
                    "Des16",
                    "JAN17",
                    "FEB17",
                    "MAR17",
                    "MEI2017",
                    "MEI17",
                    "APR17",
                    "Juni17",
                    "JUN17",
                    "JUL17",
                    "AUG17",
                    "SEP17",
                    "OKT17",
                    "NOV17",
                    "NO17",
                    "DEC17",
                    "JAN18",
                    "FEB2018",
                    "FEB18",
                    "MAR18",
                    "MAR17",
                    "APRIL18",
                    "MEI18",
                    "JUNE18",
                    "JUNE2018",
                    "JUL18",
                    "AUG18",
                    "SEP18",
                    "OKT18",
                    "NOV18",
                    "NOV8",
                    "DEC18",
                    "DES18"
                }
            ,
            //start date
                {
                   "2014-12-19",
                    "2014-12-19",
                    "2015-01-19",
                    "2015-02-19",
                    "2015-03-19",
                    "2015-04-19",
                    "2015-05-19",
                    "2015-06-19",
                    "2015-07-19",
                    "2015-07-19",
                    "2015-08-19",
                    "2015-09-19",
                    "2015-10-19",
                    "2015-11-19",
                    "2015-12-19",
                    "2016-01-19",
                    "2016-02-19",
                    "2016-03-19",
                    "2016-04-19",
                    "2016-04-19",
                    "2016-05-19",
                    "2016-06-19",
                    "2016-07-19",
                    "2016-08-19",
                    "2016-09-19",
                    "2016-10-19",
                    "2016-11-19",
                    "2016-12-19",
                    "2017-01-19",
                    "2017-02-19",
                    "2017-04-19",
                    "2017-04-19",
                    "2017-03-19",
                    "2017-05-19",
                    "2017-05-19",
                    "2017-06-19",
                    "2017-07-19",
                    "2017-08-19",
                    "2017-09-19",
                    "2017-10-19",
                    "2017-10-19",
                    "2017-11-19",
                    "2017-12-19",
                    "2018-01-19",
                    "2018-01-19",
                    "2018-02-19",
                    "2017-02-19",
                    "2018-03-19",
                    "2018-04-19",
                    "2018-05-19",
                    "2018-05-19",
                    "2018-06-19",
                    "2018-07-19",
                    "2018-08-19",
                    "2018-09-19",
                    "2018-10-19",
                    "2018-10-19",
                    "2018-11-19",
                    "2018-11-19"


                }
            ,
            //end date
                {
                   "2015-01-18",
                    "2015-01-18",
                    "2015-02-18",
                    "2015-03-18",
                    "2015-04-18",
                    "2015-05-18",
                    "2015-06-18",
                    "2015-07-18",
                    "2015-08-18",
                    "2015-08-18",
                    "2015-09-18",
                    "2015-10-18",
                    "2015-11-18",
                    "2015-12-18",
                    "2016-01-18",
                    "2016-02-18",
                    "2016-03-18",
                    "2016-04-18",
                    "2016-05-18",
                    "2016-05-18",
                    "2016-06-18",
                    "2016-07-18",
                    "2016-08-18",
                    "2016-09-18",
                    "2016-10-18",
                    "2016-11-18",
                    "2016-12-18",
                    "2017-01-18",
                    "2017-02-18",
                    "2017-03-18",
                    "2017-05-18",
                    "2017-05-18",
                    "2017-04-18",
                    "2017-06-18",
                    "2017-06-18",
                    "2017-07-18",
                    "2017-08-18",
                    "2017-09-18",
                    "2017-10-18",
                    "2017-11-18",
                    "2017-11-18",
                    "2017-12-18",
                    "2018-01-18",
                    "2018-02-18",
                    "2018-02-18",
                    "2018-03-18",
                    "2017-03-18",
                    "2018-04-18",
                    "2018-05-18",
                    "2018-06-18",
                    "2018-06-18",
                    "2018-07-18",
                    "2018-08-18",
                    "2018-09-18",
                    "2018-10-18",
                    "2018-11-18",
                    "2018-11-18",
                    "2018-12-18",
                    "2018-12-18"


                }
            ,
                {
                    "504404524367701516",
                    "504404524367701516",
                    "504404526100178415",
                    "504404526100314547",
                    "504404526100966287",
                    "504404534640068117",
                    "504404534640121161",
                    "504404534640188624",
                    "504404534640221411",
                    "504404534640221411",
                    "504404537218668105",
                    "504404569979341766",
                    "504404569979251947",
                    "504404569979195127",
                    "504404611545973124",
                    "504404611545976392",
                    "504404611546224111",
                    "504404611546354331",
                    "504404611546466021",
                    "504404611546466021",
                    "504404611546519131",
                    "504404611546607460",
                    "504404611546686197",
                    "504404611547043514",
                    "504404611547132811",
                    "504404611547239329",
                    "504404611547346346",
                    "504404643082749741",
                    "504404643082903566",
                    "504404643083324816",
                    "504404650779343238",
                    "504404650779343238",
                    "504404650779339797",
                    "504404654984886231",
                    "504404654984886231",
                    "504404654985025644",
                    "504404654985192327",
                    "504404654985285717",
                    "504404654986364782",
                    "504404654986474286",
                    "504404654986474286",
                    "504404654986545246",
                    "504404674519355529",
                    "604404643082903566",
                    "604404643082903566",
                    "604404643083324816",
                    "504404643083324816",
                    "604404650779339797",
                    "604404650779343238",
                    "604404654984886231",
                    "604404654984886231",
                    "604404654985025644",
                    "604404654985192327",
                    "604404654985285717",
                    "604404654986364782",
                    "604404654986474286",
                    "604404654986474286",
                    "604404654986545246",
                    "604404654986545246"

                }

            };
 
    
        public static String searchRegexPeriod (PayPeriod periodStart, PayPeriod periodEnd){ 
            String result = "AND (";
            for(int yy = 0 ; yy < listStringSearch[0].length; yy++){
                Date startDate = Formater.formatDate(listStringSearch[1][yy],"yyyy-MM-dd");
                if(startDate.getTime() >= periodStart.getStartDate().getTime() &&  startDate.getTime() <= periodEnd.getEndDate().getTime()){
                    if(result.length() > 5){
                        result+= " OR ";
                    }
                    result += " ( "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+" LIKE '%"+listStringSearch[0][yy].toString()+"%' ) " ;
                }
            }
            result+= " )";
        return result;
        }


        public static String searchRegexPeriod (String periodStart, String periodEnd){ 
            String result = "AND (";
            for(int yy = 0 ; yy < listStringSearch[0].length; yy++){
                Date startDate = Formater.formatDate(listStringSearch[1][yy],"yyyy-MM-dd");
                Date periodStartDate = Formater.formatDate(periodStart,"yyyy-MM-dd");
                Date periodEndDate = Formater.formatDate(periodEnd,"yyyy-MM-dd");
                if(startDate.getTime() >= periodStartDate.getTime() &&  startDate.getTime() <= periodEndDate.getTime()){
                    if(result.length() > 5){
                        result+= " OR ";
                    }
                    result += " ( "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+" LIKE '%"+listStringSearch[0][yy].toString()+"%' ) " ;
                }
            }
            result+= " )";
            if(result.length() <= 7){
            result = "";
            }
        return result;
        }

	public static String getCompany(long oidCompany){
		String returnStr="-";
		if(oidCompany!=0){
			PayGeneral payGeneral =new PayGeneral();
			try{
				payGeneral =PstPayGeneral.fetchExc(oidCompany);
			}catch(Exception exc){

			}
			returnStr = payGeneral.getCompanyName();
		}
		return returnStr;
	}

	public static String getDivision(long oidDivision){
		String returnStr="-";
		if(oidDivision!=0){
			Division division =new Division();
			try{
				division =PstDivision.fetchExc(oidDivision);
			}catch(Exception exc){

			}
			returnStr = division.getDivision();
		}
		return returnStr;
	}

	public static String getDepartment(long oidDepartment){
		String returnStr="-";
		if(oidDepartment!=0){
			Department department =new Department();
			try{
				department =PstDepartment.fetchExc(oidDepartment);
			}catch(Exception exc){

			}
			returnStr = department.getDepartment();
		}
		return returnStr;
	}

	public static String getSection(long oidSection){
		String returnStr="-";
		if(oidSection!=0){
			Section section =new Section();
			try{
				section =PstSection.fetchExc(oidSection);
			}catch(Exception exc){

			}
			returnStr = section.getSection();
		}
		return returnStr;
	}

	public static String getPosition(long oidPosition){
		String returnStr="-";
		if(oidPosition!=0){
			Position position =new Position();
			try{
				position =PstPosition.fetchExc(oidPosition);
			}catch(Exception exc){

			}
			returnStr = position.getPosition();
		}
		return returnStr;
	}

	public static String getLevel(long oidLevel){
		String returnStr="-";
		if(oidLevel!=0){
			Level level =new Level();
			try{
				level =PstLevel.fetchExc(oidLevel);
			}catch(Exception exc){

			}
			returnStr = level.getLevel();
		}
		return returnStr;
	}

	public static String getCategory(long oidCategory){
		String returnStr="-";
		if(oidCategory!=0){
			EmpCategory cat =new EmpCategory();
			try{
				cat =PstEmpCategory.fetchExc(oidCategory);
			}catch(Exception exc){

			}
			returnStr = cat.getEmpCategory();
		}
		return returnStr;
	}

        public static String getMutationType (int imutationType){
		String returnStr="-";
		
			try{
				returnStr = PstValue_Mapping.getRemarkString[imutationType];
			}catch(Exception exc){

			}
		
		return returnStr;
	}

%>
<%
	response.setHeader("Content-Disposition","attachment; filename=Salary_Movement.xls ");
	
	int iCommand = FRMQueryString.requestCommand(request);
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "inp_section_id");
    long payrollGroupId = FRMQueryString.requestLong(request, "payrollGroupId");
    long periodFrom = FRMQueryString.requestLong(request, "periodFrom");
    long periodTo = FRMQueryString.requestLong(request, "periodTo");
	String empNum = FRMQueryString.requestString(request, "emp_num");
    int mutationType = FRMQueryString.requestInt(request, "mutation_type");

	String where = " RESIGNED = 0 ";
	if (companyId > 0){
		where += " AND COMPANY_ID = "+companyId;
	}
	if (divisionId > 0){
		where += " AND DIVISION_ID = "+divisionId;
	}
	if (departmentId > 0){
		where += " AND DEPARTMENT_ID = "+departmentId;
	}
	if (sectionId > 0){
		where += " AND SECTION_ID = "+sectionId;
	}
	if (payrollGroupId > 0){
		where += " AND PAYROLL_GROUP = "+payrollGroupId;
	}
	if (!empNum.equals("")){
		where += " AND EMPLOYEE_NUM IN ("+empNum+")";
	}
	
	
	Vector listEmployee = PstEmployee.list(0, 0, where, "");
	
	String basicCode = "";
	String meritCode = "";
	String gradeAlCode = "";
	try {
		basicCode = PstSystemProperty.getValueByName("BASIC_SALARY_CODE");
		meritCode = PstSystemProperty.getValueByName("MERIT_CODE");
		gradeAlCode = PstSystemProperty.getValueByName("GRADE_ALLOWANCE_CODE");
	} catch (Exception exc){}
	
	String frmCurrency = "#,###";
        
        PayPeriod payPeriodFrom = new PayPeriod();
        PayPeriod payPeriodTo = new PayPeriod();
        try {
            if(periodFrom != 0){
            payPeriodFrom = PstPayPeriod.fetchExc(periodFrom);
            payPeriodTo = PstPayPeriod.fetchExc(periodTo);
            }
        } catch (Exception exc){}
        
%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1" style="border-collapse: collapse">
			<tr>
				<th rowspan="2">No</th>
				<th rowspan="2">Payroll</th>
				<th rowspan="2">Name</th>
				<th colspan="10">Career Path</th>
				<th colspan="5">Salary</th>
			</tr>
			<tr>
				<th>Company</th>
				<th>Division</th>
				<th>Department</th>
				<th>Section</th>
				<th>Position</th>
				<th>Level</th>
				<th>Category</th>
				<th>History From</th>
				<th>History To</th>
                                <th>Mutation Type</th>
				<th>Payroll Period</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Basic Salary</th>
                                <th>Salary Level</th>
			</tr>
			<%
                            int count = 0;
				if (listEmployee.size()>0){
					for (int i=0; i < listEmployee.size();i++){
						Employee emp = (Employee) listEmployee.get(i);
						
						String whereCareer = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+"="+emp.getOID();
                                                if (periodFrom > 0 && periodTo > 0){
                                                    whereCareer += " AND "+PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]
                                                            +" BETWEEN '"
                                                            + Formater.formatDate(payPeriodFrom.getStartDate(), "yyyy-MM-dd")+"' AND '"
                                                            +Formater.formatDate(payPeriodTo.getEndDate(), "yyyy-MM-dd")+"'";
                                                }
						String order = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];
						Vector listCareer = PstCareerPath.list(0, 0, whereCareer, order);
						
						%>
							<%
								Date lastWorkDate = new Date();
								if (listCareer.size()>0){
									for (int x = 0; x < listCareer.size(); x++){
										CareerPath careerPath = (CareerPath) listCareer.get(x);
										lastWorkDate = careerPath.getWorkTo();
										String whereMap = PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_START_DATE]+" BETWEEN '"
													+ Formater.formatDate(careerPath.getWorkFrom(),"yyyy-MM-dd") + "' AND '"
													+ Formater.formatDate(careerPath.getWorkTo(),"yyyy-MM-dd") +"' AND "
													+ PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_EMPLOYEE_ID]+"="+emp.getOID()
													+ " AND "+PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_COMP_CODE]+"='"+basicCode+"'";
                                                                                
                                                                                //add by Eri Yudi 
                                                                                if (mutationType > -1){
                                                                                        whereMap += " AND "+ PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_REMARK] + " = "+mutationType+"";
                                                                                }
                                                                                //end

										String orderMap = PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_START_DATE];
										Vector listValBasic = PstValue_Mapping.list(0, 0, whereMap, orderMap);
										%>
                                                                                <%
                                                                                    
                                                                                 /*
                                                                                 add by Eri Yudi 2021-12-09
                                                                                    penambahan fitur ngambil nilai basic salary lewat salary level yang lama
                                                                                    dikarenan data yang diimport di salary compoenent yang lama tidak masuk 
                                                                                    semua.
                                                                                    
                                                                                 */
                                                                                String whereSalaryLevel = PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+" LIKE '%"+emp.getEmployeeNum()+"%' AND COMP_CODE = '"+basicCode+"' ";
                                                                                
                                                                                String whereRegex = searchRegexPeriod(Formater.formatDate(careerPath.getWorkFrom(),"yyyy-MM-dd"),Formater.formatDate(careerPath.getWorkTo(),"yyyy-MM-dd"));
                                                                                    Vector listSalaryLevelMap = new Vector();
                                                                                    if(whereRegex.length() > 0){
                                                                                             whereSalaryLevel += whereRegex;
                                                                                              listSalaryLevelMap =  PstSalaryLevelDetail.list(0, 0, whereSalaryLevel , ""+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_LEVEL_COM_ID]);

                                                                                    }
                                                                                   

                                                                              
                                                                          
                                                                              
                                                                                   
                                                                               
                                                                                
                                                                                if(listSalaryLevelMap.size() > 0 ){
                                                                                            count++;
                                                                                            SalaryLevelDetail objSalaryLevel = (SalaryLevelDetail) listSalaryLevelMap.get(0);
                                                                                            PayPeriod payPeriod = new PayPeriod();
                                                                                            try {
                                                                                                    for(int yy = 0 ; yy < listStringSearch[0].length ; yy++){
                                                                                                        if(objSalaryLevel.getLevelCode().contains(listStringSearch[0][yy])){
                                                                                                            payPeriod = (PayPeriod) PstPayPeriod.fetchExc(Long.valueOf(listStringSearch[3][yy]));
                                                                                                        }
                                                                                                    }
                                                                                            } catch (Exception exc){}
                                                                                %>
                                                                                    <tr>
										
												<td><%=count%></td>
												<td><%=emp.getEmployeeNum()%></td>
												<td><%=emp.getFullName()%></td>
											
											
											<td><%=getCompany(careerPath.getCompanyId())%></td>
											<td><%=getDivision(careerPath.getDivisionId())%></td>
											<td><%=getDepartment(careerPath.getDepartmentId())%></td>
											<td><%=getSection(careerPath.getSectionId())%></td>
											<td><%=getPosition(careerPath.getPositionId())%></td>
											<td><%=getLevel(careerPath.getLevelId())%></td>
											<td><%=getCategory(careerPath.getEmpCategoryId())%></td>
											<td><%=Formater.formatDate(careerPath.getWorkFrom(), "dd-MMM-yyyy")%></td>
											<td><%=Formater.formatDate(careerPath.getWorkTo(), "dd-MMM-yyyy")%></td>
													
                                                                                                        <td></td>
													<td><%=payPeriod.getPeriod()%></td>
													<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
													<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
                                                                                                        <td style="text-align: center"><%=Formater.formatNumberVer1(Double.parseDouble(objSalaryLevel.getFormula().substring(1)), frmCurrency)%></td>
                                                                                                        <td ><%=objSalaryLevel.getLevelCode()%></td>
												
                                                                                    </tr>
                                                                                
                                                                                <%
                                                                                } else if (listValBasic.size()>0){
                                                                                        count++;
                                                                                            Value_Mapping valMap = (Value_Mapping) listValBasic.get(0);
                                                                                            PayPeriod payPeriod = new PayPeriod();
                                                                                            try {
                                                                                                    payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(valMap.getStartdate());
                                                                                            } catch (Exception exc){}

                                                                                            %> 
                                                                                                        
										<tr>
										
												<td><%=count%></td>
												<td><%=emp.getEmployeeNum()%></td>
												<td><%=emp.getFullName()%></td>
											
											
											<td><%=getCompany(careerPath.getCompanyId())%></td>
											<td><%=getDivision(careerPath.getDivisionId())%></td>
											<td><%=getDepartment(careerPath.getDepartmentId())%></td>
											<td><%=getSection(careerPath.getSectionId())%></td>
											<td><%=getPosition(careerPath.getPositionId())%></td>
											<td><%=getLevel(careerPath.getLevelId())%></td>
											<td><%=getCategory(careerPath.getEmpCategoryId())%></td>
											<td><%=Formater.formatDate(careerPath.getWorkFrom(), "dd-MMM-yyyy")%></td>
											<td><%=Formater.formatDate(careerPath.getWorkTo(), "dd-MMM-yyyy")%></td>
													
											
                                                                                                                <td><%=getMutationType(valMap.getRemark())%></td>
														<td><%=payPeriod.getPeriod()%></td>
														<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
														<td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
														<td style="text-align: center"><%=Formater.formatNumberVer1(valMap.getValue(), frmCurrency)%></td>
                                                                                                                <td></td>
												
										</tr>
													<%
										}else {
                                                                                  count++;
                                                                                %>
                                                                                    <tr>
										
                                                                                        <td><%=count%></td>
                                                                                        <td><%=emp.getEmployeeNum()%></td>
                                                                                        <td><%=emp.getFullName()%></td>
											
											<td><%=getCompany(careerPath.getCompanyId())%></td>
											<td><%=getDivision(careerPath.getDivisionId())%></td>
											<td><%=getDepartment(careerPath.getDepartmentId())%></td>
											<td><%=getSection(careerPath.getSectionId())%></td>
											<td><%=getPosition(careerPath.getPositionId())%></td>
											<td><%=getLevel(careerPath.getLevelId())%></td>
											<td><%=getCategory(careerPath.getEmpCategoryId())%></td>
											<td><%=Formater.formatDate(careerPath.getWorkFrom(), "dd-MMM-yyyy")%></td>
											<td><%=Formater.formatDate(careerPath.getWorkTo(), "dd-MMM-yyyy")%></td>
                                                                                            <td></td>
                                                                                            <td></td>
                                                                                            <td></td>
                                                                                            <td></td>
                                                                                            <td style="text-align: center"></td>
                                                                                            <td></td>
                                                                                <%
                                                                                }
											%>
										<%
                                                                                    if(listSalaryLevelMap.size() > 1 ){
                                                                                            for (int xx=1; xx < listSalaryLevelMap.size(); xx++){
                                                                                            %>
                                                                                                         <tr>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                                                    <td></td>
                                                                                            <%
                                                                                                                    SalaryLevelDetail objSalaryLevelDetail = (SalaryLevelDetail) listSalaryLevelMap.get(xx);
                                                                                                                    PayPeriod payPeriod = new PayPeriod();
                                                                                                                    String startDate = "";
                                                                                                                    String endDate = "";
                                                                                                                    try {
                                                                                                                        for(int yy = 0 ; yy < listStringSearch[0].length ; yy++){
                                                                                                                            if(objSalaryLevelDetail.getLevelCode().contains(listStringSearch[0][yy])){
                                                                                                                                startDate = listStringSearch[1][yy];
                                                                                                                                endDate = listStringSearch[2][yy];
                                                                                                                                payPeriod = (PayPeriod) PstPayPeriod.fetchExc(Long.valueOf(listStringSearch[3][yy]));
                                                                                                                            }
                                                                                                                        }
                                                                                                                    } catch (Exception exc){}

                                                                                            %> 
                                                                                                                    <td>-</td>
                                                                                                                    <td><%=payPeriod.getPeriod()%></td>
                                                                                                                    <td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
                                                                                                                    <td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
                                                                                                                    <td style="text-align: center"><%=Formater.formatNumberVer1(Double.parseDouble(objSalaryLevelDetail.getFormula().substring(1)), frmCurrency)%></td>
                                                                                                                    <td ><%=objSalaryLevelDetail.getLevelCode()%></td>

                                                                                                        </tr>
                                                                                            <%
                                                                                                    }
                                                                                          for (int xx=0; xx < listValBasic.size(); xx++){
                                                                                                %>
                                                                                                 <tr>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                       <td></td>
                                                                                                <%
                                                                                                       Value_Mapping valMap = (Value_Mapping) listValBasic.get(xx);
                                                                                                                       PayPeriod payPeriod = new PayPeriod();
                                                                                                                       try {
                                                                                                                               payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(valMap.getStartdate());
                                                                                                                       } catch (Exception exc){}

                                                                                               %>
                                                                                                                    <td><%=getMutationType(valMap.getRemark())%></td>
                                                                                                                   <td><%=payPeriod.getPeriod()%></td>
                                                                                                                   <td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
                                                                                                                   <td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
                                                                                                                   <td style="text-align: center"><%=Formater.formatNumberVer1(valMap.getValue(), frmCurrency)%></td>
                                                                                                                   <td></td>
                                                                                                               </tr>
                                                                                               <%
                                                                                                   }
                                                                                    }else if (listValBasic.size() > 1){
                                                                                            for (int xx=1; xx < listValBasic.size(); xx++){
                                                                                                    %>
                                                                                                    <tr>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                            <td></td>
                                                                                                    <%
                                                                                                            Value_Mapping valMap = (Value_Mapping) listValBasic.get(xx);
                                                                                                            PayPeriod payPeriod = new PayPeriod();
                                                                                                            try {
                                                                                                                    payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(valMap.getStartdate());
                                                                                                            } catch (Exception exc){}

                                                                                                            %> 
                                                                                                                    <td><%=getMutationType(valMap.getRemark())%></td>
                                                                                                                    <td><%=payPeriod.getPeriod()%></td>
                                                                                                                    <td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
                                                                                                                    <td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
                                                                                                                    <td style="text-align: center"><%=Formater.formatNumberVer1(valMap.getValue(), frmCurrency)%></td>
                                                                                                                    <td></td>
                                                                                                    </tr>
                                                                                                            <%
                                                                                            }
                                                                                    }
									}
									lastWorkDate.setDate(lastWorkDate.getDate() + 1);
									String whereMap = "";
                                                                                                if(periodFrom != 0){
                                                                                                  whereMap =  PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_START_DATE]+" BETWEEN '"
                                                                                                                + Formater.formatDate(lastWorkDate,"yyyy-MM-dd") + "' AND '"
                                                                                                                + Formater.formatDate(payPeriodTo.getEndDate(),"yyyy-MM-dd") +"' AND ";
                                                                                                }else{
                                                                                                   whereMap =  PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_START_DATE]+" BETWEEN '"
                                                                                                                + Formater.formatDate(lastWorkDate,"yyyy-MM-dd") + "' AND '"
                                                                                                                + Formater.formatDate(new Date(),"yyyy-MM-dd") +"' AND ";
                                                                                                }
												whereMap += PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_EMPLOYEE_ID]+"="+emp.getOID()
												+ " AND "+PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_COMP_CODE]+"='"+basicCode+"'";
                                                                           //add by Eri Yudi 
                                                                                if (mutationType > -1){
                                                                                        whereMap += " AND "+ PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_REMARK] + " = "+mutationType+"";
                                                                                }
                                                                                //end

									String orderMap = PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_START_DATE];
									Vector listValBasic = PstValue_Mapping.list(0, 0, whereMap, orderMap);
                                                                        
                                                                    /*
                                                                         add by Eri Yudi 2021-12-09
                                                                            penambahan fitur ngambil nilai basic salary lewat salary level yang lama
                                                                            dikarenan data yang diimport di salary compoenent yang lama tidak masuk 
                                                                            semua.

                                                                         */

                                                                           Vector listSalaryLevelMap = new Vector();
                                                                        String whereSalaryLevel = PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+" LIKE '%"+emp.getEmployeeNum()+"%' AND COMP_CODE = '"+basicCode+"' ";
//                                                                         if (periodFrom > 0 && periodTo > 0){
//                                                                                whereSalaryLevel += searchRegexPeriod(payPeriodFrom,payPeriodTo);
//                                                                                listSalaryLevelMap =  PstSalaryLevelDetail.list(0, 0, whereSalaryLevel , ""+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_LEVEL_COM_ID]);
//                                                                          }else{
                                                                             String whereLevel = searchRegexPeriod(Formater.formatDate(lastWorkDate,"yyyy-MM-dd"),Formater.formatDate(new Date(),"yyyy-MM-dd"));
                                                                                if(whereLevel.length() > 0){
                                                                                    whereSalaryLevel += searchRegexPeriod(Formater.formatDate(lastWorkDate,"yyyy-MM-dd"),Formater.formatDate(new Date(),"yyyy-MM-dd"));
                                                                                    listSalaryLevelMap =  PstSalaryLevelDetail.list(0, 0, whereSalaryLevel , ""+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_LEVEL_COM_ID]);
                                                                                }
//                                                                          }

                                                                           
                                                                if (listSalaryLevelMap.size()>0){
                                                                count++;
                                                                %>
                                                                <tr>
									<td><%=count%></td>
                                                                        <td><%=emp.getEmployeeNum()%></td>
                                                                        <td><%=emp.getFullName()%></td>
									<td><%=getCompany(emp.getCompanyId())%></td>
									<td><%=getDivision(emp.getDivisionId())%></td>
									<td><%=getDepartment(emp.getDepartmentId())%></td>
									<td><%=getSection(emp.getSectionId())%></td>
									<td><%=getPosition(emp.getPositionId())%></td>
									<td><%=getLevel(emp.getLevelId())%></td>
									<td><%=getCategory(emp.getEmpCategoryId())%></td>
									<td><%=Formater.formatDate(lastWorkDate, "dd-MMM-yyyy")%></td>
									<td><%=Formater.formatDate(new Date(), "dd-MMM-yyyy")%></td>

                                                                <% 
                                                                        SalaryLevelDetail objSalaryLevelDetail = (SalaryLevelDetail) listSalaryLevelMap.get(0);
                                                                        PayPeriod payPeriodLevel = new PayPeriod();
                                                                        String startDate = "";
                                                                        String endDate = "";
                                                                        try {
                                                                            for(int yy = 0 ; yy < listStringSearch[0].length ; yy++){
                                                                                if(objSalaryLevelDetail.getLevelCode().contains(listStringSearch[0][yy])){
                                                                                    startDate = listStringSearch[1][yy];
                                                                                    endDate = listStringSearch[2][yy];
                                                                                    payPeriodLevel = (PayPeriod) PstPayPeriod.fetchExc(Long.valueOf(listStringSearch[3][yy]));
                                                                                }
                                                                            }
                                                                        } catch (Exception exc){}

                                                                %> 
                                                                        <td></td>
                                                                        <td><%=payPeriodLevel.getPeriod()%></td>
                                                                        <td><%=Formater.formatDate(payPeriodLevel.getStartDate(), "dd-MMM-yyyy")%></td>
                                                                        <td><%=Formater.formatDate(payPeriodLevel.getEndDate(), "dd-MMM-yyyy")%></td>
                                                                        <td style="text-align: center"><%=Formater.formatNumberVer1(Double.parseDouble(objSalaryLevelDetail.getFormula().substring(1)), frmCurrency)%></td>
                                                                        <td ><%=objSalaryLevelDetail.getLevelCode()%></td>

                                                            </tr>
                                                                
                                                                <% }
                                                                    else if (listValBasic.size()>0){
                                                                    count++;
								%>
								<tr>
									<td><%=count%></td>
                                                                        <td><%=emp.getEmployeeNum()%></td>
                                                                        <td><%=emp.getFullName()%></td>
									<td><%=getCompany(emp.getCompanyId())%></td>
									<td><%=getDivision(emp.getDivisionId())%></td>
									<td><%=getDepartment(emp.getDepartmentId())%></td>
									<td><%=getSection(emp.getSectionId())%></td>
									<td><%=getPosition(emp.getPositionId())%></td>
									<td><%=getLevel(emp.getLevelId())%></td>
									<td><%=getCategory(emp.getEmpCategoryId())%></td>
									<td><%=Formater.formatDate(lastWorkDate, "dd-MMM-yyyy")%></td>
									<td><%=Formater.formatDate(new Date(), "dd-MMM-yyyy")%></td>
                                                                        
									<%
										
											Value_Mapping valMap = (Value_Mapping) listValBasic.get(0);
											PayPeriod payPeriod = new PayPeriod();
											try {
												payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(valMap.getStartdate());
											} catch (Exception exc){}

											%> 
                                                                                                <td><%=getMutationType(valMap.getRemark())%></td>
												<td><%=payPeriod.getPeriod()%></td>
												<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
												<td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
												<td style="text-align: center"><%=Formater.formatNumberVer1(valMap.getValue(), frmCurrency)%></td>
                                                                                                <td></td>
									
								</tr>
											<%
										}
									%>
								<%
                                                                if (listSalaryLevelMap.size() > 1){
                                                                        for (int xx=1; xx < listSalaryLevelMap.size(); xx++){
                                                                        %>
                                                                                     <tr>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                                                <td></td>
                                                                        <%
                                                                                                SalaryLevelDetail objSalaryLevelDetail = (SalaryLevelDetail) listSalaryLevelMap.get(xx);
                                                                                                PayPeriod payPeriod = new PayPeriod();
                                                                                                String startDate = "";
                                                                                                String endDate = "";
                                                                                                try {
                                                                                                    for(int yy = 0 ; yy < listStringSearch[0].length ; yy++){
                                                                                                        if(objSalaryLevelDetail.getLevelCode().contains(listStringSearch[0][yy])){
                                                                                                            startDate = listStringSearch[1][yy];
                                                                                                            endDate = listStringSearch[2][yy];
                                                                                                            payPeriod = (PayPeriod) PstPayPeriod.fetchExc(Long.valueOf(listStringSearch[3][yy]));
                                                                                                        }
                                                                                                    }
                                                                                                } catch (Exception exc){}

                                                                        %> 
                                                                                                <td>-</td>
                                                                                                <td><%=payPeriod.getPeriod()%></td>
                                                                                                <td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
                                                                                                <td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
                                                                                                <td style="text-align: center"><%=Formater.formatNumberVer1(Double.parseDouble(objSalaryLevelDetail.getFormula().substring(1)), frmCurrency)%></td>
                                                                                                <td ><%=objSalaryLevelDetail.getLevelCode()%></td>

                                                                                    </tr>
                                                                        <%
                                                                                }
                                                                                for (int xx=0; xx < listValBasic.size(); xx++){
										%>
                                                                                <tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										<%
											Value_Mapping valMap = (Value_Mapping) listValBasic.get(xx);
											PayPeriod payPeriod = new PayPeriod();
											try {
												payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(valMap.getStartdate());
											} catch (Exception exc){}

											%> 
                                                                                                <td><%=getMutationType(valMap.getRemark())%></td>
												<td><%=payPeriod.getPeriod()%></td>
												<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
												<td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
												<td style="text-align: center"><%=Formater.formatNumberVer1(valMap.getValue(), frmCurrency)%></td>
                                                                                                <td></td>
                                                                                </tr>
											<%
                                                                                }
                                                                                    
                                                                    
                                                                } else 	if (listValBasic.size() > 1){
									for (int xx=1; xx < listValBasic.size(); xx++){
										%>
                                                                                <tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										<%
											Value_Mapping valMap = (Value_Mapping) listValBasic.get(xx);
											PayPeriod payPeriod = new PayPeriod();
											try {
												payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(valMap.getStartdate());
											} catch (Exception exc){}

											%> 
                                                                                                <td><%=getMutationType(valMap.getRemark())%></td>
												<td><%=payPeriod.getPeriod()%></td>
												<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
												<td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
												<td style="text-align: center"><%=Formater.formatNumberVer1(valMap.getValue(), frmCurrency)%></td>
                                                                                                <td></td>
                                                                                </tr>
											<%
                                                                                }
                                                                        }
									
										
								} else {

                                                                    String whereMap  = PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_EMPLOYEE_ID]+"="+emp.getOID()
												+ " AND "+PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_COMP_CODE]+"='"+basicCode+"'";
                                                                    if (periodFrom > 0 && periodTo > 0){
                                                                            whereMap +=  " AND "+ PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_START_DATE]+" BETWEEN '"
												+ Formater.formatDate(payPeriodFrom.getStartDate(),"yyyy-MM-dd") + "' AND '"
												+ Formater.formatDate(payPeriodTo.getEndDate(),"yyyy-MM-dd") +"'  " ;
                                                                    }
									 
                                                                        //add by Eri Yudi 
                                                                        if (mutationType > -1){
                                                                                whereMap += " AND "+ PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_REMARK] + " = "+mutationType+"";
                                                                        }
                                                                        //end


                                                                           /*
                                                                                 add by Eri Yudi 2021-12-09
                                                                                    penambahan fitur ngambil nilai basic salary lewat salary level yang lama
                                                                                    dikarenan data yang diimport di salary compoenent yang lama tidak masuk 
                                                                                    semua.
                                                                                    
                                                                                 */
                                                                                Vector listSalaryLevelCompMap = new Vector();
                                                                                String whereSalaryLevel = PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+" LIKE '%"+emp.getEmployeeNum()+"%' AND COMP_CODE = '"+basicCode+"' ";
                                                                                 if (periodFrom > 0 && periodTo > 0){
                                                                                         String whereLevel = searchRegexPeriod(payPeriodFrom,payPeriodTo);
                                                                                        if(whereLevel.length() > 0){
                                                                                            whereSalaryLevel += whereLevel;
                                                                                             listSalaryLevelCompMap = PstSalaryLevelDetail.list(0, 0, whereSalaryLevel , ""+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_LEVEL_COM_ID]);
                                                                                        }
                                                                                  }
                                                                              
                                                                                   


									String orderMap = PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_START_DATE];
									Vector listValBasic = PstValue_Mapping.list(0, 0, whereMap, orderMap);
                                                                       
                                                              if (listSalaryLevelCompMap.size()>0){
                                                                        count++;
                                                                %>
                                                                        <tr>
										<td><%=count%></td>
										<td><%=emp.getEmployeeNum()%></td>
										<td><%=emp.getFullName()%></td>
										<td><%=getCompany(emp.getCompanyId())%></td>
										<td><%=getDivision(emp.getDivisionId())%></td>
										<td><%=getDepartment(emp.getDepartmentId())%></td>
										<td><%=getSection(emp.getSectionId())%></td>
										<td><%=getPosition(emp.getPositionId())%></td>
										<td><%=getLevel(emp.getLevelId())%></td>
										<td><%=getCategory(emp.getEmpCategoryId())%></td>
										<td><%=Formater.formatDate(emp.getCommencingDate(), "dd-MMM-yyyy")%></td>
										<td><%=Formater.formatDate(new Date(), "dd-MMM-yyyy")%></td>
                                                                               
										<%
											
												SalaryLevelDetail objSalaryLevel = (SalaryLevelDetail) listSalaryLevelCompMap.get(0);
												PayPeriod payPeriod = new PayPeriod();
                                                                                                String startDate = "";
                                                                                                String endDate = "";
												try {
													for(int yy = 0 ; yy < listStringSearch[0].length ; yy++){
                                                                                                            if(objSalaryLevel.getLevelCode().contains(listStringSearch[0][yy])){
                                                                                                                startDate = listStringSearch[1][yy];
                                                                                                                endDate = listStringSearch[2][yy];
                                                                                                                payPeriod = (PayPeriod) PstPayPeriod.fetchExc(Long.valueOf(listStringSearch[3][yy]));
                                                                                                            }
                                                                                                        }
												} catch (Exception exc){}

												%> 
													<td></td>
													<td><%=payPeriod.getPeriod()%></td>
													<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
													<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
                                                                                                        <td style="text-align: center"><%=Formater.formatNumberVer1(Double.parseDouble(objSalaryLevel.getFormula().substring(1)), frmCurrency)%></td>
                                                                                                        <td ><%=objSalaryLevel.getLevelCode()%></td>
										
									</tr>
                                                                <%

                                                                }else if (listValBasic.size()>0){
                                                                        count++;
									%>
                                                                        
									<tr>
										<td><%=count%></td>
										<td><%=emp.getEmployeeNum()%></td>
										<td><%=emp.getFullName()%></td>
										<td><%=getCompany(emp.getCompanyId())%></td>
										<td><%=getDivision(emp.getDivisionId())%></td>
										<td><%=getDepartment(emp.getDepartmentId())%></td>
										<td><%=getSection(emp.getSectionId())%></td>
										<td><%=getPosition(emp.getPositionId())%></td>
										<td><%=getLevel(emp.getLevelId())%></td>
										<td><%=getCategory(emp.getEmpCategoryId())%></td>
										<td><%=Formater.formatDate(emp.getCommencingDate(), "dd-MMM-yyyy")%></td>
										<td><%=Formater.formatDate(new Date(), "dd-MMM-yyyy")%></td>
                                                                               
										<%
											
												Value_Mapping valMap = (Value_Mapping) listValBasic.get(0);
												PayPeriod payPeriod = new PayPeriod();
												try {
													payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(valMap.getStartdate());
												} catch (Exception exc){}

												%> 
													<td><%=getMutationType(valMap.getRemark())%></td>
													<td><%=payPeriod.getPeriod()%></td>
													<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
													<td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
													<td style="text-align: center"><%=Formater.formatNumberVer1(valMap.getValue(), frmCurrency)%></td>
                                                                                                        <td></td>
										
									</tr>
												<%
											}
										%>
									
                                                                        <%
										if (listSalaryLevelCompMap.size() > 1){
                                                                                    for (int xx=1; xx < listSalaryLevelCompMap.size(); xx++){
                                                                                %>
                                                                                             <tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
                                                                                <%
													SalaryLevelDetail objSalaryLevelDetail = (SalaryLevelDetail) listSalaryLevelCompMap.get(xx);
													PayPeriod payPeriod = new PayPeriod();
                                                                                                        String startDate = "";
                                                                                                        String endDate = "";
													try {
                                                                                                            for(int yy = 0 ; yy < listStringSearch[0].length ; yy++){
                                                                                                                if(objSalaryLevelDetail.getLevelCode().contains(listStringSearch[0][yy])){
                                                                                                                    startDate = listStringSearch[1][yy];
                                                                                                                    endDate = listStringSearch[2][yy];
                                                                                                                    payPeriod = (PayPeriod) PstPayPeriod.fetchExc(Long.valueOf(listStringSearch[3][yy]));
                                                                                                                }
                                                                                                            }
													} catch (Exception exc){}
													
                                                                                %> 
                                                                                                        <td></td>
													<td><%=payPeriod.getPeriod()%></td>
													<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
													<td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
                                                                                                        <td style="text-align: center"><%=Formater.formatNumberVer1(Double.parseDouble(objSalaryLevelDetail.getFormula().substring(1)), frmCurrency)%></td>
                                                                                                        <td ><%=objSalaryLevelDetail.getLevelCode()%></td>
										
                                                                                            </tr>
                                                                                <%
                                                                                        }
                                                                                        for (int xx=0; xx < listValBasic.size(); xx++){
                                                                                        %>
                                                                                            <tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
                                                                                        <%
                                                                                            	Value_Mapping valMap = (Value_Mapping) listValBasic.get(xx);
													PayPeriod payPeriod = new PayPeriod();
													try {
														payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(valMap.getStartdate());
													} catch (Exception exc){}
													
													%> 
                                                                                                                <td><%=getMutationType(valMap.getRemark())%></td>
														<td><%=payPeriod.getPeriod()%></td>
														<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
														<td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
														<td style="text-align: center"><%=Formater.formatNumberVer1(valMap.getValue(), frmCurrency)%></td>
                                                                                                                <td></td>
                                                                                                                
                                                                                                </tr>
													<%
                                                                                        }
                                                                                } else if (listValBasic.size() > 1){
											for (int xx=1; xx < listValBasic.size(); xx++){
												%>
                                                                                                <tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												<%
													Value_Mapping valMap = (Value_Mapping) listValBasic.get(xx);
													PayPeriod payPeriod = new PayPeriod();
													try {
														payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(valMap.getStartdate());
													} catch (Exception exc){}
													
													%> 
                                                                                                                <td><%=getMutationType(valMap.getRemark())%></td>
														<td><%=payPeriod.getPeriod()%></td>
														<td><%=Formater.formatDate(payPeriod.getStartDate(), "dd-MMM-yyyy")%></td>
														<td><%=Formater.formatDate(payPeriod.getEndDate(), "dd-MMM-yyyy")%></td>
														<td style="text-align: center"><%=Formater.formatNumberVer1(valMap.getValue(), frmCurrency)%></td>
                                                                                                                <td></td>
                                                                                                </tr>
													<%
											}
										}
								}
					}
				}
			%>
		</table>
    </body>
</html>

