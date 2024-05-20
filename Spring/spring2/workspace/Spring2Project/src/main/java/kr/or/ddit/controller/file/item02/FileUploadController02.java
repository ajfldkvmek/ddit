package kr.or.ddit.controller.file.item02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.service.IItemservice2;
import kr.or.ddit.vo.Item2;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/item2")
@Slf4j
public class FileUploadController02 {

	/*
	 * 	13장 파일 업로드
	 * 
	 * 
	 * 	3. 여러개의 이미지 업로드
	 * 	- 한 번에 여러개의 이미지를 업로드하는 파일 업로드 기능을 구현한다.
	 * 			
	 * 
	 * 
	 * 		# 데이터베이스 만들기
	 * 		- FileUploadController에서 생성함
	 * 
	 * 		# 파일 업로드 구현 설명
	 * 
	 * 			- 파일 업로드 등록화면 컨트롤러 만들기(FileUploadController02)
	 * 			- 파일 업로드 등록 화면 컨트롤러 메서드 만들기(item2RegisterForm:Get)
	 * 			- 파일 업로드 등록 화면 만들기(item2/register.jsp)
	 * 			
	 * 
	 * 			- 파일 업로드 등록 기능 컨트롤러 메서드 만들기(item2Register:post)
	 * 			- 파일 업로드 등록 기능 서비스 인터페이스 메서드 만들기
	 * 			- 파일 업로드 등록 기능 서비스 클래스 메서드 만들기
	 * 			- 파일 업로드 등록 기능 Mapper 인터페이스 메서드 만들기
	 * 			- 파일 업로드 등록 기능 Mapper xml 쿼리 만들기
	 * 			- 파일 업로드 등록 기능완료 페이지 만들기
	 * 
	 * 
	 * 			- 파일 업로드 목록 화면 컨트롤러 메서드 만들기(item2List:get)
	 * 			- 파일 업로드 목록 화면 서비스 인터페이스 메서드 만들기
	 * 			- 파일 업로드 목록 화면 서비스 클래스 메서드 만들기
	 * 			- 파일 업로드 목록 화면 Mapper 인터페이스 메서드 만들기
	 * 			- 파일 업로드 목록 화면 Mapper xml 쿼리 만들기
	 * 			- 파일 업로드 목록 화면 완료 페이지 만들기(item2/list.jsp)
	 * 
	 * 
	 * 			- 파일 업로드 수정 화면 컨트롤러 메서드 만들기(item2ModifyForm:get)
	 * 			- 파일 업로드 수정 화면 서비스 인터페이스 메서드 만들기
	 * 			- 파일 업로드 수정 화면 서비스 클래스 메서드 만들기
	 * 			- 파일 업로드 수정 화면 Mapper 인터페이스 메서드 만들기
	 * 			- 파일 업로드 수정 화면 Mapper xml 쿼리 만들기
	 * 			- 파일 업로드 수정 화면 만들기(item2/modify.jsp)
	 * 			
	 * 
	 * 			- 파일 업로드 수정 기능 컨트롤러 메서드 만들기(item2Modify:post)
	 * 			- 파일 업로드 수정 기능 서비스 인터페이스 메서드 만들기
	 * 			- 파일 업로드 수정 기능 서비스 클래스 메서드 만들기
	 * 			- 파일 업로드 수정 기능 Mapper 인터페이스 메서드 만들기
	 * 			- 파일 업로드 수정 기능 Mapper xml 쿼리 만들기
	 * 
	 * 			
	 * 			- 파일 업로드 삭제 화면 컨트롤러 메서드 만들기(item2RemoveForm:get)
	 * 			- 파일 업로드 삭제 화면 서비스 인터페이스 메서드 만들기
	 * 			- 파일 업로드 삭제 화면 서비스 클래스 메서드 만들기
	 * 			- 파일 업로드 삭제 화면 Mapper 인터페이스 메서드 만들기
	 * 			- 파일 업로드 삭제 화면 Mapper xml 쿼리 만들기
	 * 
	 * 
	 * 			- 파일 업로드 삭제 기능 컨트롤러 메서드 만들기(item2Remove:post)
	 * 			- 파일 업로드 삭제 기능 서비스 인터페이스 메서드 만들기
	 * 			- 파일 업로드 삭제 기능 서비스 클래스 메서드 만들기
	 * 			- 파일 업로드 삭제 기능 Mapper 인터페이스 메서드 만들기
	 * 			- 파일 업로드 삭제 기능 Mapper xml 쿼리 만들기
	 * 			
	 * 
	 * 
	 */
	
	//root-context.xml에서 설정한 uplaodpath 빈등록 path를 경로로 사용
	@Resource(name="uploadPath")
	private String resourcePath;
	
