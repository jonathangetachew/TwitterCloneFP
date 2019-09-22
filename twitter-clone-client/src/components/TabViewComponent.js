import React from 'react';
import { Dimensions, View } from 'react-native';

import { TabView, TabBar, SceneMap } from 'react-native-tab-view';
import Colors from '../constants/Colors';

export default class TabViewComponent extends React.Component {
    constructor(props) {
        super();
        this.state = {
            index: 0,
            routes: props.routes.map(({ key, title }, index) => ({ key: key || index, title })) || [],
        };
    }

    getSceneDescriptor() {
        const { routes } = this.props;
        const descriptor = {};
        if (routes) {
            routes.forEach(({ key, component }, index) => descriptor[key || index] = component);
        }
        return SceneMap(descriptor);
    }

    render() {
        return (
            <View style={{ marginTop: 10, width: Dimensions.get('window').width }}>
                <TabView
                    navigationState={this.state}
                    renderTabBar={props =>
                        <TabBar
                            {...props}
                            getLabelText={({ route }) => route.title}
                            activeColor={Colors.primaryColor}
                            inactiveColor={Colors.inactiveColor}
                            labelStyle={{ fontWeight: 'bold' }}
                            indicatorStyle={{ backgroundColor: Colors.primaryColor }}
                            style={{ backgroundColor: 'white' }}
                        />
                    }
                    renderScene={this.getSceneDescriptor()}
                    onIndexChange={index => this.setState({ index })}
                    initialLayout={{ width: 200, height: 100 }}
                />
            </View>
        );
    }
}