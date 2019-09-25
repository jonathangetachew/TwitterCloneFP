import axios from "axios";
const baseURL = "http://twitter-clone.com:8080/api/v2";
const options = {
  baseURL,
  timeout: 1000,
  headers: { 
    "Content-Type": "application/json"
  }
};

const server = axios.create(options);
server.interceptors.response.use(
  response => {
    // do something with the response data
    console.log("Response was received", response);

    return response.data;
  },
  error => {
    // handle the response error
    return Promise.reject(error);
  }
);
export default server;
