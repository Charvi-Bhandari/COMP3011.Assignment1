const startButton = document.getElementById("startButton");
const stopButton = docment.getElementById("stopButton")

let audioRecorder; 
let chunksAudio = [];

startButton.addEventListener("click", async () =>{
	const stream = await navigator.mediaDevices.getUserMedia({audio: true})
	audioRecorder = new MediaRecorder(stream);
	chunksAudio = [];
	audioRecorder.start();
	audioRecorder.stop();
	
});