import React from "react";
import { Alert } from "react-bootstrap";
import routes from "../config/routes";

function Menu() {
  return (
    <div>
      {routes
        .filter(route => route.path !== "/")
        .map((route, index) => (
          <Alert key={index} variant="primary">
            <Alert.Link href={route.path}>{route.title}</Alert.Link>
          </Alert>
        ))}
    </div>
  );
}

export default Menu;
