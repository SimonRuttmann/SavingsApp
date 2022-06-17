import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import {Provider} from "react-redux";
import store from "./reduxStore/Store";
import UserService from "./api/Auth";
import 'bootstrap/dist/css/bootstrap.css';


const renderApp = () =>
ReactDOM.render(
      <Provider store={store}>
        <App/>
      </Provider>,
  document.getElementById('root')
);

UserService.initKeycloak(renderApp);
