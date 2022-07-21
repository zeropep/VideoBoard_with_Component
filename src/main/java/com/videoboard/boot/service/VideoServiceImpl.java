package com.videoboard.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.Component.Router;
import com.videoboard.boot.domain.BoardVO;
import com.videoboard.boot.domain.ComponentDTO;
import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.domain.HistoryVO;
import com.videoboard.boot.domain.JobVO;
import com.videoboard.boot.domain.LikeVO;
import com.videoboard.boot.domain.PageDTO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.domain.VideoFileDTO;
import com.videoboard.boot.domain.VideoVO;
import com.videoboard.boot.handler.FileHandler;
import com.videoboard.boot.handler.PagingHandler;
import com.videoboard.boot.repository.CatalogerDAO;
import com.videoboard.boot.repository.FileDAO;
import com.videoboard.boot.repository.HistoryDAO;
import com.videoboard.boot.repository.JobDAO;
import com.videoboard.boot.repository.LikeDAO;
import com.videoboard.boot.repository.VCommentDAO;
import com.videoboard.boot.repository.VideoDAO;

import groovyjarjarantlr4.v4.parse.ANTLRParser.exceptionGroup_return;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {
	
	@Autowired
	private VideoDAO vdao;
	@Autowired
	private FileDAO fdao;
	@Autowired
	private LikeDAO ldao;
	@Autowired
	private JobDAO jdao;
	@Autowired
	private VCommentDAO vcdao;
	@Autowired
	private HistoryDAO hdao;
	@Autowired
	private CatalogerDAO catalogDao;
	@Autowired
	private FileHandler fileHandler;
	@Autowired
	private Router router;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public long registerVideo(VideoVO vvo, MultipartFile[] files) {
		// 파일이 없을때 반환
		if (files.length < 1) {
			return 0;
		}
		
		vdao.insertVideo(vvo);
		
		List<FileVO> fList = fileHandler.uploadFiles(files);
		for (FileVO fvo : fList) {
			fvo.setVno(vvo.getVno());
			fdao.insertFile(fvo);
			JobVO jvo = new JobVO();
			jvo.setMno(vvo.getMno());
			jvo.setVno(vvo.getVno());
			jvo.setFileName(fvo.getFileName() + fvo.getFileExt());
			jvo.setFileType("02");
			jvo.setFno(fvo.getFno());
			jvo.setStatus("01");
			jvo.setComponent("1001");
			
//			jdao.insertJob(jvo);
//			router.buildMessage(jvo, fvo);
		}
		return vvo.getVno();
	}

	@Override
	public PageDTO getVideoList(PagingVO pgvo) {
		PageDTO dto = new PageDTO(new PagingHandler(pgvo, vdao.selectTotalCount(pgvo.getKeyword())));
		dto.setVideoList(vdao.selectAllVideos(pgvo));
		return dto;
	}

	@Override
	public VideoFileDTO getVideoDetail(long vno, long mno) {
		VideoFileDTO vfdto = new VideoFileDTO(vdao.videoDetail(vno), fdao.selectFile(vno)); // jobseq에서 vno로 변경
		vfdto.setMine(vfdto.getVvo().getMno() == mno ? true : false);
		vfdto.setLike(ldao.selectLikeVideo(new LikeVO(mno, vno)) > 0 ? true : false);
		return vfdto;
	}
	
	@Override
	public ComponentDTO getVideoComponent(long vno) {
		ComponentDTO dto = new ComponentDTO(new VideoFileDTO(vdao.videoDetail(vno), fdao.selectFile(vno)), 
											jdao.selectJobsByVno(vno),
											catalogDao.selectResults(vno));
		return dto;
	}

	@Override
	@Transactional
	public int modifyVideo(VideoVO vvo, MultipartFile[] files) {
		int result = 1;
		result *= vdao.updateVideo(vvo);
		if (files != null && files.length > 0) {
			List<FileVO> fList = fileHandler.uploadFiles(files);
			for (FileVO fvo : fList) {
				fvo.setVno(vvo.getVno());
				FileVO target = fdao.selectFile(vvo.getVno());
				HistoryVO hvo = new HistoryVO(0, 
											vvo.getMno(), 
											vvo.getVno(), 
											target.getUuid(), 
											target.getSaveDir(), 
											target.getFileName() + target.getFileExt(), 
											null);
				result *= hdao.insertHistory(hvo);
				
				result *= fdao.deleteFile(vvo.getVno());
				result *= fdao.insertFile(fvo);
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int removeVideo(long vno) {
		vdao.deleteVideo(vno);
		return fdao.deleteFile(vno);
	}

	@Override
	public int getTotalCount(String keyword) {
		return vdao.selectTotalCount(keyword);
	}

	@Override
	public int viewCountUp(long vno) {
		return vdao.addViewCount(vno);
	}


}
