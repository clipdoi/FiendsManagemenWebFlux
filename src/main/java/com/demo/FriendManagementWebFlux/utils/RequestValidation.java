package com.demo.FriendManagementWebFlux.utils;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveEmailsListReceiveUpdateDto;
import com.demo.FriendManagementWebFlux.dto.SubscribeAndBlockDto;
import com.demo.FriendManagementWebFlux.utils.common.ErrorConstraints;

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

    public static String checkSubscribeAndBlockRequest(SubscribeAndBlockDto.Request request) {
        if (request == null || request.getRequester() == null || request.getTarget() == null) {
            return ErrorConstraints.INVALID_REQUEST;
        }
        if (!EmailUtils.isEmail(request.getRequester())
                || !EmailUtils.isEmail(request.getTarget())) {
            return ErrorConstraints.INVALID_EMAIL;
        }
        if (request.getRequester().equals(request.getTarget())) {
            return ErrorConstraints.EMAIL_DUPLICATED;
        }
        return "";
    }

    public static String checkRetrieveRequest(RetrieveEmailsListReceiveUpdateDto.Request request) {
        if (request == null || request.getSender() == null || request.getText() == null) {
            return ErrorConstraints.INVALID_REQUEST;
        }
        if (!EmailUtils.isEmail(request.getSender())) {
            return ErrorConstraints.INVALID_EMAIL;
        }
        return "";
    }

}
