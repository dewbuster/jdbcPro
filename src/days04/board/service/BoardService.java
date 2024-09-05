package days04.board.service;

import java.sql.SQLException;
import java.util.ArrayList;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;

// 트랜잭션 처리
public class BoardService {
	
	private BoardDAO dao = null;
	// 1. 생성자 DI
	public BoardService(BoardDAO dao) {
		this.dao = dao;
	}
	// 2. Setter DI
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	
	// 1. 게시글 목록 서비스
	public ArrayList<BoardDTO> selectService(int currentPage, int numberPerPage){
		ArrayList<BoardDTO> list = null;
		// 1. DB 연동 list
		try {
			((BoardDAOImpl)this.dao).getConn().setAutoCommit(false);
			list = this.dao.select(currentPage, numberPerPage);
			// 2. 로그 기록 작업
			System.out.println("> 게시글 목록 : 로그 기록 작업...");
			// 3. 문자/메일 전송
			System.out.println("> 게시글 목록 : 문자/메일 전송 작업...");
			((BoardDAOImpl)this.dao).getConn().commit();
		} catch (SQLException e) {
			try {
				((BoardDAOImpl)this.dao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				((BoardDAOImpl)this.dao).getConn().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 2. 새글 쓰기 서비스
	public int insertService(BoardDTO dto) {
		int rowCount = 0;
		try {
			rowCount = this.dao.insert(dto);
			// 2. 로그 기록 작업
			System.out.println("> 게시글 쓰기 : 로그 기록 작업...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	public BoardDTO viewService(int seq) {
		int rowCount = 0;
		BoardDTO dto = null;
		try {
			// 1. 조회수 증가 처리
			rowCount = this.dao.increaseReaded(seq);			
			// 2. 해당 게시글
			dto = this.dao.view(seq);
			// 2. 로그 기록 작업
			System.out.println("> 게시글 쓰기 : 로그 기록 작업...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	// 3. 삭제 서비스
	public int deleteService(long seq){
		int rowCount = 0;
		try {
			rowCount = this.dao.delete(seq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 2. 로그 기록 작업
		System.out.println("> 게시글 삭제 : 로그 기록 작업...");
		return rowCount;
	}
	
	public int updateService(BoardDTO dto){
		int rowCount = 0;
		try {
			rowCount = this.dao.update(dto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 2. 로그 기록 작업
		System.out.println("> 게시글 업데이트 : 로그 기록 작업...");
		return rowCount;
	}
	
}
