import { AppLoading } from "expo";
import { Asset } from "expo-asset";
import * as Font from "expo-font";
import React, { useState } from "react";
import {
  Platform,
  StatusBar,
  SafeAreaView,
  StyleSheet,
  View
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { ThemeProvider } from "react-native-elements";
// Added this imports
import { Provider } from "react-redux";
import { persistStore } from "redux-persist";
import { PersistGate } from "redux-persist/integration/react";
import storage from "./src/redux/store";
import rootSaga from "./src/redux/sagas";
import AppNavigator from "./src/navigation/AppNavigator";

const persistor = persistStore(storage.getStore());

storage.getSagaMiddleware().run(rootSaga);

export default function App(props) {
  const [isLoadingComplete, setLoadingComplete] = useState(false);
  let Content;

  if (!isLoadingComplete && !props.skipLoadingScreen) {
    Content = (
      <AppLoading
        startAsync={loadResourcesAsync}
        onError={handleLoadingError}
        onFinish={() => handleFinishLoading(setLoadingComplete)}
      />
    );
  } else {
    Content = (
      <View style={styles.container}>
        {Platform.OS === "ios" && <StatusBar barStyle="default" />}
        <AppNavigator />
      </View>
    );
  }

  return (
    <Provider store={storage.getStore()}>
      <PersistGate loading={null} persistor={persistor}>
        <ThemeProvider>
          <SafeAreaView style={{ flex: 1, backgroundColor: "#fff" }}>
            {Content}
          </SafeAreaView>
        </ThemeProvider>
      </PersistGate>
    </Provider>
  );
}

async function loadResourcesAsync() {
  await Promise.all([
    Asset.loadAsync([require("./assets/images/logo.png")]),
    Font.loadAsync({
      // This is the font that we are using for our tab bar
      ...Ionicons.font,
      // We include SpaceMono because we use it in HomeScreen.js. Feel free to
      // remove this if you are not using it in your app
      "space-mono": require("./assets/fonts/SpaceMono-Regular.ttf")
    })
  ]);
}

function handleLoadingError(error) {
  // In this case, you might want to report the error to your error reporting
  // service, for example Sentry
  console.warn(error);
}

function handleFinishLoading(setLoadingComplete) {
  setLoadingComplete(true);
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff"
  }
});
