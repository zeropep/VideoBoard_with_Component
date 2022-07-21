package com.videoboard.boot.handler;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.domain.HistoryVO;
import com.videoboard.boot.repository.FileDAO;
import com.videoboard.boot.repository.HistoryDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileSweeper {
	
	private final String BASE_PATH = "C:/Users/sj_nb_028/Documents/boot/";
	
	@Autowired
	private FileDAO fdao;
	@Autowired
	private HistoryDAO hdao;
	
	@Scheduled(cron = "0 0 13 15 * ?")
	public void fileSweeper() {
		log.info(">>> FileSweeper Running Start : {}", LocalDateTime.now());
		
		// is_delete 컬럼이 yes인 파일목록
		List<FileVO> fList = fdao.selectDeletedFiles();
		
		List<File> filePathList = new ArrayList<File>();
		List<File> folderPathList = new ArrayList<File>();
		
		// 삭제해야 할 파일의 디렉터리 추출하여 파일객체 생성
		for (FileVO fvo : fList) {
			String orgVideoFilePath = String.format("%s/%s/%s_%s%s", "org/video", fvo.getSaveDir(), fvo.getUuid(), 
																			fvo.getFileName(), fvo.getFileExt());
			String proxyVideoFilePath = String.format("%s/%s/%s_%s%s", "proxy/video", fvo.getSaveDir(), fvo.getUuid(), 
																			fvo.getFileName(), ".mp4");
			String thumbFilePath = String.format("%s/%s/%s_th_%s%s", "org/image", fvo.getSaveDir(), fvo.getUuid(), 
																			fvo.getFileName(), ".jpg");
			String storyBoardFilePath = String.format("%s/%s/%s/%s", "proxy/storyboard", fvo.getSaveDir(), fvo.getUuid(), 
																			fvo.getFileName(), Long.toString(fvo.getFno()));
			filePathList.add(new File(BASE_PATH + orgVideoFilePath));
			filePathList.add(new File(BASE_PATH + proxyVideoFilePath));
			filePathList.add(new File(BASE_PATH + thumbFilePath));
			folderPathList.add(new File(BASE_PATH + storyBoardFilePath));
			
		}
		
		try {
			// file 지우기
			for (File file : filePathList) {
				try {
					file.delete();
					log.info(">>> Deleted File named : {}", file.getName());
				} catch (Exception e) {
					log.debug(e.toString());
				}
			}
			
			//folder(storyboard)
			for (File folder : folderPathList) {
				while (folder.exists()) {
					File[] folderList = folder.listFiles();
					
					// 폴더 안 파일삭제
					for (File storyImage : folderList) {
						storyImage.delete();
						log.info(">>> Deleted File named : {}", storyImage.getName());
					}
					
					// 파일삭제 완료시 폴더삭제
					if (folderList.length == 0 && folder.isDirectory()) {
						folder.delete();
						log.info(">>> Storyboard directory removed : {}", folder.getName());
					}
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		// 삭제 대기중인 데이터 지우기
		for (FileVO fvo : fList) {
			hdao.updateRemoved(fvo.getFno());
		}
		fdao.deleteFileData();
		
		log.info(">>> FileSweeper Running Finish : {}", LocalDateTime.now());
	}
}
