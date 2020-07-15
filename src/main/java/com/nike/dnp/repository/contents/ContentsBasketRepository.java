package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.ContentsBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsBasketRepository extends JpaRepository<ContentsBasket, Long>, ContentsBasketRepositoryCustom {

}
