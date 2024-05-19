package freshInsuranceVisitor;

import java.rmi.RemoteException;

public interface FreshInsuranceVisitor {

	public void visitInsuranceApprovalProcess(int insuranceId) throws RemoteException;
	
}