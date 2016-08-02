package com.info.service;

import java.net.ServerSocket;
import java.net.Socket;

public class SaveDataTuto {
	static final int PORT = 8089;
	public static void main(String a[]) throws Exception {
		ServerSocket serverSocket = new ServerSocket(8010);
		Socket socket = null;
		try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            e.printStackTrace();

        }
		while (true) {
			System.out.println("Server Running");
	        try {
	            socket = serverSocket.accept();
	        } catch (Exception e) {
	            System.out.println("I/O error: " + e);
	        }
	        new SyncData(socket).start();
	        System.gc();
	    }
		/*serverSocket = new ServerSocket(8088);
		socket = serverSocket.accept();
		connection = MySQLConnectionUtil.getInstance().getConnection();
		fromIp = socket.getRemoteSocketAddress().toString();
		fromUser = socket.getRemoteSocketAddress().toString();
		dataInputStream = new DataInputStream(socket.getInputStream());
		while(true){
			if(dataInputStream.available() > 0){
				message = dataInputStream.readUTF();
				calendar = Calendar.getInstance();
				randomUUIDString = RandomIDGenerationUtil.getInstance().getId();
				updatedTime = new Date(calendar.getTime().getTime());
				String query = " insert into test_info (idtest_info, from_ip, from_user, message, updated_time)"
						+ " values (?, ?, ?, ?, ?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, randomUUIDString);
				preparedStatement.setString(2, fromIp);
				preparedStatement.setString(3, fromUser);
				preparedStatement.setString(4, message);
				preparedStatement.setDate(5, updatedTime);
				int sdf = preparedStatement.executeUpdate();
				System.out.println(sdf);
			}
			System.gc();
		}*/
	}
}
