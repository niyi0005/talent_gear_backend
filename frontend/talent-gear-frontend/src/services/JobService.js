import axios from "axios";
import { getToken, redirectToLogin } from "../utils/AuthUtils";

const API_URL = "http://localhost:8080/api/jobs";
const token = getToken();

export const getJobs = async () => {
  try {
    const response = await axios.get(API_URL, {
      headers: {
        Authorization: `Bearer   ${token}`,
      },
    });
    console.log("Response data:", response.data);
    return response.data;
  } catch {
    console.log("catch reached");
    console.log(token);
    redirectToLogin();
  }
};

export const createJob = async (jobData) => {
  try {
    const response = await axios.post(API_URL, jobData, {
      headers: {
        Authorization: `Bearer   ${token}`,
      },
    });
    return response.data;
  } catch {
    redirectToLogin();
  }
};

export const updateJob = async (jobId, updatedJobData) => {
  try {
    const response = await axios.put(`${API_URL}/${jobId}`, updatedJobData, {
      headers: {
        Authorization: `Bearer   ${token}`,
      },
    });
    return response.data;
  } catch {
    redirectToLogin();
  }
};

export const deleteJob = async (jobId) => {
  try {
    await axios.delete(`${API_URL}/${jobId}`, {
      headers: {
        Authorization: `Bearer   ${token}`,
      },
    });
  } catch {
    redirectToLogin();
  }
};
