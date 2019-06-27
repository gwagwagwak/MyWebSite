package kr.co.koo.guestbook.service;

import java.sql.Connection;
import java.sql.SQLException;

import kr.co.koo.guestbook.model.MessageDAO;
import kr.co.koo.guestbook.model.MessageVO;
import kr.co.koo.jdbc.JdbcUtil;
import kr.co.koo.jdbc.connection.ConnectionProvider;



public class WriteMessageService {
	private static WriteMessageService instance = new WriteMessageService();

	public static WriteMessageService getInstance() {
		return instance;
	}

	private WriteMessageService() {
	}

	public void write(MessageVO message) {
		
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDAO messageDAO = MessageDAO.getInstance();
			messageDAO.insert(conn, message);
		} catch (SQLException e) {
			throw new ServiceException(
					"메시지 등록 실패: " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}