package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import insuranceProduct.InsuranceType;
import insuranceProduct.TermPeriod;

public interface ServerIF extends Remote {
	String registerInsuranceProduct(String insuranceName, InsuranceType insuranceType, TermPeriod termPeriod, LinkedHashMap<String,String> BasicPaperList)throws RemoteException;
	String getFreshInsuranceList()throws RemoteException;
	String getFreshInsuranceProcessList(int insuranceId)throws RemoteException;
	String getBasicInsuranceInfo(int insuranceId)throws RemoteException;
	String approvalBasicInsuranceInfo(int insuranceId, boolean approvalStatus)throws RemoteException;
	String getMemberPaperForm(int insuranceId)throws RemoteException;
	String approvalMemberPaperForm(int insuranceId, boolean approvalStatus)throws RemoteException;
	String decideStandardRate(int insuranceId, float standardRate)throws RemoteException;
	String setProductApprovalPaper(int insuranceId, String info)throws RemoteException;
	
}
