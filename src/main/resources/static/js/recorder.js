import { webmToWav } from './converter.js'

const startButton = document.getElementById('startButton');
const stopButton = document.getElementById('stopButton');
const visualizer = document.getElementById('visualizer');
const audioContext = new AudioContext();

/**
 * We have to assign each window a unique UUID in order to support multiple users recording audios at the same time.
 * Aside from that, we're setting the 'stop' button as inactive.
 */
window.onload = () => {

    window.name = crypto.randomUUID();

    stopButton.disabled = true;
}

const WEBM_MEDIA_TYPE = 'audio/webm';

const AUDIO_FORMAT_PROPERTIES = {
    autoGainControl: false,
    echoCancellation: false,
    noiseSuppression: false
};

let mediaRecorder;
let recordedChunks;
let analyzer;
let canvasContext;
let bufferLength;
let dataArray;

startButton.addEventListener('click', () => {

    recordedChunks = [];

    navigator.mediaDevices.getUserMedia({ audio: AUDIO_FORMAT_PROPERTIES })
        .then(stream => {

            mediaRecorder = new MediaRecorder(stream, { mimeType: WEBM_MEDIA_TYPE });

            mediaRecorder.ondataavailable = (event) => {
                recordedChunks.push(event.data);
            };

            const source = audioContext.createMediaStreamSource(stream);

            analyzer = audioContext.createAnalyser();
            analyzer.fftSize = 2048;
            bufferLength = analyzer.frequencyBinCount;
            dataArray = new Uint8Array(bufferLength);

            source.connect(analyzer);

            canvasContext = visualizer.getContext('2d');
            visualizer.width = 1000;
            visualizer.height = 175;

            visualize();

            mediaRecorder.start(500);
        })
        .catch((error) => {
            console.log(error);
        });

    startButton.disabled = true;
    stopButton.disabled = false;
});

stopButton.addEventListener('click', () => {

    mediaRecorder.stop();

    const webmBlob = new Blob(recordedChunks, { type: WEBM_MEDIA_TYPE });

    webmToWav(webmBlob)
        .then(result => submitResult(result))
        .catch((error) => {
            console.log(error);
        });

    startButton.disabled = false;
    stopButton.disabled = true;
});

function visualize() {

    requestAnimationFrame(visualize);

    analyzer.getByteTimeDomainData(dataArray);

    canvasContext.fillStyle = '#ffffff';
    canvasContext.strokeStyle = '#229c34';

    canvasContext.fillRect(0, 0, visualizer.width, visualizer.height);
    canvasContext.beginPath();

    const sliceWidth = visualizer.width / bufferLength;

    for (let i = 0, x = 0; i < bufferLength; ++i) {

        const v = dataArray[i] / 128.0;
        const y = v * visualizer.height / 2;

        i === 0 ? canvasContext.moveTo(x, y) : canvasContext.lineTo(x, y);

        x += sliceWidth;
    }

    canvasContext.lineTo(visualizer.width, visualizer.height / 2);
    canvasContext.stroke();
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
            .catch((error) => {
                console.log(error);
            });
    })
}