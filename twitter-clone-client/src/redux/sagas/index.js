import { all } from 'redux-saga/effects';
import tweets from './tweets';
import user from './user';

export default function* rootSaga() {
    yield all([
      ...user,
      ...tweets
    ])
  } 