const startButton = document.getElementById("startButton");
const stopButton = document.getElementById("stopButton")
const recordingStatus = document.getElementById("recordingStatus");
const recordTimer = document.getElementById("recordTimer");
const transcription = document.getElementById("transcription");

let timerSeconds = 0;
let interval;
let audioRecorder; 
let chunksAudio = [];

startButton.addEventListener("click", async () =>{
	
	const stream = await navigator.mediaDevices.getUserMedia({audio: true})
	audioRecorder = new MediaRecorder(stream);
	chunksAudio = [];
	
	audioRecorder.addEventListener("dataavailable", (event)=>{chunksAudio.push(event.data)});
	audioRecorder.addEventListener('stop',async ()=>{
		
		stream.getTracks().forEach(track => track.stop());
		const audioTypes = {
			type:"audio/webm"
		};
		const recordedAudio = new Blob(chunksAudio, audioTypes);
		const formData = new FormData();
		formData.append("audio", recordedAudio);
		
		const response = await fetch("/api/audio", {method: "POST", body: formData});
		const result = await response.json();
		transcription.textContent = result.text;
	});
	
	audioRecorder.start();
	recordingStatus.textContent = "Current Status: Audio is being recorded";
	stopButton.disabled = false;
	timerSeconds = 0;
	recordTimer.textContent = "0 seconds";
	
	interval = setInterval(()=>{
		timerSeconds++;
		recordTimer.textContent = timerSeconds + " seconds"
	}, 1000);
	
	
});

stopButton.addEventListener("click", ()=> {
		audioRecorder.stop();
		clearInterval(interval);
		recordingStatus.textContent = "Not recording";
		stopButton.disabled = true;
});


// Tests done: audio array works