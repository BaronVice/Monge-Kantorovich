package com.solutions.mongekantorovich.util.handlers;

import com.solutions.mongekantorovich.dto.ResponseDto;

public class ResponseDtoHandler {
    public static void setClosedStatus(ResponseDto responseDto, int status){
        responseDto.setIsClosed(
                switch (status){
                    case 1 -> "Opened: additional consumer required";
                    case -1 -> "Opened: additional producer required";
                    default -> "Closed";
                }
        );
    }
}
