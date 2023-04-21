package com.pm490.PM490.dto;

import lombok.Data;

@Data
public class EmailDto {
    private String fromAddress;
    private String toAddress;
    private String mailSubject;
    private String bodyText;
    private String attachFileAddress;
}
