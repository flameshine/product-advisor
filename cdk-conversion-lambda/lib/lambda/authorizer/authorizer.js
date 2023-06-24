import { buildBasicAuthorizationHeader } from '../../util/authorization';

// TODO: store credentials securely

const USERNAME = 'conversion-lambda';
const PASSWORD = '45b68ced29d2301f84908bfa5370ad6cc600b758';

exports.handler = async (event, context, callback) => {

    console.log(`AWS request id: ${context.awsRequestId}; Event: ${JSON.stringify(event)}`)

    const expected = buildBasicAuthorizationHeader(USERNAME, PASSWORD);

    if (expected === event.request.headers.authorization) {
        return callback(null, event);
    }

    return callback(null, {
        status: 403,
        body: 'Unauthorized'
    });
}