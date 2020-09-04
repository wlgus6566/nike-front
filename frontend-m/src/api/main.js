import {main} from './index';

// main
function getMain(params) {
    return main.get(``,{
        params: params,
    });
}

export { getMain };
