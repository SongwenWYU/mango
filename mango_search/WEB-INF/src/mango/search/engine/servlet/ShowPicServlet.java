package mango.search.engine.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mango.search.config.PropertyConfig;

public class ShowPicServlet extends HttpServlet{

	private static final String PATH = PropertyConfig.getImagePath();

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = arg0.getParameter("id");
		if (id == null) {
			return;
		}
		
		OutputStream outputStream = arg1.getOutputStream();
		arg1.setContentType("image/jpeg");
		File file = new File(new File(PATH),id);
		InputStream inputStream = new FileInputStream(file);
		
		byte[] bs = new byte[512];
		int length = 512;
		while ((length = inputStream.read(bs)) != -1) {
			outputStream.write(bs,0,length);
		}
		outputStream.flush();
	}
	
}
