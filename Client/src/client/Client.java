package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import freshInsuranceVisitor.ApprovalVisitor;
import insuranceProduct.InsuranceType;
import insuranceProduct.TermPeriod;
import server.ServerIF;

public class Client {

	public static void main(String[] args) throws NotBoundException {
		ServerIF server;
		BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			server = (ServerIF) Naming.lookup("Server");
			while (true) {
				printMenu();
				String choice = objReader.readLine().trim();
				switch (choice){
					case "1" :
						addNewInsurance(server, objReader);
						break;
					case "2" :
						approvalNewInsurance(server, objReader);
						break;		
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void printMenu() {
		System.out.println("***********************MENU***********************");
		System.out.println("-----상품 개발 부서-----");
		System.out.println("1. 신규 보험 등록");
		System.out.println("-----상품 개발 부서 관리자-----");
		System.out.println("2. 신규 보험 목록");
		System.out.println("-----고객-----");
		System.out.println("3. 보험 가입");
	}
	
	private static void addNewInsurance(ServerIF server, BufferedReader objReader) throws IOException {
		System.out.println("-----신규 보험 등록-----");
		System.out.print("보험 이름: ");
		String insuranceName = objReader.readLine().trim();
		// ----- 보험 타입 -----
		System.out.println("보험 타입을 선택해 주십시오");
        for (InsuranceType type : InsuranceType.values()) {
            System.out.println((type.ordinal() + 1) + ". " + type.name());
        }
        System.out.print("입력 (예: 1, 2, 3): ");
        int typeInput = Integer.parseInt(objReader.readLine().trim()) - 1; // 1을 빼서 0-based index로 만듦

        // 입력된 값으로 InsuranceType 객체 생성
        // exception 처리 보강 예정
        InsuranceType selectedType = InsuranceType.values()[typeInput];
        System.out.println("선택된 보험 타입: " + selectedType.getName());
        // ---------------
        
        // ----- 보험 기간 -----
        System.out.println("보험 기간을 선택해 주십시오");
        for(TermPeriod termPeriod : TermPeriod.values()) {
        	System.out.println((termPeriod.ordinal()+1)+". "+ termPeriod.name());
        }
        System.out.print("입력 (예: 1, 2, 3): ");
        int termInput = Integer.parseInt(objReader.readLine().trim())-1;
        // exception 처리 보강 예정
        TermPeriod selectedTerm = TermPeriod.values()[termInput];
        System.out.println("선택된 보험 기간: "+selectedTerm.getName());
        // ---------------
        
        // ----- 기초서류양식 -----
        LinkedHashMap<String, String> BasicPaperList = new LinkedHashMap<>();
        String input;
        System.out.println("필요 서류 이름과 입력 타입을 입력해 주십시오. (예: key value). 종료하려면 'exit'를 입력하십시오.");

        while (true) {
            input = objReader.readLine();
            if ("exit".equals(input)) {
                break;
            }

            String[] line = input.split(" ");
            if (line.length == 2) {
            	BasicPaperList.put(line[0], line[1]);
            } else {
            	// exception 처리 보강 예정
                System.out.println("잘못된 입력입니다. 'key value' 형식으로 입력해 주세요.");
            }
        }
        
        if (BasicPaperList.isEmpty()) {
            System.out.println("맵이 비어 있습니다.");
        } else {
            System.out.println("입력된 서류이름-타입 쌍:");
            BasicPaperList.forEach((key, value) -> System.out.println(key + ": " + value));
        }
        // ---------------
        System.out.println();
        System.out.println("-----확인-----");
        System.out.println("보험 이름: " + insuranceName);
        System.out.println("선택된 보험 타입: " + selectedType);
        System.out.println("선택된 보험 기간: " + selectedTerm);
        BasicPaperList.forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.println();
        
       System.out.println(server.registerInsuranceProduct(insuranceName, selectedType, selectedTerm, BasicPaperList));
	}
	
	private static void approvalNewInsurance(ServerIF server, BufferedReader objReader) throws NumberFormatException, IOException {
		System.out.println("승인할 신규 보험 번호를 입력하여 주십시오.");
		// 신규보험 목록 출력
		System.out.println(server.getFreshInsuranceList());
		System.out.print("입력 (예: 0, 1, 2): ");
		int selectedInsuranceID = Integer.parseInt(objReader.readLine().trim());
		
		// 특정 보험에서 처리할 수 있는 일 리스트 출력
		// ex_보험 관리자 승인 / 보험 교육 승인
		System.out.println("처리 가능 목록: 원하시는 번호를 입력하여 주십시오.");
		System.out.println(server.getFreshInsuranceProcessList(selectedInsuranceID));
		// 현재 서버에서 보험 승인 강제로 출력 중임(데이터 없음)
		System.out.print("입력 (예: 0, 1, 2): ");
		int selectedTaskIndex = Integer.parseInt(objReader.readLine().trim());
		
		// visitor pattern
		ApprovalVisitor visitor = new ApprovalVisitor(server, objReader);
	    visitor.visitInsuranceApprovalProcess(selectedInsuranceID);
		
	}

}
