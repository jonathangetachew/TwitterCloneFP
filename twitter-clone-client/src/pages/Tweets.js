import React from "react";
import axios from "../services/axios.instance";

export default class TweetPage extends React.PureComponent {
  componentDidMount() {
    axios.get("/tweets").then(res => alert("RES"));
  }

  render(){
    return null;
  }
}
