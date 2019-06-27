package kr.co.koo.guestbook.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import kr.co.koo.guestbook.model.MessageDAO;
import kr.co.koo.guestbook.model.MessageVO;
import kr.co.koo.jdbc.JdbcUtil;
import kr.co.koo.jdbc.connection.ConnectionProvider;



public class GetMessageListService {
	private static GetMessageListService instance = new GetMessageListService();

	public static GetMessageListService getInstance() {
		return instance;
	}

	private GetMessageListService() {
	}

	private static final int MESSAGE_COUNT_PER_PAGE = 3;
	
	/*
	 * 사용자가 요청한 페이지 번호를 기반으로 해당 페이지의 글 목록을
	 * 가져오는 요청 처리를 하는 서비스의 메서드.
	 */
	public MessageListView getMessageList(int pageNumber) {
		Connection conn = null;
		int currentPageNumber = pageNumber;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDAO messageDao = MessageDAO.getInstance();
			
			/*
			 * DAO가 가지고있는 글의 총 개수를 가져오는 메서드를 호출하여
			 * messageTotalCount 변수에 그 값을 저장.
			 */
			int messageTotalCount = messageDao.selectCount(conn);
			
			//게시글에 대한 정보를 담을 리스트 생성.
			List<MessageVO> messageList = null;
			int firstRow = 0;
			int endRow = 0;
			
			//방명록에 글이 1개 이상 있을 경우
			if (messageTotalCount > 0) {
				/*
				 * firstRow와 endRow는 요청받은 페이지에 따른 글 목록을
				 * 보여주기 위한 범위에 대한 변수입니다.
				 * 예를 들어 1페이지를 요청하면 DB의 1행부터 3행의 글을 가져와야 하며
				 * 2페이지를 요청했을 경우 4행부터 6행의 글을 가져와야 하기 때문에 
				 * 해당 식을 적용.
				 */
				firstRow =
						(pageNumber - 1) * MESSAGE_COUNT_PER_PAGE + 1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE - 1;
				
				/*
				 * 행의 범위를 전달하여 DAO를 통해 DB에 접근해서 해당 글들의 정보를
				 * 가져와 리스트에 저장.
				 */
				messageList =
						messageDao.selectList(conn, firstRow, endRow);
			} else {// 방명록에 글이 1개도 없을 경우
				currentPageNumber = 0; //현재 페이지번호를 0으로 설정하고
				messageList = Collections.emptyList(); //비어 있는 리스트를 messageList에 저장.
			}
			/*
			 * 위 로직이 정상적으로 실행되었을 시에 방명록의 글 목록에 관한 정보들을
			 * 담고 있는 MessageListView에 DB에서 가져온 정보들을 전달한다.
			 */			
			return new MessageListView(messageList,
					messageTotalCount, currentPageNumber,
					MESSAGE_COUNT_PER_PAGE, firstRow, endRow);
		} catch (SQLException e) {
			throw new ServiceException("목록 구하기 실패: " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}