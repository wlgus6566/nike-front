package com.nike.dnp.dto.email;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SendCountDTO {

    private String contentsSeq;

    private int sendCount;

    private String sendActionDate;
}
