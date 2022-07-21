package com.videoboard.boot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.videoboard.boot.domain.MemberVO;

@Mapper
public interface MemberDAO {
	int insertMember(MemberVO mvo);
	MemberVO selectOneMember(String username);
	MemberVO loginMember(MemberVO mvo);
	List<MemberVO> selectAllMember();
	int updateMember(MemberVO mvo);
	int deleteMember(String mno);
	MemberVO selectOneByEmail(String email);
	int emailExist(String email);
	int nameExist(String username);
}
