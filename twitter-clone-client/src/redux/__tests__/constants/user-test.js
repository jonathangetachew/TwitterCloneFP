import NavigationTestUtils from 'react-navigation/NavigationTestUtils';
import userConstants from '../../constants/user';

describe('UserConstants', () => {
    jest.useFakeTimers();

    beforeEach(() => {
        NavigationTestUtils.resetInternalState();
    });

    it(`render constants`, () => {
        expect(userConstants).toMatchSnapshot();
    });
});