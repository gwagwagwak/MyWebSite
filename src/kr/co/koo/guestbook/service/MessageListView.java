package kr.co.koo.guestbook.service;

import java.util.List;

import kr.co.koo.guestbook.model.MessageVO;


public class MessageListView {
	//방명록 글의 총 개수를 저장하는 변수.
	private int messageTotalCount;
	//현재 위치한 페이지번호를 저장하는 변수.
	private int currentPageNumber;
	//방명록 글의 정보가 저장되어 있는 리스트.
	private List<MessageVO> messageList;
	//페이지의 총 숫자를 저장하는 변수
	private int pageTotalCount;
	//한 페이당 글의 숫자의 정보를 저장하는 변수.
	private int messageCountPerPage;
	
	//첫 행과 마지막 행의 범위를 저장하는 변수.
	private int firstRow;
	private int endRow;
	
	//MessageListView 객체 생성시 위의 필드를 초기화하기 위한 생성자.
	public MessageListView(List<MessageVO> messageList, int messageTotalCount, 
			int currentPageNumber, int messageCountPerPage, 
			int startRow, int endRow) {
		this.messageList = messageList;
		this.messageTotalCount = messageTotalCount;
		this.currentPageNumber = currentPageNumber;
		this.messageCountPerPage = messageCountPerPage;
		this.firstRow = startRow;
		this.endRow = endRow;

		calculatePageTotalCount();
	}
	
	//총 페이지의 수를 계산하는 메서드.
	private void calculatePageTotalCount() {
		
		//방명록 글의 총 개수가 0개라면 총 페이지 수에 0을 저장한다.
		if (messageTotalCount == 0) {
			pageTotalCount = 0;
		//글의 총 개수에서 한 페이지당 글의 수를 나눈 몫을 pageTotalCount에 우선 저장.	
		} else {
			pageTotalCount = messageTotalCount / messageCountPerPage;
			
			/*
			 * 글의 총 개수에서 한 페이당 글의 수를 나눴을 경우 나머지가 생겼을 때만
			 * 총 페이지 수를 추가로 1 증가시킴.
			 */
			if (messageTotalCount % messageCountPerPage > 0) {
				pageTotalCount++;
			}
		}
	}

	public int getMessageTotalCount() {
		return messageTotalCount;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public List<MessageVO> getMessageList() {
		return messageList;
	}

	public int getPageTotalCount() {
		return pageTotalCount;
	}

	public int getMessageCountPerPage() {
		return messageCountPerPage;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public boolean isEmpty() {
		return messageTotalCount == 0;
	}
}