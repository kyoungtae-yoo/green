package ch14.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteForm implements CommandProcess {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) {
        String pageNum = request.getParameter("pageNum");
        int num = Integer.parseInt(request.getParameter("num"));
        
        request.setAttribute("pageNum", pageNum);
        request.setAttribute("num", num);

        return "deleteForm";
    }

}
