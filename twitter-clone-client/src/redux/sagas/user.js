import { call, put, takeLatest } from 'redux-saga/effects';
import userConstants from '../constants/user';
import UserService from '../services/user.service';
import * as userActions from '../actions/user';

function* login(action) {
    yield put(userActions.loginRequest());
    try {
        const data = yield call(UserService.signin, {
            identifier: action.identifier,
            password: action.password
        });

        yield put(userActions.loginSuccess({ token: data.jwt, user: data.user }));
    } catch (error) {
        let errorMessage = 'Error when logging in, please try again later';
        if (error && error.message) {
            errorMessage = error.message
        }

        yield put(loginFail(errorMessage));
    }
}

function* register(action) {
    yield put(userActions.requestSignUp());
    try {
        const data = yield call(UserService.signup, {
            username: action.username,
            email: action.email,
            password: action.password
        });

        yield put(userActions.successSignUp({ token: data.jwt, user: data.user }));
    } catch (error) {
        let errorMessage = 'Error when registering an profile, please try again later';
        if (error && error.message) {
            errorMessage = error.message
        }

        yield put(userActions.failedSignUp(errorMessage));
    }
}

function* logout() {
    yield put(userActions.logout());
}

function* updateSelf(action) {
    yield put(userActions.updateRequest())
    try {
        const data = yield call(UserService.update, {
            ...action.user
        })
        yield put(userActions.updateSuccess(data))
    } catch (error) {
        let errorMessage = 'reset-password-error';
        if (error && error.message) {
            errorMessage = error.message
        }
        yield put(userActions.updateFail(errorMessage))
    }
}

export default [
    takeLatest(userConstants.LOGIN, login),
    takeLatest(userConstants.REGISTER, register),
    takeLatest(userConstants.LOGOUT, logout),
    takeLatest(userConstants.UPDATE, updateSelf)
];
