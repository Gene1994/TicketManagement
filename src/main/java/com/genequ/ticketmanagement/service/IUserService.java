package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.common.ServerResponse;
import com.genequ.ticketmanagement.pojo.User;

import java.io.IOException;

public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question,String answer);

    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);

    /**
     * 上传头像
     * @param user
     * @param avatar
     * @throws IOException
     */
//    boolean uploadAvatar(User user, MultipartFile avatar) throws IOException;

}
