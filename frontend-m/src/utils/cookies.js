import Vue from 'vue';

function saveAuthToCookie(value) {
    setCookie('user_token', value);
}
function saveAuthNameToCookie(value) {
    setCookie('user_authName', value);
}
function saveUserNickToCookie(value) {
    setCookie('user_nick', value);
}
function saveUserIdToCookie(value) {
    setCookie('user_id', value);
}

function setCookie(cname, cvalue) {
    Vue.$cookies.set(cname, cvalue, '30min', '/', null, null, 'strict');
}

function getAuthNameFromCookie() {
    return Vue.$cookies.get('user_authName');
}
function getAuthFromCookie() {
    return Vue.$cookies.get('user_token');
}
function getUserNickFromCookie() {
    return Vue.$cookies.get('user_nick');
}
function getUserIdFromCookie() {
    return Vue.$cookies.get('user_id');
}

function updateCookie() {
    if (!getAuthFromCookie()) return;
    saveAuthToCookie(getAuthFromCookie());
    saveAuthNameToCookie(getAuthNameFromCookie());
    saveUserNickToCookie(getUserNickFromCookie());
    saveUserIdToCookie(getUserIdFromCookie());
}

function deleteCookie(value) {
    Vue.$cookies.remove(value);
}

export {
    saveAuthToCookie,
    saveAuthNameToCookie,
    saveUserNickToCookie,
    saveUserIdToCookie,
    getAuthNameFromCookie,
    getAuthFromCookie,
    getUserNickFromCookie,
    getUserIdFromCookie,
    deleteCookie,
    updateCookie,
};
