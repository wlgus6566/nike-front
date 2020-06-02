import axios from '@/axios'

export default {
	//목록 조회
	list(param) {
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
		return axios.post('/api/app/encription',param);
	}
}