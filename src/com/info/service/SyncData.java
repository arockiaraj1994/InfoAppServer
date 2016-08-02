package com.info.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Calendar;

import org.json.JSONObject;

import com.info.util.ByteUtils;
import com.info.util.MySQLConnectionUtil;
import com.info.util.RandomIDGenerationUtil;

public class SyncData extends Thread {
	protected Socket socket = null;

	public SyncData(Socket clientSocket) {
		this.socket = clientSocket;
	}

	public void run() {
		DataInputStream dataInputStream = null;
		DataOutputStream dataOutputStream = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// models data
		String randomUUIDString = null;
		String fromIp = null;
		String fromUser = null;
		String message = null;
		Date updatedTime = null;
		Calendar calendar = null;
		try {
			dataInputStream = new DataInputStream(socket.getInputStream());
			connection = MySQLConnectionUtil.getInstance().getConnection();
			fromIp = socket.getRemoteSocketAddress().toString();
			fromUser = socket.getRemoteSocketAddress().toString();
			if (dataInputStream.available() > 0) {
				randomUUIDString = RandomIDGenerationUtil.getInstance().getId();
				byte[] data = ByteUtils.getInstance().getBytesFromDataInputStream(dataInputStream);
				String jsonString = (String) ByteUtils.getInstance().deserialize(data);
				JSONObject jsonObject = new JSONObject(jsonString);
				String macAddress = jsonObject.getString("mac_address");
				String userName = jsonObject.getString("user_name");
				String password = jsonObject.getString("user_name");
				String mail = jsonObject.getString("mail");
				String phone = jsonObject.getString("phone");
				String query = " insert into user (iduser, mac_address, user_name, password, mail,phone)"
						+ " values (?, ?, ?, ?, ?, ?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, randomUUIDString);
				preparedStatement.setString(2, macAddress);
				preparedStatement.setString(3, userName);
				preparedStatement.setString(4, password);
				preparedStatement.setString(5, mail);
				preparedStatement.setString(6, phone);
				int result = preparedStatement.executeUpdate();
				System.out.println(result);
			}
			while (true) {
				if (dataInputStream.available() > 0) {
					calendar = Calendar.getInstance();
					randomUUIDString = RandomIDGenerationUtil.getInstance().getId();
					byte[] data = ByteUtils.getInstance().getBytesFromDataInputStream(dataInputStream);
					message  = (String) ByteUtils.getInstance().deserialize(data);
					updatedTime = new Date(calendar.getTime().getTime());
					String query = " insert into test_info (idtest_info, from_ip, from_user, message, updated_time)"
							+ " values (?, ?, ?, ?, ?)";
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, randomUUIDString);
					preparedStatement.setString(2, fromIp);
					preparedStatement.setString(3, fromUser);
					preparedStatement.setString(4, message);
					preparedStatement.setDate(5, updatedTime);
					int result = preparedStatement.executeUpdate();
					System.out.println(result);
				}
				System.gc();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
