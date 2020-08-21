import {code} from './index';

function getCode(upperCode) {
    return code.get(`/${upperCode}`);
}

export { getCode };
