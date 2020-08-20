function saveAuthToCookie(value) {
    setCookie('user_token', value);
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
    document.cookie = `${cname}=${cvalue}; expires=${expires.toGMTString()}`;

    document.cookie = `${cname}=${cvalue};`;
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

function deleteCookie(value) {
    document.cookie = `${value}=; expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
}

export {
    saveAuthToCookie,
    saveUserNickToCookie,
    saveUserIdToCookie,
    getAuthFromCookie,
    getUserNickFromCookie,
    getUserIdFromCookie,
    deleteCookie,
};
