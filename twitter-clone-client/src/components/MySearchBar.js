import React, { Component } from 'react';
import {
  StyleSheet,
  View,
  TextInput,
  Animated
} from 'react-native';

import {
  SearchBar,
  Icon
} from 'react-native-elements';
import constants from '../redux/actions/tweets';
import { connect } from 'react-redux';
import { getTweets, searchTweets } from '../redux/actions/tweets';

class MySearchBar extends Component {
  constructor() {
    super();
    this.state = {
      dataSource: [],
      isLoading: true,
      search: '',
      isFocused: false
    }
  }

  updateSearch = search => {
    this.setState({ search });
  };

  onFocusChange = () => {
    this.setState({ isFocused: true });
  }
  unFocusChange = () => {
    this.setState({ isFocused: false });
  }
  searchChange = (searchString) => {
    this.setState({ searchString });
    searchString ? this.props.searchTweet(searchString) : this.props.getTweets();
  }
  render() {
    const { search } = this.state;

    /*return (
      <SearchBar
        lightTheme
        platform = "ios"
        round
        placeholder="Search Twitter..."
        onChangeText={this.updateSearch}
        value={search}
        style= {styles.searchbar} 
        containerStyle={styles.searchcontainer}
        inputStyle={styles.input}
      />
    );*/
    return (
      <View style={(this.state.isFocused) ? styles.searchSectionFocus : styles.searchSection}>
        <Icon style={styles.searchIcon} name="search" size={20} color="#000" />
        <TextInput
          style={(this.state.isFocused) ? styles.inputFocus : styles.input}
          placeholder="Search Tweets..."
          onChangeText={(searchSTRING) => this.searchChange(searchSTRING)}
          onFocus={this.onFocusChange}
          onBlur={this.unFocusChange}
        />
      </View>
    )
  }
}

const styles = StyleSheet.create({
  searchcontainer: {
    backgroundColor: 'white',
    borderWidth: 0, //no effect
    shadowColor: 'white', //no effect
    borderBottomColor: 'transparent',
    borderTopColor: 'transparent',
    marginBottom: 20
  },
  searchbar: {
    width: "100%",
    backgroundColor: 'red', //no effect
    borderWidth: 0, //no effect
    shadowColor: 'white' //no effect
  },
  searchSection: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f7f7f7',
    borderRadius: 15,
    borderWidth: 1,
    borderColor: "#f7f7f7", //"#1da1f2",
    paddingRight: 10,
    paddingLeft: 10,
  },
  searchSectionFocus: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderRadius: 15,
    borderWidth: 1,
    borderColor: "#1da1f2", //"#1da1f2",
    paddingRight: 10,
    paddingLeft: 10
  },
  searchIcon: {
    padding: 10,
    color: '#424242'
  },
  input: {
    flex: 1,
    paddingTop: 5,
    paddingRight: 10,
    paddingBottom: 5,
    paddingLeft: 0,
    backgroundColor: '#f7f7f7',
    color: '#424242',
    fontSize: 16,
    marginLeft: 5,
    width: 500
  },
  inputFocus: {
    flex: 1,
    paddingTop: 5,
    paddingRight: 10,
    paddingBottom: 5,
    paddingLeft: 0,
    fontSize: 16,
    marginLeft: 5,
    backgroundColor: '#fff',
    color: '#424242',
    borderWidth: 0,
    borderColor: 'transparent',
    width: 500
  }
})

export default connect(({ tweets }) => ({
  tweetData: Object.values(tweets.data),
  loading: tweets.loading
}), dispatch => ({
  getTweets: () => {
    dispatch(getTweets());
  },
  searchTweet: (criteria) => {
    dispatch(searchTweets(criteria));
  }
}))(MySearchBar);