class AppElement extends HTMLElement {
  constructor() {
    super();
    let shadowRoot = this.attachShadow({mode: "open"});
    let e = document.createElement("div");
    e.textContent = this.getAttribute("message");
    shadowRoot.appendChild(e);
  }
}

customElements.define("app-root", AppElement);

window.addEventListener('message', event => {
  const u = new URL(location.href).origin;
  if (event.origin !== u) {
    console.warn('unexpected origin', u);
    return;
  }
  const data = JSON.parse(event.data);
  switch (data.action) {
    case 'signIn':
      refreshToken(data);
      break
    case 'signOut':
      revokeToken(data);
      break;
    default:
      console.warn(`invalid action: ${data.action}`)
      break;
  }
}, false)

function refreshToken(data) {
  fetch('/auth', {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({token: data.token})
  }).catch(v => console.error(v));
}

function revokeToken(data) {
  fetch('/auth', {
    method: 'DELETE'
  }).catch(v => console.error(v));
}
