//package pers.gene.ticketmanagement;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    private static final ObjectMapper MAPPER = new ObjectMapper();
//
//    @Autowired
//    private SecurityProperties securityProperties;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
//        logger.info("登录成功");
//
//        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
//            response.setContentType("application/json;charset=UTF-8");
//            response.getWriter().write(MAPPER.writeValueAsString(authentication));
//        }else {
//            super.onAuthenticationSuccess(request, response, authentication);
//        }
//
//    }
//}
