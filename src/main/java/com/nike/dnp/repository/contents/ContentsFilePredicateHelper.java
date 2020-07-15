package com.nike.dnp.repository.contents;


import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.QContents;
import com.nike.dnp.entity.contents.QContentsFile;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;


/**
 * The Class Contents file predicate helper.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 13. 오후 6:01:52
 * @Description
 */
@UtilityClass
public class ContentsFilePredicateHelper {

	/**
	 * Eq section code predicate.
	 *
	 * @param contentsFileSearchDTO the contents file search dto
	 * @return the predicate
	 * @author [이소정]
	 * @CreatedOn 2020. 7. 13. 오후 6:11:47
	 * @Description
	 */
	public Predicate eqSectionCode(final ContentsFileSearchDTO contentsFileSearchDTO) {
		final BooleanBuilder builder = new BooleanBuilder();
		final String sectionCode = contentsFileSearchDTO.getSectionCode();

		if(!StringUtils.isEmpty(sectionCode.trim()) && !"ALL".equals(sectionCode)) {
			builder.and(QContentsFile.contentsFile.fileSectionCode.eq(sectionCode));
		}

		return builder;
	}

	/**
	 * Eq file extension predicate.
	 *
	 * @param contentsFileSearchDTO the contents file search dto
	 * @return the predicate
	 * @author [이소정]
	 * @CreatedOn 2020. 7. 13. 오후 6:10:39
	 * @Description
	 */
	public Predicate compareFileExtension(final ContentsFileSearchDTO contentsFileSearchDTO) {
		final BooleanBuilder builder = new BooleanBuilder();
		final String fileExtension = contentsFileSearchDTO.getFileExtension();

		if (!StringUtils.isEmpty(fileExtension)) {
			builder.and(QContentsFile.contentsFile.fileName.contains(fileExtension));
		}
		return builder;
	}
}
