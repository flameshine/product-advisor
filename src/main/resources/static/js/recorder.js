import { webmToWav } from './converter.js'

/**
 * We have to assign each window a unique UUID in order to support multiple users recording audios at the same time.
 * Aside from that, we're setting the 'stop' button as inactive.
 */
window.onload = () => {

    window.name = crypto.randomUUID();

    stopButton.disabled = true;
}

const startButton = document.getElementById('startButton');
const stopButton = document.getElementById('stopButton');

const audioFormatProperties = {
    autoGainControl: false,
    echoCancellation: false,
    noiseSuppression: false
};

let mediaRecorder;
let recordedChunks = [];

startButton.addEventListener('click', () => {

    recordedChunks = [];

    navigator.mediaDevices.getUserMedia({ audio: audioFormatProperties })
        .then(stream => {

            mediaRecorder = new MediaRecorder(stream, { mimeType: 'audio/webm' });

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

    const webmBlob = new Blob(recordedChunks, { type: 'audio/webm' });

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