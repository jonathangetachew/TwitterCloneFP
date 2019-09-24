import React from "react";
import axios from "../services/axios.instance";
import DynamicTable from "../components/DynamicTable";

export default class TweetPage extends React.PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data: []
    };
  }

  componentDidMount() {
    axios.get(this.props.route).then(({ _embedded }) => {
      const { tweets } = _embedded;
      console.log("DATA", tweets[0]);
      this.setState({ data: tweets });
    });
  }

  render() {
    return (
      <DynamicTable
        data={this.state.data}        
      />
    );
  }
}
