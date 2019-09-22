import React from 'react';
import NavigationTestUtils from 'react-navigation/NavigationTestUtils';
import renderer from 'react-test-renderer';

import { TweetList } from '../TweetList';

const props = {
    getTweets: jest.fn()
}

jest.mock('../TweetCard', () => 'TweetCard');

describe('TweetList', () => {
    jest.useFakeTimers();

    beforeEach(() => {
        NavigationTestUtils.resetInternalState();
    });

    it(`renders the title`, () => {
        const tree = renderer.create(<TweetList title="Title" {...props} />).toJSON();
        expect(tree).toMatchSnapshot();
    });

    it(`renders without the title`, () => {
        const tree = renderer.create(<TweetList {...props} />).toJSON();
        expect(tree).toMatchSnapshot();
    });

    it(`renders render item`, () => {
        const tree = renderer.create(new TweetList().renderItem({ item: { name: 'Name' } })).toJSON();
        expect(tree).toMatchSnapshot();
    });

    it(`renders render fixed header`, () => {
        const tree = renderer.create(new TweetList({ title: "Title" }).renderFixedHeader()).toJSON();
        expect(tree).toMatchSnapshot();
    });

    it(`renders render separator`, () => {
        const tree = renderer.create(new TweetList().renderSeperator()).toJSON();
        expect(tree).toMatchSnapshot();
    });

    it(`renders render separator`, () => {
        expect(new TweetList().handleLoadMore()).toEqual(undefined);
    });
});
