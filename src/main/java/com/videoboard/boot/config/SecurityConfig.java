package com.videoboard.boot.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.videoboard.boot.security.JwtAuthenticationEntryPoint;
import com.videoboard.boot.security.JwtAuthenticationFilter;
import com.videoboard.boot.security.JwtTokenGenerator;
import com.videoboard.boot.security.WebAccessDeniedHandler;
import com.videoboard.boot.service.CustomUserDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationEntryPoint unAuthorizedHandler;
	private final WebAccessDeniedHandler webAccessDeniedHandler;
	private final JwtTokenGenerator jwtTokenGenerator;
	private final CustomUserDetailService customUserDetailService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// 패스워드 암호화에 사용될 구현체 지정
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().headers().frameOptions().disable();
		http.cors().and().csrf().disable();

		http.exceptionHandling().authenticationEntryPoint(unAuthorizedHandler)
				.accessDeniedHandler(webAccessDeniedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.formLogin().disable().headers().frameOptions().disable();

		http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenGenerator),
				UsernamePasswordAuthenticationFilter.class);

		http.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/auth/member/list").hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.POST, "/boardone/**", "/video/**", "/comment/**", "/vcommnet/**", "/meta/**", "/edit/**")
					.hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.PUT, "/boardone/**", "/video/**", "/comment/**", "/vcommnet/**", "/meta/**")
					.hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.DELETE, "/boardone/**", "/video/**", "/comment/**", "/vcommnet/**", "/meta/**")
					.hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/boardone/${bno}", "/video/**/${vno}", "/comment/**", "/vcommnet/**", "/auth/member/**")
					.hasAnyRole("ADMIN", "USER") // listup은 풀어줌.
//				.antMatchers("/member/login", "/member/register")
//					.anonymous()
				.antMatchers("/", "/auth/member/login", "/auth/member/refreshToken", "/auth/member/email", "/auth/member/name", "/auth/member/register",
							"/boardone/detail/**", "/boardone/modify/**", "/boardone/register", "/boardone/list",
							"/video/detail/**", "/video/modify/**", "/video/register", "/video/list", "/video/comp/**"
							)
					.permitAll()
				.antMatchers("/member/**", "/static/**", "/js/**", "/css/**", "/style/**", "/style/type/**", "/favicon.ico",
							 "/swagger-ui/**", "/file/{vno}", "/upload/**")
					.permitAll()
				.anyRequest().authenticated();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/favicon.ico", "/v3/api-docs", "/swagger-resources/**", "/swagger-ui/index.html",
				"/webjars/**", "/swagger/**");
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOriginPattern("*");
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
