import React from "react";
import { Switch, Route, Router } from "react-router";
import { createBrowserHistory } from "history";
import "./res/main.css";
import routes from "./config/routes";

import HomeMenu from "./pages/Home";

const history = createBrowserHistory();

const Page = (props) => {
  const { Component, data} = props;
  console.log("ROUTE", data);
  return (
    <div
      style={{
        overflowX: "hidden",
        overflowY: "auto"
      }}
    >
      <div
        style={{
          width: 200,
          position: "fixed",
          zIndex: 10,
          top: 0,
          left: 0,
          bottom: 0,
          overflowX: "hidden",
          overflowY: "auto"
        }}
      >
        <HomeMenu />
      </div>
      <div
        style={{
          position: "relative",
          overflow: "auto",
          left: 200,
          paddingRight: 200,
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
