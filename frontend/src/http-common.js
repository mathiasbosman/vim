import axios from "axios";

export default axios.create({
  baseURL: "/rest",
  headers: {
    "Content-type": "application/json",
  },
  auth: {
    username: 'user',
    password: 'password'
  }
})