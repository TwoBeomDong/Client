package freshInsuranceVisitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;

import server.ServerIF;

public interface FreshInsuranceVisitor {
	public void visitInsuranceApprovalProcess(ServerIF server, BufferedReader objReader) throws IOException, RemoteException;
}