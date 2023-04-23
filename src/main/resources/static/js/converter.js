/**
 * We have to programmatically encode .webm to .wav audio format,
 * since recording in the desired MIME type directly isn't supported in most browsers for some reason.
 */
export function webmToWav(webmBlob) {
    return new Promise((resolve, reject) => {

        const reader = new FileReader();

        reader.onload = () => {

            const webmArrayBuffer = reader.result;
            const context = new AudioContext();

            context.decodeAudioData(webmArrayBuffer)
                .then((audioBuffer) => {

                    const channelData = audioBuffer.getChannelData(0);
                    const interleavedData = interleave(channelData);
                    const wavData = encodeWav(interleavedData, audioBuffer.sampleRate);

                    resolve(
                        new Blob([wavData], { type: 'audio/wav' })
                    );

                }).catch((error) => {
                    reject(error);
                });
        };

        reader.readAsArrayBuffer(webmBlob);
    });
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