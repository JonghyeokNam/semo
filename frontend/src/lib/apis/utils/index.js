import axios from 'axios';

export const API = axios.create({
  baseURL: import.meta.env.REACT_APP_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});