package com.nike.dnp.dto.report;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportFileSaveDTO {

    /**
     * The File name
     * @author [이소정]
     */
    @Column(name = "FILE_NAME")
    private String fileName;

    /**
     * The File size
     * @author [이소정]
     */
    @Column(name = "FILE_SIZE")
    private String fileSize;

    /**
     * The File physical name
     * @author [이소정]
     */
    @Column(name = "FILE_PHYSICAL_NAME")
    private String filePhysicalName;

}
