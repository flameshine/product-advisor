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
        });

    startButton.disabled = true;
    stopButton.disabled = false;
});

stopButton.addEventListener('click', () => {

    mediaRecorder.stop();

    const blob = new Blob(recordedChunks, { type: 'audio/webm' });
    const data = new FormData();

    data.append('recording', blob, 'recording.webm');

    fetch('/recognize', {
        method: 'POST',
        body: data
    }).then(() => {
        location.href = '/result'
    })
});