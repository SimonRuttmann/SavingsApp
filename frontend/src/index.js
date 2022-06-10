import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { configureStore } from '@reduxjs/toolkit'
import { Provider } from 'react-redux';
import userReducer from './features/user'
import advertismentReducer from './features/advertisment'
import inflationReducer from './features/inflation'
import messagingReducer from './features/messaging'

const store = configureStore({
    reducer: {
        user: userReducer,
        advertisment: advertismentReducer,
        inflation: inflationReducer,
        messaging: messagingReducer
    },
})

ReactDOM.render(
  <React.StrictMode>
      <Provider store={store}>
        <App/>
      </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);

