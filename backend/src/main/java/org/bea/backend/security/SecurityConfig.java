package org.bea.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf(AbstractHttpConfigurer::disable) schalten wir ausnahmsweise! ab, da wir sonst Probleme bei Request (post, put, patch, delete) bekommen,
        // man kann extra Repositority anlegen, das Token erzeugt, die immer ausrollen, anfragen erzeugt usw.

        //.anyRequest().permitAll() ausnahmsweise, da einige Hintergrundprozesse, auch Anfrage,
        // wie Loginendpunkt gesperrt/geschlossen werden werden

        // .authorizeHttpRequests().requestMatchers(HttpMethod, url) immer
        // von speziell nach allgemein und geschlossen nach offen
        // da erste deklarierte Anweisung genommen wird, heisst steht zuerst permit all,
        // sind spätere zugriffsanpassungen schon zu spät, da der Zugriff bereits erfolgte
        // Bsp. .requestMatchers(HttpMethod.GET,"/api/pullexample").authenticated()
        //        .requestMatchers("/api/pullexample").permitAll()

        http
                /**
                 * Was es tut: Deaktiviert CSRF-Schutz.
                 * Warum: Bei REST-APIs oder OAuth2-Logins ohne klassische HTML-Formulare ist CSRF 
                 * nicht notwendig.
                 * Achtung: Nur deaktivieren, 
                 * wenn du kein Login über Formular mit Session nutzt – bei OAuth2 ok.
                 */
                // NOSONAR: Deaktiviert die Sonar-Warnung für diese Zeile
                .csrf(AbstractHttpConfigurer::disable)
                /**
                 * Was es tut: Definiert, welche Endpunkte öffentlich sind und welche Authentifizierung benötigen.
                 * Wichtig: Reihenfolge der Regeln beachten – die erste passende Regel wird angewendet.
                 */
                .authorizeHttpRequests(a -> a
                    //.requestMatchers("/api/admin").hasAuthority("ADMIN")
                    .requestMatchers("/eyf/profile").authenticated()
                    .anyRequest().permitAll()
                )
                /** Sagt Spring Security, wann eine Session erstellt werden soll.
                 * gibt cookies raus -> seesion policy
                 * ALWAYS = Immer, auch wenn nicht unbedingt nötig.
                 * Für OAuth2 ok, aber wenn du später JWT nutzen willst, nimm: SessionCreationPolicy.STATELESS
                 */
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                /** Was es macht: Wenn ein nicht-authentifizierter Nutzer eine geschützte Route aufruft, gibt es HTTP 401 (Unauthorized) statt einer Umleitung auf ein Login-Formular.
                 * Wichtig für APIs und Single-Page-Apps (SPA) → der Browser soll nicht umgeleitet, sondern informiert werden.
                 */
                .exceptionHandling(e -> e
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .logout(l -> l
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("http://localhost:5173/recipe")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                )
                .oauth2Login(o -> o
                    .defaultSuccessUrl("http://localhost:5173/login/success", true)
                //    .failureHandler(new SimpleUrlAuthenticationFailureHandler("http://localhost:5173/login/failure"))
                );

        return http.build();
    }
}