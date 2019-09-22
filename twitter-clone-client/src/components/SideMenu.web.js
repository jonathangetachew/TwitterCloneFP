import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { NavigationActions } from 'react-navigation';
import { StyleSheet, StatusBar, ScrollView, View } from 'react-native';
import { Avatar, Icon, Text } from 'react-native-elements';
import Colors from '../constants/Colors';
import sidemenuDescriptor from '../navigation/sidemenu-descriptor';

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

    renderMenuItem(name, key) {
        const Component = sidemenuDescriptor[name];
        console.log("Icon", Component, this.props);
        return (
            <View style={{ padding: 10, flexDirection: 'row', alignItems: 'center' }} key={key}>
                {(Component.navigationOptions && Component.navigationOptions.drawerIcon) && Component.navigationOptions.drawerIcon()}
                <Text style={{ marginStart: 10, fontSize: 20, color: Colors.primaryColor, fontWeight: 'bold' }}>{Component.navigationOptions && Component.navigationOptions.drawerLabel ? Component.navigationOptions.drawerLabel : name}</Text>
            </View>
        );
    }

    renderMenuOptions() {
        console.log("Cosa", sidemenuDescriptor, this.props.onlyIcons);
        return (
            <ScrollView>
                <View style={{ paddingHorizontal: 50 }}>
                    {sidemenuDescriptor && Object.keys(sidemenuDescriptor).map((item, index) => this.renderMenuItem(item, index))}
                </View>
            </ScrollView>
        );
    }

    render() {
        const { expanded } = this.state;
        //console.log("USER_TOKEN", this.props.user);
        return (
            <View style={styles.container}>
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
        alignItems: 'stretch',
        flex: 1
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

export default SideMenu;