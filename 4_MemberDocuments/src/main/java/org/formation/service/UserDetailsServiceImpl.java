package org.formation.service;

import org.formation.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
		@Autowired
		MemberRepository memberRepository;
		
		@Override
		@Transactional(readOnly = true)
		public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
					
		}



}
