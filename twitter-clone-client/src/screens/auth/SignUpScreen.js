import React from "react";
import {
  Image,
  KeyboardAvoidingView,
  View,
  Text,
  Platform,
  ScrollView
} from "react-native";
import { Header } from "react-navigation";
import Icon from "react-native-vector-icons/FontAwesome";
import Colors from "../../constants/Colors";
import RoundButton from "../../components/RoundButton";
import MyDatePicker from "../../components/MyDatePicker";
import ReactResizeDetector from "react-resize-detector";
import { default as Input } from "../../components/RespectFlexInput";

const LoginHeader = props => (
  <View
    style={{
      flex: 1,
      alignItems: "center"
    }}
  >
    <Image
      source={require("../../../assets/images/logo.png")}
      style={{
        resizeMode: "contain",
        width: 25,
        height: 25,
        marginHorizontal: 20
      }}
    />
  </View>
);

export default class SignUpScreen extends React.Component {
  state = {
    userData: {
      username: "",
      email: "",
      birthDate: null,
      password: "",
      confirmPassword: ""
    },
    error: {
      username: null,
      email: null,
      birthDate: null,
      password: null
    }
  };
  static navigationOptions = ({ navigation }) => ({
    headerTitle: <LoginHeader />,
    header: Platform.OS != "web" ? undefined : null
  });

  render() {
    return (
      <KeyboardAvoidingView
        keyboardVerticalOffset={Header.HEIGHT + 20}
        style={{ flex: 1 }}
        behavior="padding"
        enabled
      >
        <ScrollView
          contentContainerStyle={{ marginHorizontal: "20%" }}
        >
          <ReactResizeDetector handleWidth handleHeight>
            {({ width, height }) => (
              <View
                style={{
                  maxWidth: 500,
                  flex: 1,
                  marginHorizontal: "auto",
                  width: "100%"
                }}
              >
                <Text style={{ fontSize: 27, marginBottom: 20, marginTop: 30 }}>
                  Create Your account
                </Text>
                <Input
                  label="Username"
                  autoFocus={true}
                  errorMessage={this.state.error.username}
                  leftIcon={
                    <Icon
                      name="user"
                      size={24}
                      color={Colors.primaryColor}
                      style={{ marginRight: 8 }}
                    />
                  }
                  maxLength={20}
                  textContentType="username"
                  onChangeText={value =>
                    this.setState({
                      userData: { ...this.state.userData, username: value }
                    })
                  }
                  value={this.state.userData.username}
                />
                <Input
                  label="Email address"
                  errorMessage={this.state.error.email}
                  leftIcon={
                    <Icon
                      name="envelope"
                      size={24}
                      color={Colors.primaryColor}
                      style={{ marginRight: 8 }}
                    />
                  }
                  onChangeText={value =>
                    this.setState({
                      userData: { ...this.state.userData, email: value }
                    })
                  }
                  value={this.state.userData.email}
                />
                <Input
                  label="Password"
                  leftIcon={
                    <Icon
                      name="lock"
                      size={24}
                      color={Colors.primaryColor}
                      style={{ marginRight: 8 }}
                    />
                  }
                  textContentType="password"
                  secureTextEntry={true}
                  onChangeText={value =>
                    this.setState({
                      userData: { ...this.state.userData, password: value }
                    })
                  }
                />
                <Input
                  label="Confirm Password"
                  errorMessage={this.state.error.password}
                  leftIcon={
                    <Icon
                      name="lock"
                      size={24}
                      color={Colors.primaryColor}
                      style={{ marginRight: 8 }}
                    />
                  }
                  textContentType="password"
                  secureTextEntry={true}
                  onChangeText={value =>
                    this.setState({
                      userData: {
                        ...this.state.userData,
                        confirmPassword: value
                      }
                    })
                  }
                />
                <MyDatePicker
                  label="Date Of Birth"
                  errorMessage={this.state.error.birthDate}
                  maxDate={new Date()}
                  date={this.state.userData.birthDate}
                  style={{borderBottomWidth: 1, borderColor: Colors.labelColor}}
                  customStyles={{dateInput:{border: 0, borderColor: "transparent"}}}
                  onError={err => {
                    this.setState({
                      error: { ...this.state.error, birthDate: err },
                      userData: { ...this.state.userData, birthDate: null }
                    });
                  }}
                  onDateChange={date => {
                    this.setState({
                      error: { ...this.state.error, birthDate: null },
                      userData: { ...this.state.userData, birthDate: date }
                    });
                  }}
                />
                <View
                  style={{
                    marginTop: 14,
                    justifyContent: "center",
                    alignItems: "stretch"
                  }}
                >
                  <RoundButton title="Sign up" onPress={this._signup} />
                </View>
              </View>
            )}
          </ReactResizeDetector>
        </ScrollView>
      </KeyboardAvoidingView>
    );
  }

  // validateEmail function
  // copied from https://github.com/react-native-elements/react-native-elements-app/blob/master/src/views/login/screen1.js
  validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    return re.test(email);
  }

  _signup = () => {
    let errorObject = {};
    if (this.state.userData.username.length == 0)
      errorObject.username =
        "Please don't try to think outside the box in this system.";
    else errorObject.username = null;
    if (
      this.state.userData.email.length == 0 ||
      !this.validateEmail(this.state.userData.email)
    )
      errorObject.email = "We need a valid email to create your account.";
    else errorObject.email = null;
    if (this.state.userData.password != this.state.userData.confirmPassword)
      errorObject.password = "The passwords don't match.";
    else if (this.state.userData.password.length < 6)
      errorObject.password =
        "Please, make sure your password is at least 6 characters long.";
    else errorObject.password = null;
    if (this.state.userData.birthDate == null)
      errorObject.birthDate =
        "Please, check your birth date.";
    else errorObject.birthDate = null;
    if (
      errorObject.username != null ||
      errorObject.email != null ||
      errorObject.password != null ||
      errorObject.birthDate != null
    )
      this.setState({ error: errorObject });
    else {
      console.log(this.state.userData);
      this.props.navigation.navigate("SignIn");
    }
  };
}
