package game.data.analyzer.security;

import game.data.analyzer.web.GameObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private GameObjectService gameObjectService;


	@Bean
	public PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/img/analyzer.jpeg").permitAll()
				.antMatchers("/api/recipes").permitAll()
				.antMatchers("/api/locations").permitAll()
				.antMatchers("/api/gameObjects").permitAll()
				.antMatchers("/api/itemTree").permitAll()
				.antMatchers("/h2-console").permitAll()
				.antMatchers("/admin").hasRole(Roll.ADMIN.name())
				.anyRequest().authenticated()
				.and().formLogin().permitAll()
				.and().logout().permitAll();
	}
}
