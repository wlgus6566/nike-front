package com.nike.dnp.repository.auth;

import com.nike.dnp.dto.auth.AuthReturnDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Auth repository custom.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:51:23
 * @Description Auth(권한) Repository Custom Interface 작성
 */
@Repository
public interface AuthRepositoryCustom {

    /**
     * Find by auth seq array list.
     *
     * @param authSeqArray the auth seq array
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오후 2:50:03
     * @Description
     */
    List<AuthReturnDTO> findByAuthSeqArray(final Long... authSeqArray);

}
