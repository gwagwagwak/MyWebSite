package kr.co.koo.guestbook.service;

import java.sql.Connection;
import java.sql.SQLException;

import kr.co.koo.guestbook.model.MessageDAO;
import kr.co.koo.guestbook.model.MessageVO;
import kr.co.koo.jdbc.JdbcUtil;
import kr.co.koo.jdbc.connection.ConnectionProvider;



public class DeleteMessageService {

	private static DeleteMessageService instance = new DeleteMessageService();

	public static DeleteMessageService getInstance() {
		return instance;
	}

	private DeleteMessageService() {
	}

	public void deleteMessage(int messageId, String password) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);//트랜잭션 시작

			MessageDAO messageDao = MessageDAO.getInstance();
			MessageVO message = messageDao.select(conn, messageId);
			if (message == null) {
				throw new MessageNotFoundException("메시지 없음");
			}
			if (!message.matchPassword(password)) {
				throw new InvalidPassowrdException("bad password");
			}
			messageDao.delete(conn, messageId);

			conn.commit();
		} catch (SQLException ex) {
			JdbcUtil.rollback(conn);
			throw new ServiceException("삭제 실패:" + ex.getMessage(), ex);
		} catch (InvalidPassowrdException | MessageNotFoundException ex) {
			JdbcUtil.rollback(conn);
			throw ex;
		} finally {
			JdbcUtil.close(conn);
		}
	}
}