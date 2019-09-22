import React from 'react';
import { Input } from 'react-native-elements';
import {
    Platform,
  } from "react-native";

let style = { minWidth: 0, flex:1 };
if(Platform.OS == "web") {
    style.boxSizing = "border-box";
}
export default (props) => <Input {...props} inputStyle={[style, props.inputStyle]} />