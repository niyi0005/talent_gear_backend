import { useState, useEffect } from "react";

import { getJobs } from "../services/JobService"; 


const AllJobs = () => {
  const [jobs, setJobs] = useState([]);
  const [error, setError] = useState(null);

  useEffect( () => {

    getJobs()
      .then((response) => {
        setJobs(response);
        setError(null);
      })
     
    .catch( (error) => {
        console.error("Error fetching jobs:", error);
        setError(error.message);
    });
  }, []);

  return (
    <div>
      <h2>Available Job Listings</h2>
      {error && <p>{error}</p>}
      {jobs.length > 0 ? (
        <ul>
          {jobs.map((job) => (
            <li key={job.id}>
              <h3>{job.name}</h3>
              <p>Description: {job.description}</p>
              <p>Industry: {job.industry}</p>
              <p>Location: {job.location}</p>
              <p>Salary: ${job.salary}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p>No jobs available.</p>
      )}
    </div>
  );
}
  
export default AllJobs;