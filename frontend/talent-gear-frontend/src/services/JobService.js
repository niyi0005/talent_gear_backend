import axios from "axios";

const API_URL = "http://localhost:8080/api/jobs";
const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDT01QQU5ZTUFOIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9FTVBMT1lFUiJdLCJpYXQiOjE3MzQwMzk3MzUsImV4cCI6MTczNDA0MzMzNX0.WSV-DSkkkkoL9lBWrB1DGQ8z8iq4n0Ibsi0tbeFkgwvDqTQRdEsED3uRLIBwC98FcJwVeZwdPInvgl-Bsk1Hrg";

export const getJobs = async () => {
  try {
    const response = await axios.get(API_URL, {
      headers: {
        Authorization: `Bearer ` + token
      },
    });
    console.log("Response data:", response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching jobs:", error);
    throw error;
  }
};

export const createJob = async (jobData) => {
  try {
    const response = await axios.post(API_URL, jobData);
    return response.data;
  } catch (error) {
    console.error("Error creating job:", error);
    throw error;
  }
};

export const updateJob = async (jobId, updatedJobData) => {
  try {
    const response = await axios.put(`${API_URL}/${jobId}`, updatedJobData);
    return response.data;
  } catch (error) {
    console.error("Error updating job:", error);
    throw error;
  }
};

export const deleteJob = async (jobId) => {
  try {
    await axios.delete(`${API_URL}/${jobId}`);
  } catch (error) {
    console.error("Error deleting job:", error);
    throw error;
  }
};
