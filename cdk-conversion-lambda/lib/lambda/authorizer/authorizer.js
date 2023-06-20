// TODO: store secrets securely

const USERNAME = 'conversion-lambda';
const PASSWORD = '45b68ced29d2301f84908bfa5370ad6cc600b758';
const AUTHORIZATION_HEADER = USERNAME + ':' + PASSWORD;

exports.handler = async (event, context, callback) => {

    // TODO: remove this after testing

    console.log(`AWS request id: ${context.awsRequestId}; Event: ${JSON.stringify(event)}`)

    const expected = `Basic ${new Buffer(AUTHORIZATION_HEADER).toString('base64')}`;

    if (expected === event.request.headers.authorization) {
        return callback(null, event);
    }

    return callback(null, {
        status: 403,
        body: 'Unauthorized'
    });
}