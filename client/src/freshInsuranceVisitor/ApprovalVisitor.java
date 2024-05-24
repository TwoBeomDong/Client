package freshInsuranceVisitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;

import client.Client;
import server.ServerIF;

public class ApprovalVisitor implements FreshInsuranceVisitor {

    private boolean approveBasicInsuranceInfo(int insuranceId, ServerIF server, BufferedReader objReader) throws IOException, RemoteException {
        String basicInfo = server.getBasicInsuranceInfo(insuranceId).getMessage();
        System.out.println(basicInfo);
        System.out.println("기본보험 정보를 승인하시겠습니까? (yes/no)");
        String approvalInput;
        while(true) {
        	approvalInput = objReader.readLine();
            if ("yes".equalsIgnoreCase(approvalInput)) {
                String response = server.approvalBasicInsuranceInfo(insuranceId, true).getMessage();
                System.out.println(response);
                return true;
            }else if("no".equalsIgnoreCase(approvalInput)) {
                String response = server.approvalBasicInsuranceInfo(insuranceId, false).getMessage();
//                System.out.println(response);
                System.out.println("보험 승인이 정상적으로 거부되었습니다.");
                return false;
            }else {
            	System.out.print("올바르지 않은 입력입니다. 다시 입력하세요 : ");
            }
        }
    }

    private boolean approveMemberPaperForm(int insuranceId, ServerIF server, BufferedReader objReader) throws IOException, RemoteException {
        String paperFormInfo = server.getMemberPaperForm(insuranceId).getMessage();
        System.out.println(paperFormInfo);
        System.out.println("기초서류양식을 승인하시겠습니까? (yes/no)");
        String approvalInput;
        while(true) {
        	approvalInput = objReader.readLine();
            if ("yes".equalsIgnoreCase(approvalInput)) {
                String response = server.approvalMemberPaperForm(insuranceId, true).getMessage();
                System.out.println(response);
                return true;
            }else if("no".equalsIgnoreCase(approvalInput)) {
                String response = server.approvalMemberPaperForm(insuranceId, false).getMessage();
                System.out.println(response);
                return false;
            }else {
            	System.out.println("올바르지 않은 입력입니다. 다시 입력하세요 : ");
            }
        }
    }

    private void decideStandardRate(int insuranceId, ServerIF server, BufferedReader objReader) throws IOException, RemoteException {
        System.out.print("보험 요율을 입력해 주십시오:");
        float rate;
		while(true) {
			try {
				rate = Float.parseFloat(objReader.readLine()); 		
				break;
			}catch(NumberFormatException e){
				System.out.print("올바르지 않은 입력입니다. 다시 입력하세요: ");
			}			
		}
        String response = server.decideStandardRate(insuranceId, rate).getMessage();
        System.out.println(response);
    }

    private void setProductApprovalPaper(int insuranceId, ServerIF server, BufferedReader objReader) throws IOException, RemoteException {
        System.out.println("상품 인가 품의서 정보를 입력해 주십시오:");
        String info = objReader.readLine();
        String response = server.setProductApprovalPaper(insuranceId, info).getMessage();
        System.out.println(response);
    }

	@Override
	public void visitInsuranceApprovalProcess(ServerIF server, BufferedReader objReader) throws IOException, RemoteException {
		// 신규보험 목록 출력
		String insuranceList = server.getFreshInsuranceList().getMessage();
		if(insuranceList.equals("")) {
			System.out.println("신규 보험 목록이 비어있습니다.");
			return;
		}
		System.out.println(insuranceList);
		System.out.println("처리할 신규 보험 번호를 입력하여 주십시오.");
		System.out.print("입력 (예: 0, 1, 2): ");
		// 신규 보험 개수도 알아야 한다.
		int selectedInsuranceID = Client.getInputInteger(objReader, Client.SCOPE_NONE);

		// 특정 보험에서 처리할 수 있는 일 리스트 출력
		// ex_보험 관리자 승인 / 보험 교육 승인
		System.out.println("처리 가능 목록: 원하시는 번호를 입력하여 주십시오.");
		System.out.println(server.getFreshInsuranceProcessList(selectedInsuranceID).getMessage());
		// 현재 서버에서 보험 승인 강제로 출력 중임(데이터 없음)
		System.out.print("입력 (예: 0, 1, 2): ");
		int selectedTaskIndex = Client.getInputInteger(objReader, Client.SCOPE_NONE);

        try {
            // 기본보험 정보 승인
            if(!this.approveBasicInsuranceInfo(selectedInsuranceID, server, objReader)) return;

            // 기초서류양식 승인
            if(!this.approveMemberPaperForm(selectedInsuranceID, server, objReader)) return;

            // 요율 결정
            this.decideStandardRate(selectedInsuranceID, server, objReader);

            // 상품 인가 품의서 입력 및 저장
            this.setProductApprovalPaper(selectedInsuranceID, server, objReader);

            System.out.println("승인이 완료되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
	}
}
