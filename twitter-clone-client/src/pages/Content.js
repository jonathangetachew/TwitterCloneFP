import React from "react";
import axios from "../services/axios.instance";
import DynamicTable from "../components/DynamicTable";

export default class ContentPage extends React.PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data: []
    };
  }

  componentDidMount() {
    axios.get(this.props.route).then(({ _embedded }) => {
      const result = _embedded || {};
      const data = result[this.props.index] || [];
      this.setState({ data });
    });
  }

  render() {
    return <DynamicTable data={this.state.data} />;
  }
}
