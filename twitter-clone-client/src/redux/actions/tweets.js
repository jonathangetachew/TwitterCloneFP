import tweetConstants from '../constants/tweets';

export const getTweets = () => ({ type: tweetConstants.GET_TWEETS });

export const getTweetsRequest = () => ({ type: tweetConstants.GET_TWEETS_REQUEST });

export const getTweetsSuccess = (data) => ({ type: tweetConstants.GET_TWEETS_SUCCESS, data });

export const getTweetsFail = (error) => ({ type: tweetConstants.GET_TWEETS_FAIL, error });

export const searchTweets = (criteria) => ({ type: tweetConstants.SEARCH_TWEETS, criteria });

export const searchTweetsRequest = () => ({ type: tweetConstants.SEARCH_TWEETS_REQUEST });

export const searchTweetsSuccess = (data) => ({ type: tweetConstants.SEARCH_TWEETS_SUCCESS, data });

export const searchTweetsFail = (error) => ({ type: tweetConstants.SEARCH_TWEETS_FAIL, error });