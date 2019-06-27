package kr.co.koo.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import kr.co.koo.jdbc.JdbcUtil;
import kr.co.koo.jdbc.connection.ConnectionProvider;

public class BoardDAO {

	private static BoardDAO instance = new BoardDAO();

	private BoardDAO() {}

	public static BoardDAO getInstance() {
		return instance;
	}

	//게시판 글 정보를 DB에 저장하는 메서드.
	public void write(String bName, String bTitle, String bContent) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "insert into board "
				+ "(bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
				+ "select ?, ?, ?, 0, max(bId)+1, 0, 0 from board";

		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
		}

	}

	//게시판 리스트의 정보를 DB로부터 불러올 메서드 선언.
	public ArrayList<BoardVO> list() {

		ArrayList<BoardVO> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select * from board order by bGroup desc, bStep asc";

		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");

				BoardVO vo 	= new BoardVO(bId, bName, 
						bTitle, bContent, 
						bDate, bHit, bGroup, 
						bStep, bIndent);

				list.add(vo);				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}		
		return list;
	}

	//특정 글번호의 글 내용을 확인하는 메서드.
	public BoardVO contentView(String strId) {
		upHit(strId);
		
		BoardVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select * from board where bId=?";

		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strId));

			rs = pstmt.executeQuery();

			if(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				vo = new BoardVO(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return vo;
	}
	
	//조회수를 상승시키는 메서드
	private void upHit(String bId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "update board set bHit= bHit+1 where bId=?";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
		}
		
	}
	
	//글 내용 삭제 메서드
	public void delete(String bId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "delete from board where bId=?";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
		}
	}
	
	//글 내용 수정 메서드
	public void update(String bId, String bName, String bTitle, String bContent) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "update board set bName=?, "
				+ "bTitle=?, bContent=? where bId=?";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
		}
	}
	
	//답글 내용의 뷰에서 데이터를 얻기 위해 DB로부터 원본글의 데이터를 가져오는 메서드
	public BoardVO replyView(String strId) {
		
		BoardVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "select * from board where bId=?";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strId));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				vo = new BoardVO(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
		return vo;		
	}
	
	//댓글의 내용을 DB에 저장하는 메서드
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent) {
		
		replyShape(bGroup, bStep);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "insert into board "
				+ "(bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) "
				+ "values (?,?,?,0,?,?,?)";
						
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep) + 1);
			pstmt.setInt(6, Integer.parseInt(bIndent) + 1);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
		}
		
	}
	
	//답변의 bStep을 조정하기 위한 메서드
	private void replyShape(String strGroup, String strStep) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "update board set bStep = bStep + 1 where bGroup=? and bStep > ?";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strStep));
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
		}
		
	}
}








