const startButton = document.getElementById("startButton");
const stopButton = document.getElementById("stopButton")
const recordingStatus = document.getElementById("recordingStatus");

let audioRecorder; 
let chunksAudio = [];

startButton.addEventListener("click", async () =>{
	const stream = await navigator.mediaDevices.getUserMedia({audio: true})
	audioRecorder = new MediaRecorder(stream);
	chunksAudio = [];
	audioRecorder.start();
	recordingStatus.textContent = "Current Status: Audio is being recorded";
	stopButton.disabled = false;
		console.log("Recording started");
	
});

stopButton.addEventListener("click", ()=> {
		audioRecorder.stop();
		recordingStatus.textContent = "Not recording";
				console.log("Current Status: Recording stopped");
});

// Tests done: buttons work