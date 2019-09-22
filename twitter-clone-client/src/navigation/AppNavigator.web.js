import { createBrowserApp } from '@react-navigation/web';
import CommonNavigator from './CommonNavigator';
CommonNavigator.path = '';

export default createBrowserApp(CommonNavigator, { history: 'hash' });
