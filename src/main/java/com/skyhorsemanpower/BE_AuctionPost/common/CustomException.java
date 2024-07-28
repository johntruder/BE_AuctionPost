package com.skyhorsemanpower.BE_AuctionPost.common;

import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;

public class CustomException extends RuntimeException {
    private final ResponseStatus responseStatus;

    public CustomException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
