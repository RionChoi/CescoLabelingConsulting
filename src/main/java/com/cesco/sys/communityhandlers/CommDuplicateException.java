package com.cesco.sys.communityhandlers;

import org.apache.commons.lang3.StringUtils;

import com.cesco.sys.common.ErrorCode;

import lombok.Getter;

@Getter
public class CommDuplicateException extends RuntimeException{

    private ErrorCode errorCode;

    public CommDuplicateException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;

        if(!StringUtils.isBlank(message)) {
            this.errorCode.setMessage(message);
        }
    }

    public CommDuplicateException(CommDuplicateException e) {
        super(e.getMessage());
        this.errorCode = e.getErrorCode();

        if(!StringUtils.isBlank(e.getMessage())) {
            this.errorCode.setMessage(e.getMessage());
        }
    }
    
}