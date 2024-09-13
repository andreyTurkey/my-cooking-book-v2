import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/login/src/vaadin-login-form.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/tooltip/src/vaadin-tooltip.js';
import '@vaadin/button/src/vaadin-button.js';
import 'Frontend/generated/jar-resources/buttonFunctions.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '02dfa051a5d3e1204657160ad95e4eae1598a12de20c0b0c1e1fca9cb34a4e37') {
    pending.push(import('./chunks/chunk-0e04a6f5b8c3c195d623fe3fc24653177e13c909bf9f7d2bcf11e609efe5dc1d.js'));
  }
  if (key === '27341796063178e5c1622d6b5a0091f092902b33c60d8f4565d35be92b2a9999') {
    pending.push(import('./chunks/chunk-0e04a6f5b8c3c195d623fe3fc24653177e13c909bf9f7d2bcf11e609efe5dc1d.js'));
  }
  if (key === 'd4062ae7d1f9a150bfc448ac531b25a143ea117b9e5d8f6e3324a7ec14cd1dcb') {
    pending.push(import('./chunks/chunk-6eae81813773001030eb4a4e59a44ed523262dfbea901841c4fd67e9d8a3ef3a.js'));
  }
  if (key === 'e184e3f2544e811468ec3ba96a9913e0600eeba4a87b8ed2ab7db52a057fef2c') {
    pending.push(import('./chunks/chunk-ef4e27daf9cbae0a88187433b47dc7469392ea49169e3602ad6cf0b479cca810.js'));
  }
  if (key === 'e9e039dc0806f3eac07fba81802014a41168594bfdcdf7d3a38821eba6725a29') {
    pending.push(import('./chunks/chunk-79969e720e0166c8c91d121521408a2b4385a83f490295d8a83cd2ef7d26a481.js'));
  }
  if (key === '6b52fc26600e18260c79f9331218fe7935c30db2cf3ecca82a3643065c64c29f') {
    pending.push(import('./chunks/chunk-ef4e27daf9cbae0a88187433b47dc7469392ea49169e3602ad6cf0b479cca810.js'));
  }
  if (key === 'e2efe0ab7c682f7f97bea2385211c75208680a524e00706cf13d15d3c7d9809d') {
    pending.push(import('./chunks/chunk-2b85016f1cabb717e4158dae3c8677ae41f9291654ee67baa2145b62651a1f1d.js'));
  }
  if (key === '8d74eb060e8585f154245571a6ad292e2f9d3613ee21b9e670c8a79ea1afde85') {
    pending.push(import('./chunks/chunk-0e04a6f5b8c3c195d623fe3fc24653177e13c909bf9f7d2bcf11e609efe5dc1d.js'));
  }
  if (key === 'a8245c65ef88ff559e312cb05d183b45536b4c0ec72aa4a925a50165881c2993') {
    pending.push(import('./chunks/chunk-81084e5559a3afca75e720cf7e5cf6571a69289cebd6ffaa103c4cc971670ebe.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}