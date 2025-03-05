package swf.army.mil.playground.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*

To secure a Spring Boot Web API against XSS vulnerabilities, implement the following strategies:
Input Validation:
Sanitize and validate all user inputs on the server side before processing them. This includes checking data types, lengths, and formats, and rejecting or escaping any potentially malicious characters.
Output Encoding:
Encode data before rendering it in the response, especially if it includes user-generated content. Use appropriate encoding techniques like HTML escaping to prevent the execution of malicious scripts.
Content Security Policy (CSP):
Configure CSP headers to control the resources that the browser is allowed to load. This can help prevent the execution of unauthorized scripts injected through XSS attacks.
X-XSS-Protection Header:
Use the X-XSS-Protection header to enable the browser's built-in XSS filter. While not a foolproof solution, it can provide an extra layer of protection.
HttpOnly and Secure Cookies:
Set the HttpOnly and Secure flags for cookies to prevent client-side scripts from accessing them and ensure they are transmitted over HTTPS only.
Regular Security Audits:
Conduct regular security audits and penetration testing to identify and address potential vulnerabilities in the API.
Use a Web Application Firewall (WAF):
Implement a WAF to filter malicious traffic and protect the API from common web attacks, including XSS.
Spring Security:
Leverage Spring Security's built-in features for authentication, authorization, and protection against web vulnerabilities.

https://www.google.com/search?q=how+do+you+secure+spring+boot+web+api+from+XSS+vulnerabilities+Balebung&sca_esv=077ea0a66320161e&rlz=1C5GCEM_enUS1119US1120&ei=GBLHZ_m1E9e5wN4P4Orb0A0&ved=0ahUKEwj5oe6e1fCLAxXXHNAFHWD1FtoQ4dUDCBA&uact=5&oq=how+do+you+secure+spring+boot+web+api+from+XSS+vulnerabilities+Balebung&gs_lp=Egxnd3Mtd2l6LXNlcnAiR2hvdyBkbyB5b3Ugc2VjdXJlIHNwcmluZyBib290IHdlYiBhcGkgZnJvbSBYU1MgdnVsbmVyYWJpbGl0aWVzIEJhbGVidW5nSK_MAVCCowFYt8YBcAJ4ApABAJgB1AKgAaMIqgEFOS4zLTG4AQPIAQD4AQGYAgKgAgzCAgoQABiwAxjWBBhHwgIEEAAYR5gDAOIDBRIBMSBAiAYBkAYIkgcBMqAH_gg&sclient=gws-wiz-serp
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Replace with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}