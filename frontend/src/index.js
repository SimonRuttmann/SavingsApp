import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import {Provider} from "react-redux";
import store from "./reduxStore/Store";
import UserService from "./api/Auth";
import 'bootstrap/dist/css/bootstrap.css';


const renderApp = () =>
ReactDOM.render(
  <React.StrictMode>
      <Provider store={store}>
        <App/>
      </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);

UserService.initKeycloak(renderApp);
/**
 * import React from 'react';
 * import App from './App';
 * import {Provider} from "react-redux";
 * import store from "./reduxStore/Store";
 * import {createRoot} from 'react-dom/client'
 *
 * const doc = document.getElementById('root');
 * const root = createRoot(doc)
 *
 *
 * root.render(
 *   <React.StrictMode>
 *       <Provider store={store}>
 *         <App/>
 *       </Provider>
 *   </React.StrictMode>,
 *
 * );
 */