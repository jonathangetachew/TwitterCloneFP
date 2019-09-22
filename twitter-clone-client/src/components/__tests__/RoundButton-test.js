import React from 'react';
import NavigationTestUtils from 'react-navigation/NavigationTestUtils';
import renderer from 'react-test-renderer';

import RoundButton from '../RoundButton';


jest.mock('react-native-elements', () => ({
  Button: 'Button'
}));

describe('RoundButton', () => {
  jest.useFakeTimers();

  beforeEach(() => {
    NavigationTestUtils.resetInternalState();
  });

  it(`renders the button`, () => {
    const tree = renderer.create(<RoundButton />).toJSON();
    expect(tree).toMatchSnapshot();
  });
});
