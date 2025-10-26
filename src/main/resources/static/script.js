async function startVideoCall() {
  const videoContainer = document.getElementById("videoContainer");
  const localVideo = document.getElementById("localVideo");
  const remoteVideo = document.getElementById("remoteVideo");

  videoContainer.classList.remove("d-none");

  const stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
  localVideo.srcObject = stream;

  alert("This is a demo. Integrate WebRTC or Jitsi Meet API for live two-way communication.");
}

function startJitsi() {
  const room = document.getElementById('className').value.trim();
  if (!room) {
    alert('Please enter a class room name.');
    return;
  }
  
  // Open Jitsi in new window
  const jitsiUrl = `https://meet.jit.si/${room}`;
  window.open(jitsiUrl, '_blank', 'width=1200,height=700');
}

//appointment
document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById('appointmentForm');
  if (form) {
    form.addEventListener('submit', function(event) {
      event.preventDefault();
      document.getElementById('formMsg').innerHTML =
        '<span class="text-success">Thank you! We will contact you soon.</span>';
      form.reset();
    });
  }
});

