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
    const expires = new Date();
    expires.setMinutes(expires.getMinutes() + 30);
    document.cookie = `${cname}=${cvalue}; path=/; expires=${expires.toGMTString()}`;
}
function getAuthNameFromCookie() {
    return document.cookie.replace(
        /(?:(?:^|.*;\s*)user_authName\s*=\s*([^;]*).*$)|^.*$/,
        '$1'
    );
}
function getAuthFromCookie() {
    return document.cookie.replace(
        /(?:(?:^|.*;\s*)user_token\s*=\s*([^;]*).*$)|^.*$/,
        '$1'
    );
}
function getUserNickFromCookie() {
    return document.cookie.replace(
        /(?:(?:^|.*;\s*)user_nick\s*=\s*([^;]*).*$)|^.*$/,
        '$1'
    );
}
function getUserIdFromCookie() {
    return document.cookie.replace(
        /(?:(?:^|.*;\s*)user_id\s*=\s*([^;]*).*$)|^.*$/,
        '$1'
    );
}

function updateCookie() {
    if (!getAuthFromCookie()) return;
    saveAuthToCookie(getAuthFromCookie());
    saveAuthNameToCookie(getAuthNameFromCookie());
    saveUserNickToCookie(getUserNickFromCookie());
    saveUserIdToCookie(getUserIdFromCookie());
}

function deleteCookie(value) {
    document.cookie = `${value}=; expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
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
