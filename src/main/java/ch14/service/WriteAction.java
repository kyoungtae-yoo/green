package ch14.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch14.dao.Board;
import ch14.dao.BoardDao;

public class WriteAction implements CommandProcess {

    @Override
    public String requestPro(HttpServletRequest request, HttpServletResponse response) {
        
        int num=Integer.parseInt(request.getParameter("num"));
        int ref=Integer.parseInt(request.getParameter("ref"));
        int re_level=Integer.parseInt(request.getParameter("re_level"));
        int re_step=Integer.parseInt(request.getParameter("re_step"));
        String pageNum=request.getParameter("pageNum");
        String subject=request.getParameter("subject");
        String writer=request.getParameter("writer");
        String password=request.getParameter("password");
        String content=request.getParameter("content");

        Board board=new Board();
        
        board.setNum(num);
        board.setRef(ref);
        board.setRe_level(re_level);
        board.setRe_step(re_step);
        board.setSubject(subject);
        board.setWriter(writer);
        board.setPassword(password);
        board.setContent(content);
        
        BoardDao bd = BoardDao.getInstance();
        int result  = bd.insert(board);
        
        request.setAttribute("pageNum", pageNum);
        request.setAttribute("result", result);

        return "write";    // writeAction.jsp
    }

}
