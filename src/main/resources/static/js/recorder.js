// TODO: switch to the async approach

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

const WEBM_MEDIA_TYPE = 'audio/webm';

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

            mediaRecorder = new MediaRecorder(stream, { mimeType: WEBM_MEDIA_TYPE });

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

    const webmBlob = new Blob(recordedChunks, {type: WEBM_MEDIA_TYPE});

    await retrieveConvertedBlob(webmBlob)
        .then((response) => submitResult(response))
        .catch((error) => console.log(error));

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

    // TODO: store credentials securely

    const conversionLambdaUrl = 'https://re75p9fghj.execute-api.us-east-1.amazonaws.com/v1/convert';
    const username = 'conversion-lambda';
    const password = '45b68ced29d2301f84908bfa5370ad6cc600b758';
    const webmBlobBase64String = await blobToBase64(webmBlob);

    console.log(JSON.stringify(webmBlobBase64String));

    const httpRequestProperties = {
        method: 'POST',
        body: JSON.stringify(webmBlobBase64String),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': buildBasicAuthorizationHeader(username, password),
        }
    };

    return fetch(conversionLambdaUrl, httpRequestProperties)
        .catch((error) => console.log(error));
}

async function blobToBase64(blob) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();

        reader.onloadend = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);

        reader.readAsDataURL(blob);
    });
}

function buildBasicAuthorizationHeader(username, password) {
    const token = username + ':' + password;
    const encodedToken = btoa(token);
    return `Basic ${encodedToken}`;
}

function submitResult(result) {

    const data = new FormData();

    data.append('recording', result, 'recording.wav');

    fetch('/recognize', {
        method: 'POST',
        body: data
    }).then((response) => {
        response.text()
            .then((text) => document.body.innerHTML = text)
            .catch((error) => console.log(error));
    })
}