package com.videoboard.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoboard.boot.domain.JobVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PageDTO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.handler.PagingHandler;
import com.videoboard.boot.repository.JobDAO;
import com.videoboard.boot.repository.MemberDAO;
import com.videoboard.boot.repository.VideoDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImple implements MemberService {
	
	@Autowired
	private MemberDAO mdao;
	@Autowired
	private VideoDAO vdao;
	@Autowired
	private JobDAO jdao;
	
	@Override
	public int registerMember(MemberVO mvo) {
		return mdao.insertMember(mvo);
	}

	@Override
	public List<MemberVO> getMemberList() {
		return mdao.selectAllMember();
	}

	@Override
	public PageDTO getMemberDetail(String username, PagingVO pgvo) {
		PageDTO dto = new PageDTO(new PagingHandler(pgvo, vdao.selectCountByUsername(username)));
		dto.setVideoList(vdao.selectVideosByUsername(pgvo, username));
		dto.setMvo(mdao.selectOneMember(username));
		return dto; 
	}

	@Override
	public MemberVO login(MemberVO mvo) {
		return mdao.loginMember(mvo);
	}

	@Override
	public int modifyMember(MemberVO mvo) {
		return mdao.updateMember(mvo);
	}

	@Override
	public int removeMember(MemberVO mvo) {
		return mdao.deleteMember(null);
	}

	@Override
	public int emailDuple(String email) {
		return mdao.emailExist(email);
	}

	@Override
	public int usernameDuple(String username) {
		return mdao.nameExist(username);
	}

	@Override
	public PageDTO getJobList(long mno, PagingVO pgvo) {
		PageDTO dto = new PageDTO(new PagingHandler(pgvo, jdao.selectTotalCount(mno)));
		dto.setJobList(jdao.selectJobs(mno, pgvo));
		return dto;
	}

}
