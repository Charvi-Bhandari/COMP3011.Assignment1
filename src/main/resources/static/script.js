const startButton = document.getElementById("startButton");

let audioRecorder; 
let chunksAudio = [];

startButton.addEventListener("click", async () =>{
	const stream = await navigator.mediaDevices.getUserMedia({audio: true})
				console.log("mic access"); // checking
	audioRecorder = new MediaRecorder(stream);
	chunksAudio = [];
	
});