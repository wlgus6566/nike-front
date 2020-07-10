import axios from '@/axios';

export default {
    list(param) {
        //목록 조회
        // axios 요청을 생성합니다.
        /*
        axios 요청을 생성합니다.
        https://github.com/axios/axios axios API 챕터
        인터셉터를 안썼으면 다음과 같습니다.
        axios({
            baseURL: 'https://api.hnpwa.com',
            url: '/news/1.json',
            method: 'get'
        })
        */
        return axios.get('/api/manage/user', param);
    },
    post(param) {
        //사용자 등록
        return axios.post('/api/manage/user', param);
    },
    update(key, param) {
        //사용자 수정
        return axios.put('/api/manage/user/' + key, param);
    },
    detail(key) {
        //상세 조회
        return axios.get('/api/manage/user/' + key);
    },
    delete(key) {
        //사용자 삭제
        return axios.delete('/api/manage/user/' + key);
    },
    get(url,config){
        return axios.get(url,config);
    }
};
