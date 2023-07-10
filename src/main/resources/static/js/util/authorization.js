export function buildBasicAuthorizationHeader(username, password) {
    const token = username + ':' + password;
    const encodedToken = btoa(token);
    return `Basic ${encodedToken}`;
}