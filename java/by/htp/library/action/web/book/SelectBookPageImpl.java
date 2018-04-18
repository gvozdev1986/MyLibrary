package by.htp.library.action.web.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import by.htp.library.action.BaseAction;
import by.htp.library.action.util.NoDataReply;
import by.htp.library.constants.ResponseSetting;
import by.htp.library.dao.BookDao;
import by.htp.library.dao.impl.BookDaoMySql;
import by.htp.library.service.model.Book;
import by.htp.library.service.model.LimitPage;

public class SelectBookPageImpl implements BaseAction {

	private BookDao dao = new BookDaoMySql();	

	@Override
	public void doHttpReqRes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int start = Integer.valueOf(request.getParameter("start"));
		int end = Integer.valueOf(request.getParameter("end"));
		
		LimitPage limitPage = new LimitPage(); 
		limitPage.setStart(start); 
		limitPage.setEnd(end); 

		Map<String, List<Book>> books = dao.findBookPage(limitPage);

		PrintWriter out = response.getWriter();

		if (books != null) {
			String json = new Gson().toJson(books);
			response.setContentType(ResponseSetting.CONTENT_TYPE_JSON);
			response.setCharacterEncoding(ResponseSetting.CHARACTER_ENCODING);
			response.getWriter().write(json);
		} else {
			NoDataReply noDataReply = new NoDataReply();
			String answer = noDataReply.noDataReceived("SelectBookPageImpl", "BookDao");
			out.print(answer);
			out.close();
		}

	}

}
