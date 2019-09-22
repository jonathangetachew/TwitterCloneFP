import { createStackNavigator } from 'react-navigation-stack';
import SignInScreen from './SignInScreen';
import SignUpScreen from './SignUpScreen';
import WelcomeScreen from './WelcomeScreen';

const AuthStack = createStackNavigator({
    Welcome: WelcomeScreen,
    SignIn: SignInScreen,
    SignUp: SignUpScreen
}, {
    initialRouteName: 'Welcome',
});

//AuthStack.path = '';

export default AuthStack;