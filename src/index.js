import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { ModelProvider } from './store/model-context';

ReactDOM.render(
  <ModelProvider>
    <App />
  </ModelProvider>,
  document.getElementById('root')
);