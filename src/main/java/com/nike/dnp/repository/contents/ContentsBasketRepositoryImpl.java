package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.contents.ContentsBasketResultDTO;
import com.nike.dnp.entity.contents.ContentsBasket;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Class Contents basket repository.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 15. 오후 12:19:07
 * @Description
 */
@Repository
public class ContentsBasketRepositoryImpl extends QuerydslRepositorySupport implements ContentsBasketRepositoryCustom {

    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 6:15:29
     * @Description
     */
    public ContentsBasketRepositoryImpl() {
        super(ContentsBasket.class);
    }

    /**
     * Find all with contents file list.
     *
     * @param authUserDTO the auth user dto
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 15. 오후 12:19:14
     * @Description
     */
    @Override
    public List<ContentsBasketResultDTO> findAllWithContentsFile(AuthUserDTO authUserDTO, String useYn) {
        return null;
    }
}

