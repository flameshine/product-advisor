const startButton = document.getElementById("startButton");
const stopButton = document.getElementById("stopButton");

let mediaRecorder;
let recordedChunks = [];

startButton.addEventListener("click", () => {

    recordedChunks = [];

    navigator.mediaDevices.getUserMedia({ audio: true })
        .then(stream => {

            mediaRecorder = new MediaRecorder(stream);

            mediaRecorder.ondataavailable = (event) => {
                recordedChunks.push(event.data);
            };

            mediaRecorder.start(1000);
        });

    startButton.disabled = true;
    stopButton.disabled = false;
});

// TODO: should push data to API for further processing

stopButton.addEventListener("click", () => {

    mediaRecorder.stop();

    const blob = new Blob(recordedChunks, { type: 'audio/wav' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');

    recordedChunks = []

    link.href = url;
    link.download = 'recording.wav';

    document.body.appendChild(link);

    link.click();

    startButton.disabled = false;
    stopButton.disabled = true;
});