import reducer, { initialState } from '../../reducers/user'
import * as userActions from '../../actions/user';

const userData = {
    username: 'Name',
    token: 'token',
    email: 'user@mail.com',
    _id: 0
}

describe('Tweets reducer', () => {
    it('should return the initial state', () => {
        expect(reducer(undefined, {})).toEqual(initialState)
    })

    it('should handle Login request', () => {
        expect(reducer(initialState, userActions.loginRequest())).toEqual({ ...initialState, loading: true })
    })

    it('should handle Login success', () => {
        const token = 'token';
        expect(reducer(initialState, userActions.loginSuccess({ user: userData, token }))).toEqual({ ...initialState, loading: false, ...userData })
    })

    it('should handle Login fail', () => {
        expect(reducer(initialState, userActions.loginFail("Error"))).toEqual({ ...initialState, loading: false, error: "Error" })
    })

    it('should handle sign up request', () => {
        expect(reducer(initialState, userActions.requestSignUp())).toEqual({ ...initialState, loading: true })
    })

    it('should handle sign up success', () => {
        const token = 'token';
        expect(reducer(initialState, userActions.successSignUp({ user: userData, token }))).toEqual({ ...initialState, loading: false, ...userData })
    })

    it('should handle signup fail', () => {
        expect(reducer(initialState, userActions.failedSignUp("Error"))).toEqual({ ...initialState, loading: false, error: "Error" })
    })

    it('should handle update request', () => {
        expect(reducer(initialState, userActions.updateRequest())).toEqual({ ...initialState, loading: true })
    })

    it('should handle update success', () => {
        const token = 'token';
        expect(reducer(initialState, userActions.updateSuccess({ ...userData, token }))).toEqual({ ...initialState, loading: false, ...userData })
    })

    it('should handle update fail', () => {
        expect(reducer(initialState, userActions.updateFail("Error"))).toEqual({ ...initialState, loading: false, error: "Error" })
    })
})