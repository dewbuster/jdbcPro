package days04;

import java.sql.Connection;

import com.util.DBConn;

import days04.board.controller.BoardController;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

/**
 *	게시판 구현 (모델 2방식 중 MVC 패턴)
 */
public class Ex01 {

	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller = new BoardController(service);
		
		controller.boardStart();
		/*
		 * 1. 패키지 선언
		 * 	days04.board
		 * 	days04.board.controller
		 * 	days04.board.service
		 * 	days04.board.persistence - DAO // 디비연동 (CRUD)
		 * 	days04.board.domain - DTO, VO
		 * 
		 */
	}

}
