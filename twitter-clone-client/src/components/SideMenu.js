import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { NavigationActions, DrawerItems, createDrawerNavigator } from 'react-navigation';
import { StyleSheet, StatusBar, ScrollView, View } from 'react-native';
import { Avatar, Icon, Text } from 'react-native-elements';
import Colors from '../constants/Colors';
import sidemenuDescriptor from '../navigation/sidemenu-descriptor';

let UserComponent = (props) => (
    <View style={styles.userInfo}>
        <Avatar
            rounded
            size="large"
            source={{
                uri: props.avatar_url || 'https://s3.amazonaws.com/uifaces/faces/twitter/ladylexy/128.jpg',
            }}
        />
        <View style={{ marginTop: 10, flexDirection: 'row', justifyContent: 'space-between' }}>
            <View>
                <Text h3 h3Style={styles.textNameStyle}>{props.name || 'Jane Doe'}</Text>
                <Text style={{ color: '#5c5c5c' }}>{props.username || '@JaneDoe'}</Text>
            </View>
            <View>
                <Icon name={props.expanded ? "expand-more" : "expand-less"} color={Colors.primaryColor} onPress={props.onToggleExpand ? props.onToggleExpand : () => { }} />
            </View>
        </View>
        <View style={{ flexDirection: 'row', marginTop: 10 }}>
            <View style={{ flexDirection: 'row', marginEnd: 10 }}>
                <Text style={styles.numericBold}>{props.following || 6}</Text>
                <Text>Following</Text>
            </View>
            <View style={{ flexDirection: 'row' }}>
                <Text style={styles.numericBold}>{props.followers || 3}</Text>
                <Text>Followers</Text>
            </View>
        </View>
    </View>
);

UserComponent = connect(({ user }) => ({ user }))(UserComponent);

/**
 * Code idea obtained from https://codeburst.io/custom-drawer-using-react-navigation-80abbab489f7
 */
class SideMenu extends Component {
    state = {
        expanded: true
    };

    constructor() {
        super();

        this._onToggleExpand = this._onToggleExpand.bind(this);
    }
    navigateToScreen = (route) => () => {
        const navigateAction = NavigationActions.navigate({
            routeName: route
        });
        this.props.navigation.dispatch(navigateAction);
    }

    _onToggleExpand() {
        const { expanded } = this.state;
        this.setState({ expanded: !expanded });
    }

    renderOtherOptions() {
        return null;
    }

    renderMenuOptions() {
        return (
            <ScrollView>
                <DrawerItems {...this.props} />
            </ScrollView>
        );
    }

    render() {
        const { expanded } = this.state;
        //console.log("USER_TOKEN", this.props.user);
        return (
            <View style={styles.container}>
                <UserComponent expanded={expanded} onToggleExpand={this._onToggleExpand} />
                {expanded ? this.renderMenuOptions() : this.renderOtherOptions()}
            </View>
        );
    }
}

SideMenu.propTypes = {
    navigation: PropTypes.object
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'stretch',
    },
    userInfo: {
        padding: 20,
        marginTop: StatusBar.currentHeight,
        borderBottomColor: '#ececec',
        borderBottomWidth: 1
    },
    textNameStyle: {
        fontSize: 18,
        fontWeight: 'bold'
    },
    numericBold: {
        fontWeight: 'bold',
        marginEnd: 5
    }
});

export default createDrawerNavigator(sidemenuDescriptor, { contentComponent: SideMenu });