const startButton = document.getElementById("startButton");

startButton.addEventListener("click", async () =>{
	const stream = await navigator.mediaDevices.getUserMedia({audio: true})
				console.log("mic access");
	
});