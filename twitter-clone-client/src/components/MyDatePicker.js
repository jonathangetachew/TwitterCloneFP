import React from "react";
import moment from 'moment';
import { View, Text } from "react-native";
import Colors from "../constants/Colors";
import { Platform } from "react-native";
import { default as MobileDatePicker } from "react-native-datepicker";
import { default as Input } from "../components/RespectFlexInput";

const Label = props => (
  <Text style={{ fontSize: 16, color: Colors.labelColor, fontWeight: "bold" }}>
    {props.children}
  </Text>
);

var exportFunc;

if (Platform.OS == "web") {
  exportFunc = props => (
    <Input
      errorMessage={props.errorMessage}
      placeHolder="MM-DD-YYYY"
      label={props.label}
                  onChangeText={value => {
                    let d = moment(value, "MM-DD-YYYY");
                    if(value.length == "MM-DD-YYYY".length && d.isValid()) {
                      if(d < props.maxDate && d.year() >= 1890) {
                        props.onDateChange(value);
                      } else
                        props.onError('Date is weird.');
                    } else
                      props.onError('Invalid date.');
                      /*props.onDateChange
                      userData: {
                        ...this.state.userData,
                        confirmPassword: value
                      }*/
                    }
                  }
                />
  );
} else {
    exportFunc = props => (
        <View style={{ paddingHorizontal: 10 }}>
          <Label>{props.label}</Label>
          <MobileDatePicker
            {...props}
            style={{ borderBottomWidth: 1, borderColor: Colors.labelColor }}
            customStyles={{ dateInput: { border: 0, borderColor: "transparent" } }}
          />
        </View>
      );
}

export default exportFunc
