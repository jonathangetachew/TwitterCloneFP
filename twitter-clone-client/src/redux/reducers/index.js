import { combineReducers } from 'redux';
import user from './user';
import tweets from './tweets';

const AppReducer = combineReducers({
    user,
    tweets
});

export default (state, action) => {
    return AppReducer(state, action);
};
