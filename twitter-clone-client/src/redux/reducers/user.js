import constants from '../constants/user';

export const initialState = {
  loading: false,
  username: '',
  token: '',
  email: '',
  error: '',
  _id: '',
  provider: ''
};

/**
 * This reducer is used to manage the profile state.
 * @param state
 * @param action
 * @returns {{loading: boolean, username: string, token: string, email: string, error: string, id: string}}
 */
export default (state = initialState, action) => {
  switch (action.type) {
    case constants.LOGIN_REQUEST:
      return { ...state, loading: true };
    case constants.LOGIN_SUCCESS:
      return { ...state, loading: false, ...action.data };
    case constants.LOGIN_FAIL:
      return { ...state, loading: false, error: action.error };
    case constants.REGISTER_REQUEST:
      return { ...state, loading: true };
    case constants.REGISTER_SUCCESS:
      return { ...state, loading: false, ...action.data };
    case constants.REGISTER_FAIL:
      return { ...state, loading: false, error: action.error };
    case constants.UPDATE_REQUEST:
      return { ...state, loading: true };
    case constants.UPDATE_SUCCESS:
      return { ...state, loading: false, ...action.data };
    case constants.UPDATE_FAIL:
      return { ...state, loading: false, error: action.error };
    default:
      return state;
  }
};
