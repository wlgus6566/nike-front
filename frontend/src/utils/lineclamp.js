export class LineClamp {
  constructor(element, binding=2, node) {
    this.element = element;
    this.value = binding.value;
    this.node = node;
    this.build( node.context );
  }

  build( context ) {
    if (this.element.scrollHeight > (this.element.offsetHeight * 1.2)) {
      this.element.classList.add('line-clamp');
    }
  }
}

export default {
  inserted: function(el, binding, node) {
    if (!process.browser || navigator.userAgent.indexOf('AppleWebKit') > -1) return;
    node.lineClamp = new LineClamp(el, binding, node);
  },
  unbind (el, binding, node) {
    if(node.lineClamp) {
      node.lineClamp = null;
    }
  }
}
