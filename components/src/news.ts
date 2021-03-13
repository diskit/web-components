import { customElement, LitElement, property } from "lit-element"

@customElement('news-list')
export class NewsListElement extends LitElement {
    @property() 
    lang = 'ja';
    

}