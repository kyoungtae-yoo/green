package ch14.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.*;
import javax.sql.DataSource;
public class BoardDao { // Data Access Object
	// single ton
	private static BoardDao instance = new BoardDao();
	private BoardDao() {}
	public static BoardDao getInstance() {
		return instance;		
	}
	// DataBase Connection Pool
	private Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		}catch (Exception e) {
			System.out.println("연결에러 : "+e.getMessage());
		}
		return conn;
	}

//	public List<Board> list() {
    public List<Board> list(int startRow, int ROW_PER_PAGE) {
		List<Board> list = new ArrayList<Board>();
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String sql = "select * from board1 order by num desc";
//		String sql = "select * from board1 order by ref desc, re_step";
		String sql = "select * from board1 order by ref desc, re_step "
		           + "offset ? rows fetch next ? rows only";
		try {
			pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, ROW_PER_PAGE);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setWriter(rs.getString("writer"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setPassword(rs.getString("password"));
				board.setRef(rs.getInt("ref"));
				board.setRe_step(rs.getInt("re_step"));
				board.setRe_level(rs.getInt("re_level"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setDel(rs.getString("del"));
				
				list.add(board);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {	}
		}
		return list;
	}
	public int insert(Board board) {
		int result  = 0;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// num을 1씩 증가
		String sql2 = "select nvl(max(num),0) + 1 from board1";
		String sql="insert into board1 values(?,?,?,?,0,?,?,?,?,sysdate,'n')";
		String sqlUp =
			"update board1 set re_step=re_step+1 where ref=? and re_step > ?";
		try { // 가장 큰번호 + 1, 입력할 번호 num 생성
			pstmt = conn.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			rs.next();
			int number = rs.getInt(1); 
			pstmt.close();
			if (board.getNum() != 0) {  // 답변글, 읽은글 번호
				pstmt = conn.prepareStatement(sqlUp);
				pstmt.setInt(1, board.getRef());
				pstmt.setInt(2, board.getRe_step());
				pstmt.executeUpdate();
				pstmt.close();
				board.setRe_level(board.getRe_level()+1);
				board.setRe_step(board.getRe_step()+1);
			} else board.setRef(number); // 답변글이 아닐 때는 num과 ref가 같다
			// 입력
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getSubject());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getPassword());
			// 답변글
			pstmt.setInt(6, board.getRef());
			pstmt.setInt(7, board.getRe_step());
			pstmt.setInt(8, board.getRe_level());
			
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {	}
		}
		return result;
	}
	public void readCountUpdate(int num) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql="update board1 set readcount = readcount + 1 where num=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {	}
		}
	}
	public Board select(int num) {
		Board board = new Board();
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from board1 where num=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				board.setNum(rs.getInt("num"));
				board.setWriter(rs.getString("writer"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setPassword(rs.getString("password"));
				board.setRef(rs.getInt("ref"));
				board.setRe_step(rs.getInt("re_step"));
				board.setRe_level(rs.getInt("re_level"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setDel(rs.getString("del"));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {	}
		}
		return board;
	}
//	                      입력한 내용
	public int update(Board board) {
		int result = 0;
		Board board2 = select(board.getNum());  // 현재 table에 있는 내용
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql="update board1 set subject=?,content=? where num=?";
		if (board.getPassword().equals(board2.getPassword())) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getSubject());
				pstmt.setString(2, board.getContent());
				pstmt.setInt(3, board.getNum());
				result = pstmt.executeUpdate();
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null)  conn.close();
				}catch (Exception e) {	}
			}
		} else result = -1; // 암호가 다르다
		return result;
	}
	public int delete(int num,String password) {
		int result = 0;
		Board board2 = select(num);  // 현재 table에 있는 내용
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql="update board1 set del='y' where num=?";
		if (password.equals(board2.getPassword())) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null)  conn.close();
				}catch (Exception e) {	}
			}
		} else result = -1; // 암호가 다르다
		return result;		
	}
	public int getTotal() {
		int total = 0;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from board1";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {	}
		}
		return total;
	}
}