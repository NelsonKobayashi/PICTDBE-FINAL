package com.alucar.domain.security;

import com.alucar.domain.model.Funcao;
import com.alucar.domain.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class JWTConfiguracao extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl clienteService;
    private final PasswordEncoder passwordEncoder;


    public JWTConfiguracao(UserDetailsServiceImpl clienteService, PasswordEncoder passwordEncoder) {
        this.clienteService = clienteService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(clienteService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String ADMIN = Funcao.Authority.ADMIN.name();
        String MODERADOR = Funcao.Authority.MODERADOR.name();
        String CLIENTE = Funcao.Authority.CLIENTE.name();


        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())

                .and()
                .csrf().disable()
                .authorizeRequests()

                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/swagger-ui").permitAll()
                .mvcMatchers("/doc/**").permitAll()

                .antMatchers(HttpMethod.POST, "/clientes/cadastrar").permitAll()
                .antMatchers(HttpMethod.POST, "/clientes/validarSenha").permitAll()
                .antMatchers(HttpMethod.POST, "/clientes/atualizar").authenticated()
                .antMatchers(HttpMethod.POST, "/clientes/deletar").authenticated()

                .mvcMatchers(HttpMethod.POST, "/carro/cadastrar").authenticated()
                .antMatchers(HttpMethod.POST, "/carro/atualizar").authenticated()
                .antMatchers(HttpMethod.POST, "/carro/deletar").authenticated()

                .mvcMatchers(HttpMethod.GET).hasAnyAuthority(ADMIN, MODERADOR)
                .mvcMatchers(HttpMethod.PUT).hasAnyAuthority(ADMIN, MODERADOR)
                .mvcMatchers(HttpMethod.DELETE).hasAnyAuthority(ADMIN, MODERADOR)
                //.anyRequest().authenticated()
                .and()
                .addFilter(new JWTAutenticarFiltro(authenticationManager()))
                .addFilter(new JWTValidarFiltro(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            source.registerCorsConfiguration("/**", corsConfiguration);

            return source;
        }


}
