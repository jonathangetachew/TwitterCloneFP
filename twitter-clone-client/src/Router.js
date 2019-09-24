import React from "react";
import { Switch, Route, Router } from "react-router";
import { createBrowserHistory } from "history";
import "./res/main.css";
import routes from "./config/routes";

const history = createBrowserHistory();

export default props => (
  <Router history={history}>
    <div className="Home">
      <Switch>
        {routes.map((route, index) => (
          <Route {...route} key={index} />
        ))}
        {/**<Route component={NoMatch}/>**/}
      </Switch>
    </div>
  </Router>
);
