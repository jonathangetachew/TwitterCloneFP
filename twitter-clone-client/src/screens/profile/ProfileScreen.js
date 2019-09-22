import React from 'react';
import { ActivityIndicator, Platform, StyleSheet, View, ScrollView } from 'react-native';
import { Avatar, Text, Icon, Image } from 'react-native-elements';
import { connect } from 'react-redux';
import Colors from '../../constants/Colors';
import RoundButton from '../../components/RoundButton';
import TabBarIcon from '../../components/TabBarIcon';
import TabViewComponent from '../../components/TabViewComponent';
import TweetList from '../../components/TweetList';

const FirstRoute = () => (
    <View style={[styles.scene, { height: 300 }]}>
        <Text>a</Text>
    </View>
);

const SecondRoute = () => (
    <View style={[styles.scene]} />
);

export class ProfileScreen extends React.Component {
    renderActions() {
        const { user } = this.props;
        if (user && user.email) {// Todo: Add condition to check if is current user's profile or not.
            return (
                <View style={styles.actionButtonStyles}>
                    <RoundButton
                        title={"Edit profile"}
                        type="outline"
                    />
                </View>
            );
        }

        return (
            <View style={styles.actionButtonStyles}>
                <RoundButton
                    type="outline"
                    icon={{ name: "mail-outline", color: Colors.primaryColor }}
                />
                <RoundButton
                    type="outline"
                    icon={{ name: "add-alert", color: Colors.primaryColor }}
                />
                <RoundButton
                    title={"Following"}
                />
            </View>
        );
    }
    render() {
        const { background, avatar_url, name, username, description, followers, following } = this.props;
        return (
            <ScrollView>
                <View style={styles.container}>
                    <Image
                        source={{ uri: background || "https://cdn.pixabay.com/photo/2018/08/31/18/21/fantasy-3645269_960_720.jpg" }}
                        style={{ width: '100%', height: 200 }}
                        PlaceholderContent={<ActivityIndicator />}
                    />
                    <Avatar
                        rounded
                        containerStyle={styles.avatarStyle}
                        size={120}
                        source={{
                            uri:
                                avatar_url || 'https://s3.amazonaws.com/uifaces/faces/twitter/ladylexy/128.jpg'
                        }}
                    />
                    {this.renderActions()}
                    <View style={styles.profileInfo}>
                        <Text h3>{name || "Jane Doe"}</Text>
                        <Text style={{ color: '#5c5c5c', fontSize: 20 }}>{username || '@JaneDoe'}</Text>
                        <Text style={{ marginTop: 5, fontSize: 20 }}>{description || 'Sample description'}</Text>
                        <View style={{ flexDirection: 'row', marginTop: 10 }}>
                            <View style={{ flexDirection: 'row', marginEnd: 10 }}>
                                <Text style={styles.numericBold}>{following || 6}</Text>
                                <Text>Following</Text>
                            </View>
                            <View style={{ flexDirection: 'row' }}>
                                <Text style={styles.numericBold}>{followers || 3}</Text>
                                <Text>Followers</Text>
                            </View>
                        </View>
                    </View>
                    <TabViewComponent routes={[
                        { title: 'Tweets', component: TweetList },
                        { title: 'Tweets & replies', component: SecondRoute },
                        { title: 'Media', component: FirstRoute },
                        { title: 'Likes', component: SecondRoute },
                    ]} />
                </View>
            </ScrollView>
        );
    }
}

ProfileScreen.navigationOptions = {
    drawerIcon: () => <Icon size={30} color={Colors.primaryColor} name={"person"} />,
    tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name={Platform.OS === 'ios' ? 'ios-person' : 'md-person'} />
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'stretch'
    },
    avatarStyle: {
        position: 'absolute',
        left: 5,
        top: 130,
        borderColor: '#fff',
        borderWidth: 2,
        //zIndex: 100,
        backgroundColor: 'red'
    },
    profileInfo: {
        paddingHorizontal: 10
    },
    numericBold: {
        fontWeight: 'bold',
        marginEnd: 5
    },
    actionButtonStyles: {
        alignSelf: 'flex-end',
        marginTop: 10,
        marginRight: 10,
        flexDirection: 'row',
        alignItems: 'center'
    },
    scene: {
        flex: 1,
    }
});

export default connect(({ user }) => ({ user: {...user, email:'current@mail.com'} }))(ProfileScreen);