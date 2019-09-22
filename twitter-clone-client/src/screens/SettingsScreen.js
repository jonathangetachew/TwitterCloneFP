import React from 'react';
import { AsyncStorage, View } from 'react-native';
import { ExpoConfigView } from '@expo/samples';
import LogoTitle from "../components/LogoTitle";
import RoundButton from '../components/RoundButton';

export default function SettingsScreen(props) {

  const _signOutAsync = async () => {
    await AsyncStorage.clear();
    props.navigation.navigate('Auth');
  };
  /**
   * Go ahead and delete ExpoConfigView and replace it with your content;
   * we just wanted to give you a quick view of your config.
   */
  return (
    <View>
      <RoundButton title="Sign Out" onPress={_signOutAsync} />
      <ExpoConfigView />
    </View>
  );
}

SettingsScreen.navigationOptions = {
  headerTitle: <LogoTitle title="Settings" />
};
