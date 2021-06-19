import { html, LitElement } from "lit";
import { customElement, property } from "lit/decorators.js";
import { serverHost } from './configuration';

@customElement("user-element")
export class UserA extends LitElement {

  @property()
  username: string = 'initial name'

  render() {
    return html`
      <div>${this.username}</div>
    `
  }

  connectedCallback() {
    super.connectedCallback();
    this.fetchUser();
  }

  async fetchUser() {
    const response = await fetch(`${serverHost}/api/me`);
    if (response.ok) {
      this.apply(await response.json())
    } else {
      console.log('error');
    }
  }
  apply(value: any) {
    this.username = value.name;
  }
}