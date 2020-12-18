package br.com.zup.nossocartao.proposta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers(HttpMethod.POST, "/carteiras_digitais/**/associa_carteiras_digitais").hasAuthority("SCOPE_propostas:write")
                        .antMatchers(HttpMethod.POST, "/viagens/**/avisos_viagens").hasAuthority("SCOPE_propostas:write")
                        .antMatchers(HttpMethod.POST, "/bloqueios/**/bloquear_cartao").hasAuthority("SCOPE_propostas:write")
                        .antMatchers(HttpMethod.GET, "/propostas/**").hasAuthority("SCOPE_proposals:read")
                        .antMatchers(HttpMethod.POST, "/propostas/**").hasAuthority("SCOPE_proposals:write")
                        .antMatchers(HttpMethod.POST, "/biometrias/**/cria_biometria").hasAuthority("SCOPE_propostas:write")
                        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .anyRequest().authenticated()
        )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}
