import { applyMiddleware, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga';
import { persistReducer } from 'redux-persist';
import storage from './storage';

import reducer from '../reducers';

const persistConfig = {
  key: 'root',
  whitelist: ['user'],
  storage
};

const persistedReducer = persistReducer(persistConfig, reducer);

export const sagaMiddleware = createSagaMiddleware();
export const store = createStore(
  persistedReducer,
  applyMiddleware(sagaMiddleware),
);

export default {
  getStore: () => store,
  getState: () => store.getState(),
  getSagaMiddleware: () => sagaMiddleware
}