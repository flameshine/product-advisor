import { webmToWav } from './converter.js'

const startButton = document.getElementById('startButton');
const stopButton = document.getElementById('stopButton');

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
let recordedChunks = [];

startButton.addEventListener('click', () => {

    recordedChunks = [];

    navigator.mediaDevices.getUserMedia({ audio: AUDIO_FORMAT_PROPERTIES })
        .then(stream => {

            mediaRecorder = new MediaRecorder(stream, { mimeType: WEBM_MEDIA_TYPE });

            mediaRecorder.ondataavailable = (event) => {
                recordedChunks.push(event.data);
            };

            mediaRecorder.start(1000);
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
        .then(result => {

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
        })
        .catch((error) => {
            console.log(error);
        });

    startButton.disabled = false;
    stopButton.disabled = true;
});