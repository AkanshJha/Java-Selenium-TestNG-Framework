package com.appname.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtilsTest {

	public static void createJSONFile() {
		JSONObject userDetails = new JSONObject();

		// First User Details
		userDetails.put("firstName", "Akansh");
		userDetails.put("lastName", "Jha");
		userDetails.put("Position", "Test Engineer");

		// Creating an object if this user
		JSONObject userDetail = new JSONObject();
		userDetail.put("User 1", userDetails);

		JSONObject userDetails2 = new JSONObject();

		// Second User Details
		userDetails2.put("firstName", "Avinash");
		userDetails2.put("lastName", "Sharma");
		userDetails2.put("Position", "Manager");

		JSONObject user2Detail = new JSONObject();
		user2Detail.put("User 2", userDetails2);

		JSONArray userList = new JSONArray();
		userList.add(userDetail);
		userList.add(user2Detail);

		// Files.write(Paths.get(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\TestJSON.json"),
		// userList.toJSONString().getBytes());
		String jsonFilePath = System.getProperty("user.dir") + "\\configurations\\TestJSON.json";
		System.out.println(jsonFilePath);

		/*
		 * // Files.write(Paths.get(jsonFilePath), userList.toJSONString().getBytes());
		 * // Files.write(userList.toJSONString().getBytes(), Paths.get(jsonFilePath));
		 * try { Files.write(userList.toJSONString().getBytes(), new
		 * File(jsonFilePath)); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(jsonFilePath);
			pw.write(userList.toJSONString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	public static void readJSONFile(String filePath) {
		Object ob = null;
		try {
			 ob = new JSONParser().parse(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject job = (JSONObject) ob;
	job.get(ob);
	}
}
