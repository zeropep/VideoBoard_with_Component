package com.videoboard.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.enums.ErrorCode;
import com.videoboard.boot.repository.MemberDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private MemberDAO mdao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO mvo = mdao.selectOneByEmail(username);
		log.debug(">>> DB에서 꺼내져 온 mvo", mvo.toString());
		if (mvo == null) {
//			 throw new AuthenticationException(ErrorCode.UsernameOrPasswordNotFoundException);
		}
		return mvo;
	}

}
