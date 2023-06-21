export function buildBasicAuthorizationHeader(username, password) {
    const token = username + ':' + password;
    const encodedToken = new Buffer(token).toString('base64');
    return `Basic ${encodedToken}`;
}