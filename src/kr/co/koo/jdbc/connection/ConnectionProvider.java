package kr.co.koo.jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionProvider {

	//커넥션 객체를 전달하는 메서드
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		Context ct = null;
		DataSource ds = null;
		
		try {
			ct = new InitialContext();
			ds = (DataSource) ct.lookup("java:comp/env/jdbc/mysql");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return conn;
	}
}
