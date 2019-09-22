import React from 'react';
import { StyleSheet, TouchableOpacity, View } from 'react-native';
import { Avatar, Text, Icon } from 'react-native-elements';

export default function TweetCard({ picture, name, username, body }) {
    return (
        <TouchableOpacity
            style={styles.touchableStyle}
        >
            <Avatar
                style={styles.profileimage}
                source={{ uri: picture }}
            />
            <View style={{ flex: 1 }}>
                <View style={{ flexDirection: 'row' }}>
                    <Text style={{ fontSize: 15, color: '#000000', marginRight: 5, fontWeight: 'bold' }}>{name}</Text>
                    <Text style={{ fontSize: 15, color: '#777' }}>@{username}</Text>
                </View>
                <Text>{body}</Text>
                <View style={{ flex: 1, flexDirection: "row", justifyContent: "space-around" }}>
                    <View style={styles.iconView}>
                        <Icon
                            name='comment'
                            type='font-awesome'
                            color='#657786'
                            size={18}
                        />
                        <Text style={styles.subtext}>1</Text>
                    </View>
                    <View style={styles.iconView}>
                        <Icon
                            name='retweet'
                            type='font-awesome'
                            color='#657786'
                            size={18}
                        />
                        <Text style={styles.subtext}>1</Text>
                    </View>
                    <View style={styles.iconView}>
                        <Icon
                            name='heart'
                            type='font-awesome'
                            color='#657786'
                            size={18}
                        />
                        <Text style={styles.subtext}>1</Text>
                    </View>
                </View>
            </View>
        </TouchableOpacity>
    );
};

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
    touchableStyle: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'row',
        marginBottom: 3
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