package freshInsuranceVisitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;

import server.ServerIF;

public class ApprovalVisitor implements FreshInsuranceVisitor {

    private ServerIF server;
    private BufferedReader objReader;
    
    public ApprovalVisitor(ServerIF server, BufferedReader objReader) {
        this.server = server;
        this.objReader = objReader;
    }

    @Override
    public void visitInsuranceApprovalProcess(int insuranceId) throws RemoteException {
        try {
            // 기본보험 정보 승인
            approveBasicInsuranceInfo(insuranceId);

            // 기초서류양식 승인
            approveMemberPaperForm(insuranceId);

            // 요율 결정
            decideStandardRate(insuranceId);

            // 상품 인가 품의서 입력 및 저장
            setProductApprovalPaper(insuranceId);

            System.out.println("승인이 완료되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void approveBasicInsuranceInfo(int insuranceId) throws IOException, RemoteException {
        String basicInfo = server.getBasicInsuranceInfo(insuranceId).getMessage();
        System.out.println(basicInfo);
        System.out.println("기본보험 정보를 승인하시겠습니까? (yes/no)");
        String approvalInput = objReader.readLine();
        if ("yes".equalsIgnoreCase(approvalInput)) {
            String response = server.approvalBasicInsuranceInfo(insuranceId, true).getMessage();
            System.out.println(response);
        }
    }

    private void approveMemberPaperForm(int insuranceId) throws IOException, RemoteException {
        String paperFormInfo = server.getMemberPaperForm(insuranceId).getMessage();
        System.out.println(paperFormInfo);
        System.out.println("기초서류양식을 승인하시겠습니까? (yes/no)");
        String approvalInput = objReader.readLine();
        if ("yes".equalsIgnoreCase(approvalInput)) {
            String response = server.approvalMemberPaperForm(insuranceId, true).getMessage();
            System.out.println(response);
        }
    }

    private void decideStandardRate(int insuranceId) throws IOException, RemoteException {
        System.out.print("보험 요율을 입력해 주십시오:");
        float rate = Float.parseFloat(objReader.readLine());
        String response = server.decideStandardRate(insuranceId, rate).getMessage();
        System.out.println(response);
    }

    private void setProductApprovalPaper(int insuranceId) throws IOException, RemoteException {
        System.out.println("상품 인가 품의서 정보를 입력해 주십시오:");
        String info = objReader.readLine();
        String response = server.setProductApprovalPaper(insuranceId, info).getMessage();
        System.out.println(response);
    }
}