	@Inject
	private IItemservice2 itemService2;
	
	
	
//	@RequestMapping(value="/register", method = RequestMethod.GET) 이전에 하던 방식
//	@PostMapping(value="register") 로도 가능함
	@GetMapping(value="register") //처럼 할 수도 있음
	public String item2RegisterForm() {
		return "item2/register";
	}
	
	@PostMapping(value="register") //처럼 할 수도 있음
	public String item2Register(Item2 item, Model model) throws Exception {
		
		List<MultipartFile> pictures = item.getPictures();
		
		for(int i = 0; i < pictures.size(); i++) {
			MultipartFile file = pictures.get(i);
			
			log.info("fileName>>> " + file.getOriginalFilename() );
			log.info("size>>> " + file.getSize());
			log.info("contentType>>> " + file.getContentType());
			
			String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
			
			
			if(i == 0) {
				item.setPictureUrl(savedName);
			} else if(i == 1) {
				item.setPictureUrl2(savedName);
			}
			
		}
		
		itemService2.register(item);
		model.addAttribute("msg", "등로고ㅗ로로로록 완료");
		return "item2/success";
	}

	
	
	@GetMapping(value="/list")
	public String itemList(Model model) {
		
		List<Item2> itemList = itemService2.list();
		model.addAttribute("itemList", itemList);
		return "item2/list";
	}
	
	
	@GetMapping(value="/modify")
	public String item2ModifyForm(int itemId, Model model) {
		Item2 item = itemService2.read(itemId);
		model.addAttribute("item", item);
		return "item2/modify";
	}
	
	
	@PostMapping(value="/modify")
	public String item2Modify(Item2 item, Model model) throws Exception {
		List<MultipartFile> pictures = item.getPictures();
		
		for(int i = 0; i < pictures.size(); i++) {
			MultipartFile file = pictures.get(i);
			
			if(file != null && file.getSize() > 0) {
				log.info("fileName>> " + file.getOriginalFilename());
				log.info("size>> " + file.getSize());
				log.info("contentType>> " + file.getContentType());
				
				String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
				
				if(i == 0) {
					item.setPictureUrl(savedName);
				} else if(i == 1) {
					item.setPictureUrl2(savedName);
				}
			}
		}
		
		itemService2.modify(item);
		model.addAttribute("msg", "수저저저저저저ㅓㅇ오안로");
		return "item2/success";
	}
	
	@GetMapping(value="/remove")
	public String item2RemoveForm(int itemId, Model model) {
		Item2 item = itemService2.read(itemId);
		model.addAttribute("item",item);
		return "item2/remove";
	}
	
	@PostMapping(value="/remove")
	public String item2Remove(int itemId, Model model) {
		itemService2.remove(itemId);
		model.addAttribute("msg","삭제와료로로로로로");
		return "item2/success";
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(int itemId){
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		String fileName = itemService2.getPicture(itemId);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1); // 확장자 잘라오기
		
		//확장자를 던져서 확장자와 일치하는 MimeType(미디어타입)을 리턴
		MediaType mType = getMediaType(formatName);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			in = new FileInputStream(resourcePath + File.separator + fileName);
			if(mType != null) {
				headers.setContentType(mType);
			} 
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			try {
				in.close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		return entity;
	}
	@ResponseBody
	@RequestMapping(value="/display2", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile2(int itemId){
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		String fileName = itemService2.getPicture2(itemId);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1); // 확장자 잘라오기
		
		//확장자를 던져서 확장자와 일치하는 MimeType(미디어타입)을 리턴
		MediaType mType = getMediaType(formatName);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			in = new FileInputStream(resourcePath + File.separator + fileName);
			if(mType != null) {
				headers.setContentType(mType);
			} 
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			try {
				in.close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		return entity;
	}
	
	
	private MediaType getMediaType(String formatName) {
		if(formatName != null) {
			if(formatName.toUpperCase().equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}
			if(formatName.toUpperCase().equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
			if(formatName.toUpperCase().equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}
		}
		return null;
	}
	
	
	//파일 업로드 함수
	private String uploadFile(String originalFilename, byte[] fileData) throws Exception {
		UUID uuid = UUID.randomUUID();
		String createFileName = uuid.toString() + "_" + originalFilename;
		
		
		File file = new File(resourcePath);
		if(!file.exists()) {
			file.mkdirs(); //없으면 폴더 생성
		}
		
		// 파일 업로드 준비
		File target = new File(resourcePath, createFileName);
		FileCopyUtils.copy(fileData, target); // 파일 복사 진행
		return createFileName;
	}
	

}