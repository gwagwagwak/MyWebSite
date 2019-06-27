package kr.co.koo.members;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.co.koo.jdbc.JdbcUtil;
import kr.co.koo.jdbc.connection.ConnectionProvider;

public class MemberDAO {
	
	public static final int MEMBER_NONEXISTENT = 0;
	public static final int MEMBER_EXISTENT = 1;
	public static final int JOIN_FAIL = 0;
	public static final int JOIN_SUCCESS = 1;
	
	public static final int LOGIN_OK = 1;
	public static final int LOGIN_PW_NO = 0;
	public static final int LOGIN_ID_NO = -1;
	
	//싱글톤 패턴을 사용하여 객체 생성
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() {
		
	}
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private Connection getConnection() {
		Context ct = null;
		DataSource ds = null;
		Connection conn = null;
		
		try {
			ct = new InitialContext();
			ds = (DataSource) ct.lookup("java:comp/env/jdbc/mysql");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//회원 가입을 처리하는 메서드.
	public int insertMember(MemberVO vo) {
		int regNum = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "insert into members values (?,?,?,?,?,?)";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setTimestamp(5, vo.getrDate());
			pstmt.setString(6, vo.getAddress());
			
			pstmt.executeUpdate();
			
			regNum = MemberDAO.JOIN_SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return regNum;
	}
	
	//이미 가입된 회원인지 검증하는 메서드
	public int confirmId(String id) {
		int regNum = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select id from members where id=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				regNum = MemberDAO.MEMBER_EXISTENT;
			}else {
				regNum = MemberDAO.MEMBER_NONEXISTENT;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}//end finally
		return regNum;		
	}
	
	//로그인의 유효성을 검증하는 메서드 선언.
	public int userCheck(String id, String pw) {
		int loginNum = 0;
		String dbPw;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "select pw from members where id=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbPw = rs.getString("pw");
				if(dbPw.equals(pw)) {
					loginNum = MemberDAO.LOGIN_OK;
				}else {
					loginNum = MemberDAO.LOGIN_PW_NO;
				}
			}else {
				loginNum = MemberDAO.LOGIN_ID_NO;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return loginNum;
	}
	
	//회원의 정보를 가져오는 메서드 선언.
	public MemberVO getMember(String id) {
		
		MemberVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "select * from members where id=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new MemberVO();
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setrDate(rs.getTimestamp("rDate"));
				vo.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return vo;
	}
	
	//회원정보 수정을 처리하는 메서드 선언.
	public int updateMember(MemberVO vo) {
		int updateNum = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "update members set pw=?, email=?, address=? where id=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getAddress());
			pstmt.setString(4, vo.getId());
			
			updateNum = pstmt.executeUpdate();
								
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return updateNum;
	}
	
	//회원 탈퇴를 처리하는 메서드 선언.
	public void deleteMember(String id) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "delete from members where id=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	//전체 회원의 정보를 조회하는 메서드
	public ArrayList<MemberVO> membersAll() {
		
		ArrayList<MemberVO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select * from members";
		
		try {
			
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setrDate(rs.getTimestamp("rDate"));
				vo.setAddress(rs.getString("address"));
				
				list.add(vo);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
		
		return list;
		
	}

}//end class








