import React from "react";
import Menu from "../components/Menu";

function Home() {
  return (
    <div
      style={{
        display:'flex',
        justifyContent: "center",
        alignItems: "center",
        flex: 1,
        flexDirection:'row'
      }}
    >
      <Menu />
    </div>
  );
}

export default Home;
