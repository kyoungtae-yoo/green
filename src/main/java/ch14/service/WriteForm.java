package ch14.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch14.dao.Board;
import ch14.dao.BoardDao;

public class WriteForm implements CommandProcess {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) {
        String pageNum = request.getParameter("pageNum");
        int num = 0, ref = 0, re_level = 0, re_step = 0;
        num = Integer.parseInt(request.getParameter("num"));

        if (num != 0) {  // 답변글, num=0은 처음쓴글
            BoardDao bd = BoardDao.getInstance();
            Board board = bd.select(num);
            ref = board.getRef();
            re_level = board.getRe_level();
            re_step  = board.getRe_step();
        }
        
        request.setAttribute("pageNum", pageNum);
        request.setAttribute("num", num);
        request.setAttribute("ref", ref);
        request.setAttribute("re_level", re_level);
        request.setAttribute("re_step", re_step);

        return "writeForm";   // writeForm.jsp
    }

}
