import React from "react";
import {
  AsyncStorage,
  View,
  ScrollView,
  KeyboardAvoidingView,
  Text,
  Image,
  Platform
} from "react-native";
import { Header } from "react-navigation";
import { CheckBox } from "react-native-elements";
import RoundButton from "../../components/RoundButton";
import Colors from "../../constants/Colors";
import Icon from "react-native-vector-icons/FontAwesome";
import imageLogo from "../../../assets/images/logo.png";
import { default as Input } from "../../components/RespectFlexInput";

const LoginHeader = props => (
  <View
    style={{
      flex: 1,
      flexDirection: "row-reverse",
      color: Colors.primaryColor
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
    <Text
      h3
      style={{ color: Colors.primaryColor, marginHorizontal: 20 }}
      onPress={() => {
        props.onSignupPress();
      }}
    >
      Sign up
    </Text>
  </View>
);

export default class SignInScreen extends React.Component {
  state = {
    username: "",
    usernameError: null,
    password: "",
    passwordError: null,
    rememberMe: false
  };
  static navigationOptions = ({ navigation }) => ({
    headerTitle: navigation.getParam("headerTitle", null),
    header: Platform.OS != "web" ? undefined : null
  });

  componentDidMount() {
    if (Platform.OS != "web")
      this.props.navigation.setParams({
        headerTitle: <LoginHeader onSignupPress={this._signUp} />
      });
    else this.props.navigation.setParams({ header: null });
  }
  render() {
    return (
      <KeyboardAvoidingView
        keyboardVerticalOffset={Header.HEIGHT + 20}
        style={{ }}
        behavior="padding"
        enabled
      >
        <ScrollView>
          <View
            style={{
              flex: 1,
              backgroundColor: "white",
              alignItems: "stretch",
              padding: Platform.OS == "web" ? undefined : "20%",
              paddingHorizontal: Platform.OS == "web" ? "40%" : undefined
            }}
          >
            <View style={{ alignItems: "center" }}>
              <Image
                source={imageLogo}
                style={{
                  width: 95,
                  height: 80,
                  resizeMode: "contain",
                  marginTop: 30
                  //marginLeft: -10,
                }}
              />
            </View>
            <View
              style={{
                flexDirection: "column",
                alignItems: "stretch"
              }}
            >
              <Text style={{ fontSize: 27, marginBottom: 20 }}>
                Log in to TwitterClone
              </Text>
              <Input
                label="Username"
                autoFocus={true}
                errorMessage={this.state.usernameError}
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
                onChangeText={value => this.setState({ username: value })}
                value={this.state.value}
              />
              <Input
                label="Password"
                errorMessage={this.state.passwordError}
                leftIcon={
                  <Icon
                    name="lock"
                    size={24}
                    color={Colors.primaryColor}
                    style={{ marginRight: 8 }}
                  />
                }
                textContentType="password"
                autoCompleteType="password"
                secureTextEntry={true}
                onChangeText={value => this.setState({ password: value })}
              />
              <View
                style={{
                  justifyContent: "center",
                  alignItems: "stretch",
                  marginTop: 14
                }}
              >
                <RoundButton title="Log in!" onPress={this._signInAsync} />
                <CheckBox
                  style={{ flex: 1 }}
                  checked={this.state.rememberMe}
                  onPress={() =>
                    this.setState({
                      rememberMe: !this.state.rememberMe
                    })
                  }
                  title="Remember me"
                ></CheckBox>
              </View>

              <View
                style={{
                  flex: 1,
                  flexDirection: "row",
                  justifyContent: "center",
                  alignItems: "center"
                }}
              >
                <Text style={this.styles.spacedThingyForJonathansEyes}>
                  New to TwitterClone?
                </Text>
                <Text style={{ color: "blue" }} onPress={this._signUp}>
                  Sign up
                </Text>
              </View>
            </View>
          </View>
        </ScrollView>
      </KeyboardAvoidingView>
    );
  }

  _signUp = () => this.props.navigation.navigate("SignUp");

  _signInAsync = async () => {
    let errorObject = {};
    if (this.state.username.length == 0)
      errorObject.usernameError = "Please, inform a valid username";
    else errorObject.usernameError = null;
    if (this.state.password.length == 0)
      errorObject.passwordError = "Please, inform a valid password";
    else errorObject.passwordError = null;
    if (errorObject.usernameError != null || errorObject.passwordError != null)
      this.setState(errorObject);
    else {
      this.setState({
        usernameError: null,
        passwordError: null
      });
      await AsyncStorage.setItem("userToken", "abc");
      this.props.navigation.navigate("App");
    }
  };

  styles = {
    moreSpacedThingyForJonathansEyes: {
      margin: 5
    },
    spacedThingyForJonathansEyes: {
      margin: 5
    }
  };
}
