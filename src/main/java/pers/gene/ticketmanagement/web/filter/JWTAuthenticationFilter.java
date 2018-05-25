package pers.gene.ticketmanagement.web.filter;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pers.gene.ticketmanagement.web.constant.ConstantKey;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //重点：前端获取login成功时的res.addHeader("Authorization", "Bearer " + token);。前端发送请求时在header中带有Authorization信息，后端抓取该hear做验证
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            // parse the token.
            String user = null;
            try {
                user = Jwts.parser()
                        .setSigningKey(ConstantKey.SIGNING_KEY)
                        .parseClaimsJws(token.replace("Bearer ", ""))
                        .getBody()
                        .getSubject();
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            } catch (ExpiredJwtException e) {
                logger.error("Token已过期: {} " + e);
                e.printStackTrace();
            } catch (UnsupportedJwtException e) {
                logger.error("Token格式错误: {} " + e);
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.error("Token没有被正确构造: {} " + e);
                e.printStackTrace();
            } catch (SignatureException e) {
                logger.error("签名失败: {} " + e);
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                logger.error("非法参数异常: {} " + e);
                e.printStackTrace();
            }
        }
        return null;
    }


}