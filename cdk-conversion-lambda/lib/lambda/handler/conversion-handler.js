import { buildBasicAuthorizationHeader } from '../../util/authorization';

const USERNAME = 'conversion-lambda';
const PASSWORD = '45b68ced29d2301f84908bfa5370ad6cc600b758';

exports.handler = async (event, context) => {

    console.log(`AWS request id: ${context.awsRequestId}; Event: ${JSON.stringify(event)}`);

    const expectedAuthorizationHeader = buildBasicAuthorizationHeader(USERNAME, PASSWORD);

    if (expectedAuthorizationHeader !== event.request.headers.authorization) {
        return {
            statusCode: 403,
            body: 'Unauthorized'
        }
    }

    try {

        const webmBlob = new Blob(event.buffer);
        const result = webmToWav(webmBlob);

        console.log(`Result size: ${result.size}`);

        return {
            statusCode: 200,
            body: result,
        };

    } catch (error) {
        console.log(error);
        return {
            statusCode: 500,
            body: 'An unexpected error has occurred'
        }
    }
}

function webmToWav(webmBlob) {
    const reader = new FileReaderSync();
    const webmArrayBuffer = reader.readAsArrayBuffer(webmBlob);
    const context = new AudioContext();
    const audioBuffer = context.decodeAudioData(webmArrayBuffer);
    const channelData = audioBuffer.getChannelData(0);
    const interleavedData = interleave(channelData);
    const wavData = encodeWav(interleavedData, audioBuffer.sampleRate);

    return new Blob([wavData], { type: 'audio/wav' });
}

function interleave(channelData) {

    const frameCount = channelData.length;
    const outputData = new Float32Array(frameCount * 2);

    for (let i = 0, j = 0; i < frameCount; i++, j += 2) {
        outputData[j] = channelData[i];
        outputData[j + 1] = channelData[i];
    }

    return outputData;
}

function encodeWav(interleavedData, sampleRate) {

    const bitDepth = 16;
    const bytesPerSample = bitDepth / 8;
    const blockAlign = 2 * bytesPerSample;
    const buffer = new ArrayBuffer(44 + interleavedData.length * bytesPerSample);
    const view = new DataView(buffer);

    writeString(view, 0, 'RIFF');
    view.setUint32(4, 36 + interleavedData.length * bytesPerSample, true);
    writeString(view, 8, 'WAVE');
    writeString(view, 12, 'fmt ');
    view.setUint32(16, 16, true);
    view.setUint16(20, 1, true);
    view.setUint16(22, 2, true);
    view.setUint32(24, sampleRate, true);
    view.setUint32(28, sampleRate * blockAlign, true);
    view.setUint16(32, blockAlign, true);
    view.setUint16(34, bitDepth, true);
    writeString(view, 36, 'data');
    view.setUint32(40, interleavedData.length * bytesPerSample, true);

    let offset = 44;

    for (let i = 0; i < interleavedData.length; i++, offset += bytesPerSample) {
        const sample = Math.max(-1, Math.min(1, interleavedData[i]));
        view.setInt16(offset, sample < 0 ? sample * 0x8000 : sample * 0x7FFF, true);
    }

    return new Uint8Array(buffer);
}

function writeString(view, offset, string) {
    for (let i = 0; i < string.length; i++) {
        view.setUint8(offset + i, string.charCodeAt(i));
    }
}