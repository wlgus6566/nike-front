import {code} from './index';

// MYPAGE 주문내역
function getCode(upperCode) {
    return code.get(`/${upperCode}`);
}

export { getCode };
