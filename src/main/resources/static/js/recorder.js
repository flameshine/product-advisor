import { blobToBase64, base64toBlob } from './util/base64.js';
import { buildBasicAuthorizationHeader } from './util/authorization.js';
import { WEBM, WAV } from './util/media-types.js';

const START_BUTTON = document.getElementById('startButton');
const STOP_BUTTON = document.getElementById('stopButton');
const VISUALIZER = document.getElementById('visualizer');

/**
 * We have to assign each window a unique UUID in order to support multiple users recording audios at the same time.
 * Aside from that, we're setting the 'stop' button as inactive.
 */
window.onload = () => {
    window.name = crypto.randomUUID();
    STOP_BUTTON.disabled = true;
}

// TODO: store credentials securely

const CONVERSION_ENDPOINT = 'https://b4ouf4uuu2.execute-api.us-east-1.amazonaws.com/v1/convert';
const USERNAME = 'conversion-lambda';
const PASSWORD = '45b68ced29d2301f84908bfa5370ad6cc600b758';

let mediaRecorder;
let recordedChunks;
let analyzer;
let canvasContext;
let bufferLength;
let dataArray;

START_BUTTON.addEventListener('click', () => {

    recordedChunks = [];

    navigator.mediaDevices.getUserMedia({ audio: true })
        .then(stream => {

            mediaRecorder = new MediaRecorder(stream, { mimeType: WEBM });

            mediaRecorder.ondataavailable = (event) => {
                recordedChunks.push(event.data);
            };

            const audioContext = new AudioContext();
            const source = audioContext.createMediaStreamSource(stream);

            analyzer = audioContext.createAnalyser();
            analyzer.fftSize = 2048;
            bufferLength = analyzer.frequencyBinCount;
            dataArray = new Uint8Array(bufferLength);

            source.connect(analyzer);

            canvasContext = VISUALIZER.getContext('2d');
            VISUALIZER.width = 1000;
            VISUALIZER.height = 175;

            visualize();

            mediaRecorder.start(500);
        })
        .catch((error) => {
            console.log(error);
        });

    START_BUTTON.disabled = true;
    STOP_BUTTON.disabled = false;
});

STOP_BUTTON.addEventListener('click', async () => {

    mediaRecorder.stop();

    const webmBlob = new Blob(recordedChunks, { type: WEBM });

    try {
        const response = await retrieveConvertedBlob(webmBlob);
        await submitResult(response);
    } catch (error) {
        console.log(error);
    }

    START_BUTTON.disabled = false;
    STOP_BUTTON.disabled = true;
});

function visualize() {

    requestAnimationFrame(visualize);
    analyzer.getByteTimeDomainData(dataArray);

    canvasContext.fillStyle = '#ffffff';
    canvasContext.strokeStyle = '#229c34';

    canvasContext.fillRect(0, 0, VISUALIZER.width, VISUALIZER.height);
    canvasContext.beginPath();

    const sliceWidth = VISUALIZER.width / bufferLength;

    for (let i = 0, x = 0; i < bufferLength; ++i) {

        const v = dataArray[i] / 128.0;
        const y = v * VISUALIZER.height / 2;

        i === 0 ? canvasContext.moveTo(x, y) : canvasContext.lineTo(x, y);

        x += sliceWidth;
    }

    canvasContext.lineTo(VISUALIZER.width, VISUALIZER.height / 2);
    canvasContext.stroke();
}

async function retrieveConvertedBlob(webmBlob) {

    const webmBlobBase64String = await blobToBase64(webmBlob);

    const httpRequestProperties = {
        method: 'POST',
        body: webmBlobBase64String,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': buildBasicAuthorizationHeader(USERNAME, PASSWORD),
        }
    };

    try {
        const response = await fetch(CONVERSION_ENDPOINT, httpRequestProperties);
        const responseBody = await response.text();
        return base64toBlob(responseBody, WAV);
    } catch (error) {
        console.log(error);
    }
}

function submitResult(result) {

    const data = new FormData();

    data.append('recording', result, 'recording.wav');

    fetch('/advise', {
        method: 'POST',
        body: data
    }).then((response) => {
        response.text()
            .then((text) => document.body.innerHTML = text)
            .catch((error) => console.log(error));
    })
}