import React from "react";
import { Image } from "react-native";
import { Text } from "react-native-elements";

export default class LogoTitle extends React.Component {
  render() {
    return (
      <React.Fragment>
        <Image
          source={require("../../assets/images/logo.png")}
          style={{ width: 34, height: 21 }}
        />
        <Text h3>{this.props.title}</Text>
      </React.Fragment>
    );
  }
}
