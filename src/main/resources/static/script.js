const startButton = document.getElementById("startButton");
const stopButton = document.getElementById("stopButton")
const recordingStatus = document.getElementById("recordingStatus");
const recordTimer = document.getElementById("recordTimer");

let timerSeconds = 0;
let interval;
let audioRecorder; 
let chunksAudio = [];

startButton.addEventListener("click", async () =>{
	const stream = await navigator.mediaDevices.getUserMedia({audio: true})
	audioRecorder = new MediaRecorder(stream);
	chunksAudio = [];
	audioRecorder.start();
	recordingStatus.textContent = "Current Status: Audio is being recorded";
	stopButton.disabled = false;
	
	interval = setInterval(()=>{
		timerSeconds++;
		recordTimer.textContent = timerSeconds + " seconds"
	}, 1000);
	
	
});

stopButton.addEventListener("click", ()=> {
		audioRecorder.stop();
		clearInterval(interval);
		recordingStatus.textContent = "Not recording";
});

// Tests done: timer works