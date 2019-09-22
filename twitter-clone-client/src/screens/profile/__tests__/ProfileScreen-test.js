import React from 'react';
import NavigationTestUtils from 'react-navigation/NavigationTestUtils';
import renderer from 'react-test-renderer';

import { ProfileScreen } from '../ProfileScreen';

jest.mock('react-native-elements', () => ({
    Image: 'Image',
    Avatar: 'Avatar',
    Text: 'Text'
}));

jest.mock('../../../components/TabViewComponent', () => 'TabViewComponent');
jest.mock('../../../components/RoundButton', () => 'RoundButton');

describe('ProfileScreen', () => {
    jest.useFakeTimers();

    beforeEach(() => {
        NavigationTestUtils.resetInternalState();
    });

    it(`renders current user's profile`, () => {
        const tree = renderer.create(<ProfileScreen user={{ email: 'current@mail.com' }} />).toJSON();
        expect(tree).toMatchSnapshot();
    });

    it(`renders another user's profile`, () => {
        const tree = renderer.create(<ProfileScreen />).toJSON();
        expect(tree).toMatchSnapshot();
    });
});
