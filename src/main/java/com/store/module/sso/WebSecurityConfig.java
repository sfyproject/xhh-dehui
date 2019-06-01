package com.store.module.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.headers().frameOptions().disable();
		httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/").permitAll()
				.antMatchers("/user/api/order/callBack.json", "/user/api/sso/**", "/back/sso/**", "/**")
				.permitAll();

		httpSecurity.authorizeRequests().antMatchers("/back/**").hasAnyAuthority("ADMIN");
		httpSecurity.authorizeRequests().antMatchers("/user/**").hasAnyAuthority("USER");

		httpSecurity.authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new Http401UnauthorizedEntryPoint()).and().logout().permitAll();

		httpSecurity.headers().cacheControl();
	}

}