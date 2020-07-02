<template>
    <div id="wrap">
        <header>
            <div class="inner">
                <h1>
                    <a href="#"><img alt="" src="@/assets/images/logo-nike.svg" /></a>
                </h1>
                <nav>
                    <ul>
                        <li>
                            <a href="#">TEST</a>
                        </li>
                    </ul>
                    <div class="bg"></div>
                </nav>
            </div>
        </header>
        <section id="container">
            <div class="contents">
                <div class="btn-detail">
                    <a href="#" class="btn-list"><span>목록으로 가기</span></a>
                    <div class="btn-box">
                        <button type="button" class="btn-o-white"><span>삭제</span></button>
                        <button type="button" class="btn-o-white"><span>수정</span></button>
                    </div>
                </div>

                <div class="folder-wrap">
                    <h2 class="folder-title">일산라페스타</h2>
                    <div class="inner">
                        <p class="folder-desc">
                            나이키 다이렉트 캠페인 자료와 동영상, 스타일가이드, 기타 그래픽자료가 업데이트 되었습니다. SP20 나이키 다이렉트 NSW 캠페인
                            시공 에셋 자료가 업데이트 되었습니다.
                        </p>
                        <span class="folder-date">2020.01.01 - 2020.01.03</span>
                    </div>
                </div>
                <div class="feedback-wrap">
                    <strong class="title">FEEDBACK</strong>
                    <ul class="feedback-list">
                        <li class="feedback-item">
                            <p class="txt">일산 라페스타점 점장님, 시공보고서 확인완료 했습니다.</p>
                            <div class="info">
                                <span class="name">NIKE MKT 01</span>
                                <span class="date">2020. 06. 04.</span>
                            </div>
                            <button type="button" class="del"><span>삭제</span></button>
                        </li>
                        <li class="feedback-item">
                            <p class="txt">
                                일 산 라페스타점 점장님, 시공보고서 확인완료 했습니다.산 라페스타점 점장님, 시공보고서 확인완료 했습니다.산 라페스타점
                                점장님, 시공보고서 확인완료 했습니다.산 라페스타점 점장님, 시공보고서 확인완료 했습니다.산 라페스타점 점장님,
                                시공보고서 확인완료 했습니다.산 라페스타점 점장님, 시공보고서 확인완료 했습니다.산 라페스타점 점장님, 시공보고서
                                확인완료 했습니다.산 라페스타점 점장님, 시공보고서 확인완료 했습니다.
                            </p>
                            <div class="info">
                                <span class="name">NIKE MKT 01</span>
                                <span class="date">2020. 06. 04.</span>
                            </div>
                            <button type="button" class="del"><span>삭제</span></button>
                        </li>
                    </ul>
                    <div class="more-box">
                        <button type="button" class="txt-btn"><span>더보기</span></button>
                    </div>
                    <div class="textarea">
                        <textarea></textarea>
                        <button type="button" class="btn-form-gray">댓글등록</button>
                    </div>
                </div>
                <ul class="sorting-list">
                    <li class="active">
                        <button type="button"><span>FILE</span></button>
                    </li>
                </ul>
                <div class="all-box">
                    <!-- todo 전체선택 스크립트 작업 필요  -->
                    <label class="check-label">
                        <span class="checkbox">
                            <input type="checkbox" />
                            <span></span>
                        </span>
                        <strong class="txt">전체선택</strong>
                    </label>
                    <p class="desc"><em>1</em>개의 파일이 선택됨</p>
                    <!-- todo select 스크립트 작업 필요  -->
                    <div class="filter-select">
                        <select>
                            <option value="필터">필터</option>
                        </select>
                    </div>
                </div>
                <!-- todo 추가 스크립트 작업 필요  -->
                <ul class="file-item-list">
                    <li class="file-item">
                        <div class="list">
                            <label>
                                <span class="checkbox">
                                    <input type="checkbox" checked />
                                    <span></span>
                                </span>
                                <span class="thumbnail">
                                    <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                                </span>
                                <span class="info-box">
                                    <strong class="title"
                                        >타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다
                                        타이틀입니다 타이틀입니다타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다타이틀입니다
                                        타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다
                                        타이틀입니다</strong
                                    >
                                </span>
                            </label>
                            <div class="btn-box">
                                <button type="button" class="btn-s-sm-white"><i class="icon-check"></i><span>ADDED</span></button>
                                <button type="button" class="btn-more active"><span>더보기</span></button>
                            </div>
                        </div>
                        <div class="detail active">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="down-info">
                                <span class="key">다운로드 횟수</span>
                                <span class="val"><strong>100,300</strong> 회</span>
                            </div>
                        </div>
                    </li>
                    <li class="file-item video">
                        <div class="list">
                            <label>
                                <span class="checkbox">
                                    <input type="checkbox" checked />
                                    <span></span>
                                </span>
                                <span class="thumbnail">
                                    <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                                </span>
                                <span class="info-box">
                                    <strong class="title"
                                        >타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다
                                        타이틀입니다 타이틀입니다타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다타이틀입니다
                                        타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다
                                        타이틀입니다</strong
                                    >
                                </span>
                            </label>
                            <div class="btn-box">
                                <button type="button" class="btn-s-sm-white"><i class="icon-check"></i><span>ADDED</span></button>
                                <button type="button" class="btn-more active"><span>더보기</span></button>
                            </div>
                        </div>
                        <div class="detail active">
                            <div class="video">
                                <iframe
                                    src="https://www.youtube.com/embed/i_IrO7R78Ro"
                                    frameborder="0"
                                    allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                                    allowfullscreen
                                ></iframe>
                            </div>
                            <div class="down-info">
                                <span class="key">다운로드 횟수</span>
                                <span class="val"><strong>100,300</strong> 회</span>
                            </div>
                        </div>
                    </li>
                    <li class="file-item">
                        <div class="list">
                            <label>
                                <span class="checkbox">
                                    <input type="checkbox" />
                                    <span></span>
                                </span>
                                <span class="thumbnail">
                                    <img src="@/assets/images/svg/icon-illust-file-doc.svg" alt="" />
                                </span>
                                <span class="info-box">
                                    <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 </strong>
                                </span>
                            </label>
                            <div class="btn-box">
                                <button type="button" class="btn-s-sm-black"><span>ADD</span></button>
                                <button type="button" class="btn-more"><span>더보기</span></button>
                            </div>
                        </div>
                        <div class="detail">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="down-info">
                                <span class="key">다운로드 횟수</span>
                                <span class="val"><strong>100,300</strong> 회</span>
                            </div>
                        </div>
                    </li>
                    <li class="file-item">
                        <div class="list">
                            <label>
                                <span class="checkbox">
                                    <input type="checkbox" disabled />
                                    <span></span>
                                </span>
                                <span class="thumbnail">
                                    <img src="@/assets/images/svg/icon-illust-file-ar.svg" alt="" />
                                </span>
                                <span class="info-box">
                                    <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 </strong>
                                </span>
                            </label>
                            <div class="btn-box">
                                <a href="#" class="btn-s-sm-white"><i class="icon-link"></i><span>LINK</span></a>
                                <button type="button" class="btn-more" disabled><span>더보기</span></button>
                            </div>
                        </div>
                    </li>
                    <li class="file-item">
                        <div class="list">
                            <label>
                                <span class="checkbox">
                                    <input type="checkbox" />
                                    <span></span>
                                </span>
                                <span class="thumbnail">
                                    <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                                </span>
                                <span class="info-box">
                                    <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 </strong>
                                </span>
                            </label>
                            <div class="btn-box">
                                <button type="button" class="btn-s-sm-black"><span>ADD</span></button>
                                <button type="button" class="btn-more"><span>더보기</span></button>
                            </div>
                        </div>
                        <div class="detail">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="down-info">
                                <span class="key">다운로드 횟수</span>
                                <span class="val"><strong>100,300</strong> 회</span>
                            </div>
                        </div>
                    </li>
                    <li class="file-item">
                        <div class="list">
                            <label>
                                <span class="checkbox">
                                    <input type="checkbox" />
                                    <span></span>
                                </span>
                                <span class="thumbnail">
                                    <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                                </span>
                                <span class="info-box">
                                    <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 </strong>
                                </span>
                            </label>
                            <div class="btn-box">
                                <button type="button" class="btn-s-sm-black"><span>ADD</span></button>
                                <button type="button" class="btn-more"><span>더보기</span></button>
                            </div>
                        </div>
                        <div class="detail">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="down-info">
                                <span class="key">다운로드 횟수</span>
                                <span class="val"><strong>100,300</strong> 회</span>
                            </div>
                        </div>
                    </li>
                    <li class="file-item">
                        <div class="list">
                            <label>
                                <span class="checkbox">
                                    <input type="checkbox" />
                                    <span></span>
                                </span>
                                <span class="thumbnail">
                                    <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                                </span>
                                <span class="info-box">
                                    <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다 </strong>
                                </span>
                            </label>
                            <div class="btn-box">
                                <button type="button" class="btn-s-sm-black"><span>ADD</span></button>
                                <button type="button" class="btn-more"><span>더보기</span></button>
                            </div>
                        </div>
                        <div class="detail">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="down-info">
                                <span class="key">다운로드 횟수</span>
                                <span class="val"><strong>100,300</strong> 회</span>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <aside>
                <div class="aside-wrap">
                    aside
                </div>
            </aside>
        </section>
        <footer></footer>
    </div>
</template>
<script>
export default {
    name: 'NIKE_P_REPORT_03',
};
</script>
<style scoped></style>
