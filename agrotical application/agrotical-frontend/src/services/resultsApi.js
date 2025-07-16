import axios from 'axios';

export const getUserResults = (userId) => {
  return axios.get(`http://localhost:8080/api/crop-results/user/${userId}`);
};

export const getResultsByFieldId = (fieldId) => {
  return axios.get(`http://localhost:8080/api/results/field/${fieldId}`);
};

export const deleteResultById = (resultId) => {
  return axios.delete(`http://localhost:8080/api/crop-results/${resultId}`);
};

export const updateResult = (id, data) => {
  return axios.put(`http://localhost:8080/api/crop-results/${id}`, data);
};
