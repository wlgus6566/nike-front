import { join } from './index';

// join time init
function joinInit(param) {
    return join.get(`/init`, {
        params: param,
    });
}
// join time delete
function joinDelete(param) {
    return join.delete(`/delete`, {
        params: param,
    });
}
export { joinInit, joinDelete };
