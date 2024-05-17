package freshInsuranceVisitor;

import java.rmi.RemoteException;

public interface FreshInsuranceVisitor {

	void visitInsuranceApprovalProcess(int insuranceId) throws RemoteException;
	
}