package com.nike.dnp.repository.manage;

/**
 * ManagerRepository
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 관리자(유저) Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

//@Repository
//public class ManagerRepositorySupport extends QuerydslRepositorySupport {
public class ManagerRepositorySupport {

   /* private final JPAQueryFactory jpaQueryFactory;

    public ManagerRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Manager.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public PageImpl<Tuple> findAllByManagerIdLikeOrManagerNameLike(
            Pageable pageable, String keyword1, String keyword2
    ) {
        QManager qManager = manager;


        SearchResults<Tuple> list = jpaQueryFactory.from((EntityPath<?>) qManager).listResults();
        *//*List<Manager> managers = query.from((EntityPath<?>) manager)
                .where(manager.managerName.)
                .orderBy(manager.managerSeq.desc())
                .offset(10).limit(20).list(manager);*//*

        return new PageImpl<Tuple>(list.getResults(), pageable, list.getTotal());
    }*/

}
