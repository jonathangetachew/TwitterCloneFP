import React from 'react';
import NavigationTestUtils from 'react-navigation/NavigationTestUtils';
import renderer from 'react-test-renderer';

import TabBarIcon from '../TabBarIcon';

jest.mock('@expo/vector-icons', () => ({
    Ionicons: 'Ionicons'
}));

describe('TabBarIcon', () => {
    jest.useFakeTimers();

    beforeEach(() => {
        NavigationTestUtils.resetInternalState();
    });

    it(`renders the icon focused`, () => {
        const tree = renderer.create(<TabBarIcon focused />).toJSON();
        expect(tree).toMatchSnapshot();
    });

    it(`renders the icon normal`, () => {
        const tree = renderer.create(<TabBarIcon />).toJSON();
        expect(tree).toMatchSnapshot();
    });
});
