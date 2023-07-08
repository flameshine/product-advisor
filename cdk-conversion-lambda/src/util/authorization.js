const { BASE_64 } = require('./encodings');

function buildBasicAuthorizationHeader(username, password) {
    const token = username + ':' + password;
    const encodedToken = new Buffer(token).toString(BASE_64);
    return `Basic ${encodedToken}`;
}

module.exports = { buildBasicAuthorizationHeader };