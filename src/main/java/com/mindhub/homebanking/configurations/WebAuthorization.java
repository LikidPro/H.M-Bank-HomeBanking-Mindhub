package com.mindhub.homebanking.configurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter  {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()

                .antMatchers("/rest/**").hasAuthority("ADMINSUPREMO")
                .antMatchers("/admin/admin.html").hasAuthority("ADMINSUPREMO")
                .antMatchers("/admin/admin.js").hasAuthority("ADMINSUPREMO")
                .antMatchers("/admin/style-admin.css").hasAuthority("ADMINSUPREMO")
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN","ADMINSUPREMO")
                .antMatchers("/api/loans/admin").hasAnyAuthority("ADMIN","ADMINSUPREMO")
                .antMatchers("/h2-console").hasAuthority("ADMIN")
                .antMatchers("/api/client/validation").permitAll()
                .antMatchers("/web/assets/**").permitAll()
                .antMatchers("/web/**", "/Web/**","/wEb/**","/weB/**","/WeB/**","/wEB/**","/WEB/**").hasAnyAuthority("ADMIN", "USER", "ADMINSUPREMO")
                .antMatchers("login/**").permitAll()
                .antMatchers(HttpMethod.POST , "api/clients").permitAll()
                .antMatchers(HttpMethod.POST , "api/accounts").permitAll()
                .antMatchers(HttpMethod.PATCH, "api/clients/current").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE,"api/clients/current/cards").hasAuthority("USER")
        ;

       http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/log");
        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");


        // turn off checking for CSRF tokens

        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

//        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> {
            if (req.getRequestURI().contains("web")) {
              res.sendRedirect("/login/index.html");
            }
            if (req.getRequestURI().contains("admin")) {
                res.sendRedirect("/login/index.html");
            }
        });

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> {clearAuthenticationAttributes(req);});


        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());



    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }


    }


