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

class UserElement extends HTMLElement {
  constructor() {
    super();
    let shadowRoot = this.attachShadow({mode: "open"});
    let e = document.createElement('div');
    shadowRoot.appendChild(e);
    window.addEventListener('tokenrefresh', () => {
      this.find();
    }, false)
  }

  find() {
    fetch('/api/me')
        .then(v => v.json())
        .then(v => this.apply(v.name))
        .catch(v => this.apply('anonymous'));
  }

  apply(name) {
    this.shadowRoot.querySelector('div').textContent = name
  }
}

customElements.define("app-user", UserElement);

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
  })
      .then(() => window.dispatchEvent(new CustomEvent('tokenrefresh')))
      .catch(v => console.error(v));
}

function revokeToken(data) {
  fetch('/auth', {
    method: 'DELETE'
  })
    .then(() => window.dispatchEvent(new CustomEvent('tokenrefresh')))
    .catch(v => console.error(v));
}
