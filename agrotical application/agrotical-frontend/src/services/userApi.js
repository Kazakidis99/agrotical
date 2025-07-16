import axios from 'axios';

export const loginUser = async ({ username, password }) => {
  return axios.post('http://localhost:8080/api/users/login', {
    username,
    password,
  });
};

export const registerUser = async ({ username, password }) => {
  return axios.post('http://localhost:8080/api/users/register', {
    username,
    password,
  });
};

