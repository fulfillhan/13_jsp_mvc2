package _09_file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/upload2")
public class Upload2 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private final String FILE_REPOSITORY_PATH = FileConfig.FILE_REPOSITORY_PATH;   
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//String saveDirectory = "D:\\abu\\15_web_share_folder\\workspace\\12_jsp_basic\\src\\main\\webapp\\chapter09_file\\fileRepository\\";
		
		MultipartRequest multipartRequest = new MultipartRequest(request , FILE_REPOSITORY_PATH , 1024 * 1024 * 100 , "utf-8");
	
		Enumeration<?> files = multipartRequest.getFileNames();
		
		while (files.hasMoreElements()) {
			
			String element = (String)files.nextElement();
			
			if (multipartRequest.getOriginalFileName(element) != null) { // 원본파일명이 있으면 > 파일을 업로드했으면
				
				String originalFileName = multipartRequest.getOriginalFileName(element); // 업로드 '한' 파일명을 반환
				
				UUID uuid = UUID.randomUUID(); // UUID.randomUUID(); > 해쉬 생성 메서드
				String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
				
				String fileUUID = uuid + extension;
				
				//System.out.println("originalFileName : " + originalFileName);
				//System.out.println("fileUUID : " + fileUUID);
				//System.out.println();
				
				File originalFile = new File(FILE_REPOSITORY_PATH + originalFileName); // 기존에 업로드한 파일을 읽어옴
				File renameFile = new File(FILE_REPOSITORY_PATH + fileUUID);			// 변환된 파일명으로 새로운 파일을 생성
				originalFile.renameTo(renameFile);								// 기존에 업로드한 파일을 변환된 파일명으로 이름 변경
				
			}
			
		}
		
		String jsScript = """
				<script>
					alert('파일을 업로드 하였습니다.');
					location.href = 'fileMain';
				</script>""";
			
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsScript);
		
		
		
		
	}

}
