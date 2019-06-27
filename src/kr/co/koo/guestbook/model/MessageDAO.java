package kr.co.koo.guestbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.co.koo.jdbc.JdbcUtil;



public class MessageDAO {
	
	private static MessageDAO messageDAO = new MessageDAO();
	public static MessageDAO getInstance() {
		return messageDAO;
	}
	
	private MessageDAO() {}
	
	//방명록에 새 글을 데이터베이스에 추가하는 기능.
	public int insert(Connection conn, MessageVO message) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
					"insert into guestbook_message " + 
					"(guest_name, password, message) values (?, ?, ?)");
			pstmt.setString(1, message.getGuestName());
			pstmt.setString(2, message.getPassword());
			pstmt.setString(3, message.getMessage());
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	//방명록 글번호를 기준으로 해당 글의 정보를 DB로부터 가져오는 기능.
	public MessageVO select(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from guestbook_message where message_id = ?");
			pstmt.setInt(1, messageId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return makeMessageFromResultSet(rs);
			} else {
				return null;
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	/*
	 * ResultSet을 이용하여 DB에서 검색한 값들을 가져와
	 * MessageVO 객체에 담아 전달해주는 기능.
	 */
	private MessageVO makeMessageFromResultSet(ResultSet rs) throws SQLException {
		MessageVO message = new MessageVO();
		message.setId(rs.getInt("message_id"));
		message.setGuestName(rs.getString("guest_name"));
		message.setPassword(rs.getString("password"));
		message.setMessage(rs.getString("message"));
		return message;
	}
	
	//방명록의 전체 글 개수를 데이터베이스로부터 가져오는 기능.
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from guestbook_message");
			rs.next();
			
			/*
			 * ResultSet으로 getInt() 메서드 매개값에 1을 넣어주면
			 * 카운트 결과를 가져올 수 있음.
			 */
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	//방명록에서 특정 글의 목록을 리스트 형태로 제공하는 기능
	public List<MessageVO> selectList(Connection conn, int firstRow, int endRow) 
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			/*
			 * limit Query문 설명: 방명록 테이블에서 모든 정보를 내림차순으로
			 * 정렬한 뒤에 limit를 사용하여 검색할 범위를 지정함.
			 * ex) limit 0,3 -> 첫번째 행부터 아래의 총 3개의 데이터를 검색하라.
			 *     (행의 인덱스는 0번부터 시작)
			 *     limit 3,3 -> 4번째 행부터 아래의 총 3개의 데이터를 검색하라.
			 *     limit 0,10 -> 첫번째 행부터 아래의 10개의 데이터를 검색하라.
			 */
			pstmt = conn.prepareStatement(
					"select * from guestbook_message " + 
					"order by message_id desc limit ?, ?");
			/*
			 * 사용자가 1페이지를 보여달라는 요청을 했을시 service에서 넘어오는 값이
			 * 1,3이고 2페이지 요청시 넘어오는 값이 4, 6이므로 위의 limit 범위를 사용하기 위해
			 * 아래와 같은 로직으로 ?값을 할당.
			 */
			pstmt.setInt(1, firstRow - 1);
			pstmt.setInt(2, endRow - firstRow + 1);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				List<MessageVO> messageList = new ArrayList<MessageVO>();
				do {
					/*
					 * DB 검색결과를 ResultSet을 이용하여 VO클래스에 담은뒤
					 * do~while 문을 이용하여 방명록의 정보를 리스트에 반복하여 저장.
					 */
					messageList.add(makeMessageFromResultSet(rs));
				} while (rs.next());
				//위 로직이 성공적으로 수행됐을 시 해당 리스트를 반환.
				return messageList;
			} else {
				//DB select 검색 결과가 없을시 비어있는 리스트를 반환.
				return Collections.emptyList();
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	//글 번호를 전달받아 해당 글을 DB에서 삭제하는 기능.
	public int delete(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
					"delete from guestbook_message where message_id = ?");
			pstmt.setInt(1, messageId);
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

}
