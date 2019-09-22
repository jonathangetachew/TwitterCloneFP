import userConstants from '../constants/user';

export const loginRequest = () => ({ type: userConstants.LOGIN_REQUEST });

export const loginSuccess = ({ token, user }) => ({ type: userConstants.LOGIN_SUCCESS, data: { token, ...user } });

export const loginFail = (error) => ({ type: userConstants.LOGIN_FAIL, error });

export const requestSignUp = () => ({ type: userConstants.REGISTER_REQUEST });

export const successSignUp = ({ token, user }) => ({ type: userConstants.REGISTER_SUCCESS, data: { token, ...user } });

export const failedSignUp = (error) => ({ type: userConstants.REGISTER_FAIL, error });

export const logout = () => ({ type: userConstants.LOGOUT_REQUEST });

export const updateRequest = () => ({ type: userConstants.UPDATE_REQUEST });

export const updateSuccess = (data) => ({ type: userConstants.UPDATE_SUCCESS, data });

export const updateFail = (error) => ({ type: userConstants.UPDATE_FAIL, error });