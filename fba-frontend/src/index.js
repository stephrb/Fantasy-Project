import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import { ModelProvider } from "./store/model-context";
import { BrowserRouter } from "react-router-dom";

ReactDOM.render(
  <ModelProvider>
    <BrowserRouter>
    <App />
    </BrowserRouter>
  </ModelProvider>,
  document.getElementById("root")
);
