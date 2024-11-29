-- Drop the indexes if they exist
DROP INDEX idx_job_location ON jobs;
DROP INDEX idx_job_industry ON jobs;
DROP INDEX idx_job_employer ON jobs;
DROP INDEX idx_application_status ON applications;

-- Drop the tables if they exist
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS jobs;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
                      id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                      username VARCHAR(255) UNIQUE NOT NULL,
                      email VARCHAR(255) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role ENUM('ADMIN', 'EMPLOYER', 'JOB_SEEKER') NOT NULL,
                      bio TEXT,
                      resume_url VARCHAR(255),  -- URL or file path for job seekers' resumes
                      address VARCHAR(255),  -- Address for companies (EMPLOYER role)
                      phone_number VARCHAR(20),  -- Phone number for companies (EMPLOYER role)
                      company_email VARCHAR(255),  -- Company email for EMPLOYER role
                      website VARCHAR(255),  -- Company website for EMPLOYER role
                      createdAt DATETIME NOT NULL

);

-- Create indexes for 'username', 'email', and 'createdAt' for performance
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_createdAt ON users(createdAt);

CREATE TABLE jobs (
                       id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(255) NOT NULL,
                       description VARCHAR(255) NOT NULL,
                       industry VARCHAR(255),
                       location VARCHAR(255) DEFAULT NULL,
                       salary DOUBLE NOT NULL,
                       employer_id BIGINT REFERENCES users(id) ON DELETE SET NULL
);

-- Create index on 'location', 'industry' and 'employer_id' columns of 'jobs' table for faster querying
CREATE INDEX idx_job_location ON jobs(location);
CREATE INDEX idx_job_industry ON jobs(industry);
CREATE INDEX idx_job_employer ON jobs(employer_id);

CREATE TABLE applications (
                      id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                      job_id VARCHAR(255) REFERENCES jobs(id) ON DELETE CASCADE,
                      applicant_id VARCHAR(255) REFERENCES users(id) ON DELETE CASCADE,
                      applicationDate DATETIME NOT NULL,
                      status ENUM('PENDING', 'ACCEPTED', 'REJECTED', 'DRAFT', 'SUBMITTED')

);

-- Create index on 'status' column of 'applications' table for faster querying
CREATE INDEX idx_application_status ON applications(status);