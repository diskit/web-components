import { html, LitElement } from "lit";
import {property, customElement, state} from 'lit/decorators.js';

@customElement("app-clock")
export class ClockElement extends LitElement {

  @state()
  now: Date = new Date();

  render() {
    return html`
      <div>${this.formatTime(this.now)}</div>
    `
  }

  formatTime(d: Date): string {
    return [d.getHours(), d.getMinutes(), d.getSeconds()].map(v => v.toString().padStart(2, '0')).join(':');
  }

  tick() {
    this.now = new Date();
    setTimeout(() => this.tick(), 1000);
  }

  connectedCallback() {
    super.connectedCallback();
    this.tick();
  }
}