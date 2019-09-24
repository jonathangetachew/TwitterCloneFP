import axios from "axios";
const baseURL = "http://localhost:8080/api/v2";
const options = {
  baseURL,
  timeout: 1000,
  headers: {
    "Content-Type": "application/json",
  }
};

export default axios.create(options);
