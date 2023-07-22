'use strict';

const ffmpeg = require('fluent-ffmpeg');
const { Readable, PassThrough } = require('stream');
const { BASE_64 } = require('./util/encodings');
const { buildBasicAuthorizationHeader } = require('./util/authorization');

const USERNAME = 'conversion-lambda';
const PASSWORD = '45b68ced29d2301f84908bfa5370ad6cc600b758';

exports.handler = async (event) => {

    console.log(`Event: ${event.body}`);

    const expectedAuthorizationHeader = buildBasicAuthorizationHeader(USERNAME, PASSWORD);

    if (event.headers && expectedAuthorizationHeader !== event.headers.authorization) {
        return {
            statusCode: 403,
            body: 'Unauthorized'
        };
    }

    try {

        const webmBuffer = Buffer.from(event.body, BASE_64);
        const wavBuffer = await convertWebmToWav(webmBuffer);
        const responseBody = wavBuffer.toString(BASE_64);

        console.log(`Response body: ${responseBody}`);

        return {
            statusCode: 200,
            body: responseBody
        };

    } catch (error) {
        console.log(error);
        return {
            statusCode: 500,
            body: 'An unexpected error has occurred'
        };
    }
};

function convertWebmToWav(webmBuffer) {
    return new Promise((resolve, reject) => {

        const readableStream = new Readable();

        readableStream.push(webmBuffer);
        readableStream.push(null);

        const outputStream = new PassThrough();
        const buffers = [];

        outputStream.on('data', (chunk) => buffers.push(chunk));
        outputStream.on('finish', () => resolve(Buffer.concat(buffers)));

        ffmpeg()
            .input(readableStream)
            .inputFormat('webm')
            .toFormat('wav')
            .output(outputStream)
            .on('error', (error) => reject(error))
            .run();
    });
}