-- Drop the tables if they exist
DROP TABLE IF EXISTS resumes;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS jobs;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(255) UNIQUE NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         roles ENUM('JOB_SEEKER', 'EMPLOYER') NOT NULL,
                         bio TEXT,
                         resumeUrl VARCHAR(255),
                         address VARCHAR(255),
                         phoneNumber VARCHAR(20),
                         website VARCHAR(255),
                         createdAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for 'username', 'email', and 'createdAt' for performance
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_createdAt ON users(createdAt);

CREATE TABLE resumes (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           userId BIGINT NOT NULL REFERENCES users(id),
                           fileName VARCHAR(255) NOT NULL,
                           fileUrl VARCHAR(255) NOT NULL,
                           uploadTime DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE jobs (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT,
                        industry VARCHAR(255),
                        location VARCHAR(255),
                        salary DECIMAL(10, 2),
                        employerId BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE

);

-- Create index on 'location', 'industry' and 'employer_id' columns of 'jobs' table for faster querying
CREATE INDEX idx_job_location ON jobs(location);
CREATE INDEX idx_job_industry ON jobs(industry);
CREATE INDEX idx_job_employer ON jobs(employerId);

CREATE TABLE applications (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                job_id BIGINT NOT NULL REFERENCES jobs(id),
                                applicantId BIGINT NOT NULL REFERENCES users(id),
                                applicationDate DATE NOT NULL,
                                status ENUM('PENDING', 'ACCEPTED', 'REJECTED', 'DRAFT', 'SUBMITTED') NOT NULL
);

-- Create index on 'status' column of 'applications' table for faster querying
CREATE INDEX idx_application_status ON applications(status);

CREATE TABLE messages (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            senderId BIGINT NOT NULL REFERENCES users(id),
                            recipientId BIGINT NOT NULL REFERENCES users(id),
                            content TEXT NOT NULL,
                            sentAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            isRead BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_recipient_id ON messages (recipientId);
CREATE INDEX idx_sender_id ON messages (senderId);
CREATE INDEX idx_sentAt ON messages (sentAt);


INSERT INTO users (username, email, password, roles, bio, resumeUrl, address, phoneNumber, website, createdAt)
VALUES
    ('johndoe', 'johndoe@example.com', 'password123', 'JOB_SEEKER', 'Experienced software developer with a passion for coding.', 'https://example.com/resumes/johndoe.pdf', '1234 Elm Street, SomeCity, SomeCountry', '555-1234', 'https://johndoeportfolio.com', NOW()),
    ('janedoe', 'janedoe@example.com', 'password456', 'JOB_SEEKER', 'Digital marketing specialist with 5+ years of experience.', 'https://example.com/resumes/janedoe.pdf', '5678 Oak Avenue, AnotherCity, AnotherCountry', '555-5678', 'https://janedoemarketing.com', NOW()),
    ('employerjoe', 'employerjoe@example.com', 'password789', 'EMPLOYER', 'Tech company looking to hire top talent for software development roles.', 'https://example.com/resumes/employerjoe.pdf', '91011 Pine Road, TechCity, TechCountry', '555-91011', 'https://employerjoetech.com', NOW()),
    ('susanbaker', 'susanbaker@example.com', 'password101', 'JOB_SEEKER', 'Experienced project manager in the construction industry.', 'https://example.com/resumes/susanbaker.pdf', '1213 Maple Lane, BusinessCity, BusinessCountry', '555-1213', 'https://susanbakerpm.com', NOW()),
    ('techhub', 'techhub@example.com', 'password102', 'EMPLOYER', 'Hiring managers for a growing IT consultancy firm.', 'https://example.com/resumes/techhub.pdf', '1415 Birch Boulevard, ConsultingCity, ConsultingCountry', '555-1415', 'https://techhubconsultancy.com', NOW()),
    ('startupco', 'startupco@example.com', 'company123', 'EMPLOYER', 'Innovative tech startup looking to disrupt the industry.', 'https://example.com/resumes/startupco.pdf', '200 Tech Park, InnovateCity, StartupCountry', '555-2000', 'https://startupco.com', NOW()),
    ('fintechcorp', 'fintechcorp@example.com', 'company456', 'EMPLOYER', 'Leading fintech company providing cutting-edge financial solutions.', 'https://example.com/resumes/fintechcorp.pdf', '300 Finance Ave, FinCity, FinanceCountry', '555-3000', 'https://fintechcorp.com', NOW());







