import React from "react";
import { Table } from "react-bootstrap";

const defaultRenderHeader = (data) => (
  <tr>
    <th>#</th>
    {Object.keys(data).map((k, i) => (
      <th key={i}>{k.toUpperCase()}</th>
    ))}
  </tr>
);

const defaultRenderItem = (item, key) => (
  <tr key={key}>
    <td>{key || item.id}</td>
    {Object.values(item).map((v, i) => (
      <td key={i}>{v ? typeof(v) == "object" ? Object.keys(v).map(vi => `${vi}, `) : v.toString() : "-"}</td>
    ))}
  </tr>
);

export default props => {
  const { data, renderHeader, renderItem } = props;
  console.log("DATA", data);

  if (data && data.length > 0) {
    return (
      <Table striped bordered hover>
        <thead>
          {renderHeader ? renderHeader(data) : defaultRenderHeader(data[0])}
        </thead>
        <tbody>
          {data.map((item, k) => renderItem ? renderItem(item, k) : defaultRenderItem(item, k))}
        </tbody>
      </Table>
    );
  }

  return (
    <div>
      <p>No data provided</p>
    </div>
  );
};
