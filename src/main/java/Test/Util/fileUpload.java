package Test.Util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class fileUpload {

	// 프로젝트내에 파일 저장경로설정 - 섬네일 이미지 불러올곳 , 프로젝트내에 저장되어 웹에서접근 불가능 썸네일생성불가
	//private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
	
	// static폴도는 어플리케이션 접근이 용이함 
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

	
	// 파일 유효성검사 : 확장자 , 파일크기, mime타입
	public static boolean inValidFile(MultipartFile file, List<String> allowedExtensions, long maxSize) {
		String fileName = file.getOriginalFilename();
		String fileExtension = getFileExtension(fileName).toLowerCase();

		// 확장자 검사
		if (!allowedExtensions.contains(fileExtension)) {
			return false; // 확장자가 허용되지않으면 ㄴㄴ
		}

		// 파일 크기검사
		if (file.getSize() > maxSize) {
			return false; // 크기제한
		}
		// 미미타입 검사
		if (!isValidMimeType(file)) {
			return false;
		}
		return true;

	}

	// 파일 확장자 추출 매서드
	private static String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex > 0) {
			return fileName.substring(dotIndex + 1);
		}
		return "";
	}

	// 허용 확장자 검사
	private static boolean isAllowedExtension(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); // 추출
		List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif"); // 목록
		return allowedExtensions.contains(extension);
	}

	// mime타입 검사
	private static boolean isValidMimeType(MultipartFile file) {
		String mimeType = file.getContentType();
		if (mimeType == null) {
			return false;
		}

		List<String> allowedMimeTypes = List.of("image/jpg", "image/jpeg", "image/png", "image/gif");
		return allowedMimeTypes.contains(mimeType); // 검증
	}

	// 파일 저장	
	public static String uploadFile(MultipartFile file) throws IOException {
		// UUID 고유파일명
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		// 파일 저장 static/uploads
		File targetFile = new File(UPLOAD_DIR + fileName);

		// 디렉토리가 없으면 생성
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		// 파일을 해당 경로에 저장
		file.transferTo(targetFile);
		
		String rootPath = System.getProperty("user.dir");  // 현재 작업 중인 디렉토리 경로
		log.info("프로젝트 루트 경로: " + rootPath);

		// 저장된 파일 경로 반환
		return fileName; // 웹에서 접근 가능한 경로

	}
}
