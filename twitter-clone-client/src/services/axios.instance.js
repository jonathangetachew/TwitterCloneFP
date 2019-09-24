import axios from "axios";
const baseURL = "http://localhost:8080/api/v2";
const options = {
  baseURL,
  timeout: 1000,
  headers: {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "true"
  }
};

const server = axios.create(options);
export default server;
