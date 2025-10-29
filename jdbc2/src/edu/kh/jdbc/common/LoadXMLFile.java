package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class LoadXMLFile {

	public static void main(String[] args) {
		
		// XML 파일 읽어오기 (FileInputStream, Properties)
		
		FileInputStream fis = null;
		Connection conn = null;
		
		try {
			Properties prop = new Properties();
			
			// drivdr.xml 파일을 읽기위한 InputStream 객체 생성
			fis = new FileInputStream("driver.xml");
			
			// 연결된 driver.xml 파일에 있는
			// 내용을 모두 읽어와
			// Properties 객체에 K:V 형식으로 저장
			prop.loadFromXML(fis);
			// key      : value
			// driver   : oracle.jdbc.driver.OracleDriver
			// url      : jdbc:oracle:thin:@localhost:1521:XE
			// userName : kh_yhj
			// password : kh1234
			
			// prop.getProperty("key") : key가 일치하는 속성값(value)을 얻어옴
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName, password);
			
			System.out.println(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(fis != null) fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

}
