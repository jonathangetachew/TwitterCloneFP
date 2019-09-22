import { createSwitchNavigator } from 'react-navigation';
import AuthStack from '../screens/auth';

import AuthLoadingScreen from '../screens/auth/AuthLoadingScreen';

import MainTabNavigator from './MainTabNavigator';

export default createSwitchNavigator({
  // You could add another route here for authentication.
  // Read more at https://reactnavigation.org/docs/en/auth-flow.html
  AuthLoading: AuthLoadingScreen,
  App: MainTabNavigator,
  Auth: AuthStack,
},
{
  initialRouteName: 'AuthLoading',
});