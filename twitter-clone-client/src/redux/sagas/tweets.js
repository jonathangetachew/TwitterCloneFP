import { call, put, takeLatest } from 'redux-saga/effects';
import constants from '../constants/tweets';
import { store } from '../store';
import tweetServiceMock from '../services/tweet.service.mock';
import { getTweetsSuccess, getTweetsFail, searchTweetsRequest, getTweetsRequest, searchTweetsSuccess, searchTweetsFail } from '../actions/tweets';

export function* items(action) {
    yield put(getTweetsRequest());
    try {
        const data = yield call(tweetServiceMock.getTweets);

        yield put(getTweetsSuccess(data.map((item, key) => !item.id ? { ...item, id: key } : item)));
    } catch (error) {
        let errorMessage = 'Error when retrieving items';
        if (error && error.message) {
            errorMessage = error.message;
        }

        yield put(getTweetsFail(errorMessage));
    }
}

export function* searchItems(action) {
    yield put(searchTweetsRequest());
    try {
        const data = yield call(tweetServiceMock.search, action.criteria);
        yield put(searchTweetsSuccess(data.map((item, key) => !item.id ? { ...item, id: key } : item)));
    } catch (error) {

        let errorMessage = 'Error when retrieving items';
        if (error.message.includes("504")) {
            errorMessage = "Error with the criteria. Must be alphanum."
        } else if (error && error.message) {
            errorMessage = error.message;
        }

        yield put(searchTweetsFail(errorMessage));
    }
}

export default [
    takeLatest(constants.GET_TWEETS, items),
    takeLatest(constants.SEARCH_TWEETS, searchItems),
];
