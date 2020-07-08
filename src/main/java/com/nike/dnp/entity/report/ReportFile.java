package com.nike.dnp.entity.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_REPORT_FILE")
public class ReportFile extends BaseTimeEntity {

    /**
     * The Report file seq
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_FILE_SEQ")
    private Long reportFileSeq;

    /**
     * The Report seq
     * @author [이소정]
     */
    @Column(name = "REPORT_SEQ")
    private Long reportSeq;

    /**
     * The File kind code
     * @author [이소정]
     */
    @Column(name = "FILE_KIND_CODE")
    private String fileKindCode;

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

    /**
     * The Download count
     * @author [이소정]
     */
    @Column(name = "DOWNLOAD_COUNT")
    private Long downloadCount;

    /**
     * The Report
     * @author [이소정]
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_SEQ", insertable = false, updatable = false)
    @ApiModelProperty(name = "report", value = "The Report", hidden = true)
    private Report report;
}
