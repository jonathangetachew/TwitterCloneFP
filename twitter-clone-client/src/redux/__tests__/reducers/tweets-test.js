import reducer from '../../reducers/tweets'
import * as tweetActions from '../../actions/tweets';
const initialState = {
    loading: false,
    error: undefined,
    data: {}
};

describe('Tweets reducer', () => {
    it('should return the initial state', () => {
        expect(reducer(undefined, {})).toEqual(initialState)
    })

    it('should handle GET_TWEETS_REQUEST', () => {
        expect(
            reducer(initialState, tweetActions.getTweetsRequest())
        ).toEqual({ ...initialState, loading: true })
    })

    it('should handle GET_TWEETS_SUCCESS', () => {
        const data = { id: 0, name: 'data' }
        expect(reducer(initialState, tweetActions.getTweetsSuccess(data))).toEqual({ ...initialState, loading: false, data })
    })

    it('should handle GET_TWEETS_FAIL', () => {
        expect(reducer(initialState, tweetActions.getTweetsFail("Error"))).toEqual({ ...initialState, loading: false, error: "Error" })
    })

    it('should handle SEARCH_TWEETS_REQUEST', () => {
        expect(reducer(initialState, tweetActions.searchTweetsRequest())).toEqual({ ...initialState, loading: true })
    })

    it('should handle SEARCH_TWEETS_SUCCESS', () => {
        const data = { id: 0, name: 'data' }
        expect(reducer(initialState, tweetActions.searchTweetsSuccess(data))).toEqual({ ...initialState, loading: false, data })
    })

    it('should handle SEARCH_TWEETS_FAIL', () => {
        expect(reducer(initialState, tweetActions.searchTweetsFail("ERROR"))).toEqual({ ...initialState, loading: false, error: "ERROR" })
    })
})