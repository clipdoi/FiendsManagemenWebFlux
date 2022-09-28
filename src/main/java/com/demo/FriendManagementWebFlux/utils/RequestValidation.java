package com.demo.FriendManagementWebFlux.utils;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.utils.constraints.ErrorConstraints;

public class RequestValidation {

    private RequestValidation(){}

    public static String checkAddAndGetCommonRequest(AddFriendDto.Request request) {
        if (request == null || request.getFriends() == null) {
            return ErrorConstraints.INVALID_REQUEST;
        }

        if (request.getFriends().size() != 2) {
            return ErrorConstraints.INVALID_SIZE;
        }
        if (request.getFriends().get(0) == null || request.getFriends().get(1) ==null) {
            return ErrorConstraints.INVALID_REQUEST;
        }
        if (!EmailUtils.isEmail(request.getFriends().get(0))
                || !EmailUtils.isEmail(request.getFriends().get(1))) {
            return ErrorConstraints.INVALID_EMAIL;
        }
        if (request.getFriends().get(0).equals(request.getFriends().get(1))) {
            return ErrorConstraints.EMAIL_DUPLICATED;
        }
        return "";
    }


}
