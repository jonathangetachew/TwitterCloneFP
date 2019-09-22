import NavigationTestUtils from 'react-navigation/NavigationTestUtils';
import tweetConstants from '../../constants/tweets';

describe('TweetsConstants', () => {
    jest.useFakeTimers();

    beforeEach(() => {
        NavigationTestUtils.resetInternalState();
    });

    it(`render constants`, () => {
        expect(tweetConstants).toMatchSnapshot();
    });
});