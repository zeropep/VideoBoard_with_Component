package com.videoboard.boot.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.apache.tika.Tika;
import org.imgscalr.Scalr;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Picture;
import org.jcodec.containers.mp4.demuxer.MP4Demuxer;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.domain.FileVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@AllArgsConstructor
@Component
public class FileHandler {
	
	private final String BASE_DIR ="C:\\Users\\sj_nb_028\\Documents\\org\\";;
	
	private final String VIDEO_UP_DIR = BASE_DIR + "video\\";
	private final String IMAGE_UP_DIR = BASE_DIR + "image\\";
	
	public FileVO uploadFiles(MultipartFile file) {
		LocalDate date = LocalDate.now();
		String today = date.toString(); // 2022-01-10
		today = today.replace("-", File.separator); // 2022\01\10
		
		File videoFolders = new File(VIDEO_UP_DIR, today);
		File imageFolders = new File(IMAGE_UP_DIR, today);
		
		if (!videoFolders.exists()) {
			videoFolders.mkdirs();
		}
		if (!imageFolders.exists()) {
			imageFolders.mkdirs();
		}
		
		// fvo에 저장할 파일 정보 생성 => DB로 간다
		FileVO fvo = new FileVO();
		fvo.setSaveDir(today);
		fvo.setFileSize(file.getSize());
		
		String originalFileName = file.getOriginalFilename();
		log.info(">>> originalFileName : {}", originalFileName);
		String onlyFileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1
				, originalFileName.lastIndexOf(".")); // 확장자를 포함한 파일네임.
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
		log.info(">>> onlyFileName : {}", onlyFileName);
		fvo.setFileName(onlyFileName);
		fvo.setFileExt(fileExt);
		
		UUID uuid = UUID.randomUUID();
		fvo.setUuid(uuid.toString());
		
		// 실제로 저장공간에 저장할 파일 객체 생성
		String fullFileName = uuid.toString() + "_" + onlyFileName;
		File storeFile = new File(videoFolders, fullFileName + fileExt);
		
		try {
			file.transferTo(storeFile); // 원본객체를 저장을 위한 형태로 만든 객체로 복사
			// apache.tika lib 파일의 header를 열 수 있다.
			log.info("저장완료");
			if (isImageFile(storeFile)) {
				fvo.setFileType(1); // 이미지는 파일타입을 1이라고 정한다.
				File thumbNail = new File(imageFolders, uuid.toString() + "_th_" + onlyFileName);
				Thumbnails.of(storeFile).size(100, 100).toFile(thumbNail);
			} else if (isVideoFile(storeFile)) {
				fvo.setFileType(2);
				File thumbNail = new File(imageFolders, uuid.toString() + "_th_" + onlyFileName + ".jpg");
				BufferedImage framedImage = getVideoThumb(storeFile);
				ImageIO.write(framedImage, "jpg", thumbNail);
			}
		} catch (Exception e) {
			log.info(">>> File 생성 오류");
			e.printStackTrace();
		}
		
		return fvo;
	}
	
	public List<FileVO> uploadFiles(MultipartFile[] files) {
		LocalDate date = LocalDate.now();
		String today = date.toString(); // 2022-01-10
		today = today.replace("-", File.separator); // 2022\01\10
		
		File videoFolders = new File(VIDEO_UP_DIR, today);
		File imageFolders = new File(IMAGE_UP_DIR, today);
		
		if (!videoFolders.exists()) {
			videoFolders.mkdirs();
		}
		if (!imageFolders.exists()) {
			imageFolders.mkdirs();
		}
		
		List<FileVO> FList = new ArrayList<FileVO>();
		
		for (MultipartFile file : files) {
			// fvo에 저장할 파일 정보 생성 => DB로 간다
			FileVO fvo = new FileVO();
			fvo.setSaveDir(today);
			fvo.setFileSize(file.getSize());
			
			String originalFileName = file.getOriginalFilename();
			log.info(">>> originalFileName : {}", originalFileName);
			String onlyFileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1
																, originalFileName.lastIndexOf(".")); // 확장자를 포함한 파일네임.
			String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
			log.info(">>> onlyFileName : {}", onlyFileName);
			fvo.setFileName(onlyFileName);
			fvo.setFileExt(fileExt);
			
			UUID uuid = UUID.randomUUID();
			fvo.setUuid(uuid.toString());
			
			// 실제로 저장공간에 저장할 파일 객체 생성
			String fullFileName = uuid.toString() + "_" + onlyFileName;
			File storeFile = new File(videoFolders, fullFileName + fileExt);
			
			try {
				file.transferTo(storeFile); // 원본객체를 저장을 위한 형태로 만든 객체로 복사
				// apache.tika lib 파일의 header를 열 수 있다.
				if (isImageFile(storeFile)) {
					fvo.setFileType(1); // 이미지는 파일타입을 1이라고 정한다.
					File thumbNail = new File(imageFolders, uuid.toString() + "_th_" + onlyFileName + ".jpg");
					Thumbnails.of(storeFile).size(100, 100).toFile(thumbNail);
				} else if (isVideoFile(storeFile)) {
					fvo.setFileType(2);
					File thumbNail = new File(imageFolders, uuid.toString() + "_th_" + onlyFileName + ".jpg");
//					BufferedImage framedImage = getVideoThumb(storeFile);
//					ImageIO.write(framedImage, "jpg", thumbNail);
				}
			} catch (Exception e) {
				log.info(">>> File 생성 오류");
				e.printStackTrace();
			}
			FList.add(fvo);
		}
		
		return FList;
	}

	private BufferedImage getVideoThumb(File storeFile) throws IOException, JCodecException {
		int frameNum = 0;
		Picture picture = FrameGrab.getFrameFromFile(storeFile, frameNum);
		
		BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
		BufferedImage targetImage = Scalr.resize(bufferedImage, 320, 180);
		
		return targetImage;
	}
	
	private boolean isImageFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile); 
		log.info(mimeType);
//		 mimeType : 멀티미디어 파일타입을 지칭하는 용어
//		 tika lib를 통해서 파일의 타입을 알 수 있다.
		return mimeType.startsWith("image") ? true : false;
	}
	
	private boolean isVideoFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile);
		log.info(">>>> mimeType : {}", mimeType);
		return mimeType.startsWith("video") ? true : false;
		
	}

}
