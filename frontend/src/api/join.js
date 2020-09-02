import { join } from './index';

// join time init
function joinInit(param) {
    return join.get(`/init`, {
        params: param,
    });
}
// join time delete
function joinDelete(menuCode, seq) {
    return join.delete(`/delete`, {
        menuCode: menuCode,
        seq: seq,
    });
}
export { joinInit, joinDelete };
