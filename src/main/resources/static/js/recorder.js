import { webmToWav } from './converter.js'

/**
 * We have to assign each window a unique UUID in order to support multiple users recording audios at the same time.
 */
window.onload = () => {
    window.name = crypto.randomUUID();
}

const startButton = document.getElementById('startButton');
const stopButton = document.getElementById('stopButton');

let mediaRecorder;
let recordedChunks = [];

startButton.addEventListener('click', () => {

    recordedChunks = [];

    navigator.mediaDevices.getUserMedia({ audio: true })
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
                    .then((body) => {
                        const container = document.createElement('div');
                        container.setAttribute('class', 'text-center');
                        const text = document.createTextNode(body);
                        container.appendChild(text);
                        document.body.appendChild(container);
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            })
        })
        .catch((error) => {
            console.log(error);
        });
});