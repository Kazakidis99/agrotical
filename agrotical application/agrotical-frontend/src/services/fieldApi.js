import axios from 'axios';

export const createField = async (userId, data) => {
  return axios.post(`http://localhost:8080/api/fields/create/${userId}`, data);
};

export const calculateCropResults = async (data) => {
  return axios.post(`http://localhost:8080/api/crop-results/calculate`, data);
};

export const getResultsByFieldId = (fieldId) => {
  return axios.get(`http://localhost:8080/api/results/field/${fieldId}`);
};