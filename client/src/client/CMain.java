package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;

import server.ServerIF;

public class CMain {
	public static void main(String[] args) throws Exception {
		ServerIF server = null;
		Client client = new Client();

		BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				server = (ServerIF) Naming.lookup("Server");

//				if(token == null) client.showInitMenu();
//				else client.showMenu();

				client.printMenu();
				System.out.print("menu를 선택하세요. (종료하려면 x를 입력합니다.): ");
				String sChoice = objReader.readLine().trim();

				int failNum = 0;
				while (!sChoice.equals("x")) {
					switch (sChoice) {
					case "1":
						client.addNewInsurance(server, objReader);
						break;
					case "2":
						client.approvalNewInsurance(server, objReader);
						break;
					case "3":
						System.out.println("준비중인 메뉴입니다.");
					case "menu":
						client.printMenu();
					default:
						failNum++;
						break;
					}
					if (failNum > Client.RE_PRINT_MENU_COUNT) {
						System.out.println("메뉴를 다시 보고싶다면 menu를 입력하세요.");
						failNum = 0;
					}
					System.out.print("menu를 선택하세요. (종료하려면 x를 입력합니다.): ");
					sChoice = objReader.readLine().trim();
				}
				break;
//			} catch (ConnectException e) {
//				System.out.println("서버에 연결할 수 없습니다. 재시도 중...");
//				Thread.sleep(3000);
//			} catch (NullDataException e) {
//				e.doAction();
//				Thread.sleep(3000);
//			}catch (RemoteException e) {
//				e.printStackTrace();
//				break;
//			}catch (SessionExpireException e) {
//				e.doAction();
//				token = null;
//			}catch (NullTokenException e) {
//				e.doAction();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
