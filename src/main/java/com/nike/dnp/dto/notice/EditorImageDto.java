package com.nike.dnp.dto.notice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Component
public class EditorImageDto {

    private int uploaded;

    private String fileName;

    private String url;

}
