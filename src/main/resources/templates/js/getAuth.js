function getAuth(jqXHR) {
    var auth = jqXHR.getResponseHeader('Authorization');
    return auth;
}