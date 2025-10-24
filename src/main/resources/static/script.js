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
  document.getElementById('jitsiContainer').style.display = 'block';
  if (window.jitsiApi) {
    window.jitsiApi.dispose();
  }
  window.jitsiApi = new JitsiMeetExternalAPI("meet.jit.si", {
    roomName: room,
    width: "100%",
    height: 500,
    parentNode: document.getElementById('jitsiContainer'),
    interfaceConfigOverwrite: {
      TILE_VIEW_ENABLED: true
    }
  });
}

// Load Jitsi Meet API
const script = document.createElement('script');
script.src = 'https://meet.jit.si/external_api.js';
document.head.appendChild(script);

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

