import React from "react";
import { Switch, Route, Router } from "react-router";
import { createBrowserHistory } from "history";
import "./res/main.css";
import routes from "./config/routes";
import Menu from "./components/Menu";

const history = createBrowserHistory();
const width = 250;

const Page = (props) => {
  const { Component, data} = props;
  return (
    <div
      style={{
        overflowX: "hidden",
        overflowY: "auto"
      }}
    >
      <div
        style={{
          width,
          position: "fixed",
          padding: 10,
          zIndex: 10,
          top: 0,
          left: 0,
          bottom: 0,
          overflowX: "hidden",
          overflowY: "auto"
        }}
      >
        <Menu />
      </div>
      <div
        style={{
          position: "relative",
          overflow: "auto",
          left: width,
          paddingRight: width,
          display: "flex",
          height:'100vh',
        }}
      >
        <div
          style={{
            overflow: "auto"
          }}
        >
          {Component ? <Component route={data.pathname}/> : null}
        </div>
      </div>
    </div>
  );
};

export default props => (
  <Router history={history}>
    <div className="Home">
      <Switch>
        <Route
          exact
          path="/"
          component={routes.filter(route => route.path === "/")[0].component}
        />
        {routes
          .filter(route => route.path !== "/")
          .map((route, index) => (
            <Route
              exact
              path={route.path}
              component={(data) => <Page Component={route.component} route={route} data={data.location}/>}
              key={index}
            />
          ))}
        {/**<Route component={NoMatch}/>**/}
      </Switch>
    </div>
  </Router>
);
