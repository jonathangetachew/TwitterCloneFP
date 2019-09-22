import React from 'react';
import {
    View,
    StyleSheet,
    FlatList,
    ActivityIndicator
} from 'react-native';
import { connect } from 'react-redux';
import { getTweets } from '../redux/actions/tweets';
import { Text } from "react-native-elements";
import TweetCard from './TweetCard';

export class TweetList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            dataSource: [],
            isLoading: true
        }
    }

    componentDidMount() {
        this.props.getTweets();
    }

    renderItem = ({ item }) => {
        return (
            <TweetCard {...item} />
        );
    }

    renderFixedHeader = () => {
        const { title } = this.props;
        return title ? (
            <View style={styles.header_style}>
                <Text h2 h2Style={{ marginTop: 10, marginBottom: 3, paddingBottom: 3, fontWeight: "700", fontSize: 22, paddingLeft: 5 }}>{title}</Text>
                <View style={{ width: '100%', height: 1, backgroundColor: '#ccc' }} />
            </View>
        ) : null;
    }
    renderSeperator = () => {
        return (
            <View style={{ width: '100%', height: 1, backgroundColor: '#ccc', marginVertical: 5 }} />
        );
    }

    handleLoadMore = () => {
        console.log("Warn load more");
    }
    render() {
        const { tweetData, loading, error } = this.props;
        if (loading) return <ActivityIndicator />
        return (
            <View style={styles.container}>
                <FlatList
                    data={tweetData}
                    renderItem={this.renderItem}
                    keyExtractor={(item, id) => id.toString()}
                    ItemSeparatorComponent={this.renderSeperator}
                    ListHeaderComponent={this.renderFixedHeader}
                    stickyHeaderIndices={[0]}
                    onEndReached={this.handleLoadMore}
                    onEndReachedThreshold={0}
                />
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: '#FFF',
        maxWidth: 600,
        borderLeftWidth: 1,
        borderLeftColor: '#e6ecf0',
        borderRightWidth: 1,
        borderRightColor: '#e6ecf0',
    },
    profileimage: {
        width: 50,
        height: 50,
        borderRadius: 25,
        marginHorizontal: 5,
        marginTop: 5,
        overflow: "hidden",
    },
    infocontainer: {
        flex: 1,
        justifyContent: 'flex-start'
    },
    header: {
        marginBottom: 15,
        paddingBottom: 15
    },
    header_style: {
        backgroundColor: 'white',
        borderBottomWidth: 1,
        borderBottomColor: '#CCC',
        paddingHorizontal: 2
    },
    subtext: {
        marginRight: 10,
        color: '#657786',
        fontSize: 15,
        position: "absolute",
        top: -2,
        left: 20
    },
    iconView: {
        marginLeft: 1,
        flex: 1,
        flexDirection: "row"
    }
});

export default connect(({ tweets }) => ({
    tweetData: Object.values(tweets.data),
    loading: tweets.loading
}), dispatch => ({
    getTweets: () => dispatch(getTweets())
}))(TweetList);