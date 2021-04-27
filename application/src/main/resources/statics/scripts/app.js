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

