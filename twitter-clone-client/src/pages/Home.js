import React from "react";
import { Button, Alert } from "react-bootstrap";
import routes from "../config/routes";

function Home() {
  return (
    <div style={{display: 'flex', flex: 1, justifyContent:'center', alignItems:'center'}}>
      <div className="">
        {routes.filter(route => route.path != "/").map((route, index) => (
          <Alert key={index} variant="primary">
            <Alert.Link href={route.path}>{route.title}</Alert.Link>
          </Alert>
        ))}
      </div>
    </div>
  );
}

export default Home;
