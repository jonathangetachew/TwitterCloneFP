import React from "react";
import Menu from "../components/Menu";

function Home() {
  return (
    <div
      style={{
        display:'flex',
        justifyContent: "center",
        alignItems: "center",
        height:'100vh',
        flex: 1
      }}
    >
      <div>
        <Menu />
      </div>
    </div>
  );
}

export default Home;
