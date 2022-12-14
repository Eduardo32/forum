package com.pauloeduardocosta.forum.config.security;

import com.pauloeduardocosta.forum.repository.IUsuarioRepository;
import com.pauloeduardocosta.forum.service.TokenServise;
import com.pauloeduardocosta.forum.service.UsuarioDetalhesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioDetalhesService usuarioDetalhesService;

    @Autowired
    private TokenServise tokenService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioDetalhesService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                .antMatchers(HttpMethod.POST, "/api/usuarios/resetar-senha").permitAll()
                .antMatchers(HttpMethod.GET, "/api/usuarios/verificar-conta/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/usuarios/*/cancelar-senha-temp").permitAll()
                .antMatchers(HttpMethod.POST, "/api/usuarios/esqueci-minha-senha").permitAll()
                .antMatchers(HttpMethod.GET, "/api/email-send").permitAll()
                .antMatchers(HttpMethod.GET, "/api/topicos").permitAll()
                .antMatchers(HttpMethod.GET, "/api/topicos/*").permitAll()
                .antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**", "/h2-console/**");
    }
}
