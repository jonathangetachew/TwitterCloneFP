import configureStore from 'redux-mock-store';

// Actions to be tested
import tweetConstants from '../../constants/tweets';
import * as tweetActions from '../../actions/tweets';

const mockStore = configureStore();
const store = mockStore();

// Tests for actions
describe("Tweets actions", () => {
    beforeEach(() => { // Runs before each test in the suite
        store.clearActions();
    });

    test('Dispatches getTweet', () => {
        const expectedActions = [
            {
                'type': tweetConstants.GET_TWEETS,
            },
        ];

        store.dispatch(tweetActions.getTweets());
        expect(store.getActions()).toEqual(expectedActions);
    });

    test('Dispatches getTweetRequest', () => {
        const expectedActions = [
            {
                'type': tweetConstants.GET_TWEETS_REQUEST,
            },
        ];

        store.dispatch(tweetActions.getTweetsRequest());
        expect(store.getActions()).toEqual(expectedActions);
    });

    test('Dispatches getTweetsSuccess', () => {
        const data = [{name: "Fake User"}];
        const expectedActions = [
            {
                'type': tweetConstants.GET_TWEETS_SUCCESS,
                data
            },
        ];

        store.dispatch(tweetActions.getTweetsSuccess(data));
        expect(store.getActions()).toEqual(expectedActions);
    });

    test('Dispatches getTweetsFail', () => {
        const error = "Error";
        const expectedActions = [
            {
                'type': tweetConstants.GET_TWEETS_FAIL,
                error
            },
        ];

        store.dispatch(tweetActions.getTweetsFail(error));
        expect(store.getActions()).toEqual(expectedActions);
    });

    test('Dispatches searchTweets', () => {
        const expectedActions = [
            {
                'type': tweetConstants.SEARCH_TWEETS,
                'criteria': 'abc'
            },
        ];

        store.dispatch(tweetActions.searchTweets('abc'));
        expect(store.getActions()).toEqual(expectedActions);
    });

    test('Dispatches searchTweetsRequest', () => {
        const expectedActions = [
            {
                'type': tweetConstants.SEARCH_TWEETS_REQUEST,
            },
        ];

        store.dispatch(tweetActions.searchTweetsRequest());
        expect(store.getActions()).toEqual(expectedActions);
    });

    test('Dispatches searchTweetsSuccess', () => {
        const data = [{name: "Fake User"}];
        const expectedActions = [
            {
                'type': tweetConstants.SEARCH_TWEETS_SUCCESS,
                data
            },
        ];

        store.dispatch(tweetActions.searchTweetsSuccess(data));
        expect(store.getActions()).toEqual(expectedActions);
    });

    test('Dispatches searchTweetsFail', () => {
        const error = "Error";
        const expectedActions = [
            {
                'type': tweetConstants.SEARCH_TWEETS_FAIL,
                error
            },
        ];

        store.dispatch(tweetActions.searchTweetsFail(error));
        expect(store.getActions()).toEqual(expectedActions);
    });
});