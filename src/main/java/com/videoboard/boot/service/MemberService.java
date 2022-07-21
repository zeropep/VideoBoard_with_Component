package com.videoboard.boot.service;

import java.util.List;

import com.videoboard.boot.domain.JobVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PageDTO;
import com.videoboard.boot.domain.PagingVO;

public interface MemberService {
	int registerMember(MemberVO mvo);
	List<MemberVO> getMemberList();
	PageDTO getMemberDetail(String username, PagingVO pgvo);
	MemberVO login(MemberVO mvo);
	int modifyMember(MemberVO mvo);
	int removeMember(MemberVO mvo);
	int emailDuple(String email);
	int usernameDuple(String username);
	PageDTO getJobList(long mno, PagingVO pgvo);
}
