-- Drop the tables if they exist

DROP TABLE IF EXISTS resumes;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS jobs;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) UNIQUE NOT NULL,
                       description VARCHAR(255),
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       password_reset_token VARCHAR(255),
                       password_reset_token_expiration_date DATETIME,
                       bio TEXT,
                       resume_url VARCHAR(255),
                       address VARCHAR(255),
                       phone_number VARCHAR(20),
                       website VARCHAR(255),
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for 'username', 'email', for performance
# CREATE INDEX idx_user_username ON users(username);
# CREATE INDEX idx_user_email ON users(email);

CREATE TABLE users_roles (
                             user_id BIGINT NOT NULL,                   -- User reference
                             role_id BIGINT NOT NULL,                   -- Role reference
                             PRIMARY KEY (user_id, role_id),            -- Composite primary key
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,  -- Foreign key to users table
                             FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE   -- Foreign key to roles table
);

CREATE TABLE resumes (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         user_id BIGINT NOT NULL REFERENCES users(id),
                         file_name VARCHAR(255) NOT NULL,
                         file_url VARCHAR(255) NOT NULL,
                         upload_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE jobs (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      description TEXT,
                      industry VARCHAR(255),
                      location VARCHAR(255),
                      salary DECIMAL(10, 2),
                      employer_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE

);

-- Create index on 'location', 'industry' and 'employer_id' columns of 'jobs' table for faster querying
# CREATE INDEX idx_job_location ON jobs(location);
# CREATE INDEX idx_job_industry ON jobs(industry);

CREATE TABLE applications (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              job_id BIGINT NOT NULL REFERENCES jobs(id),
                              applicant_id BIGINT NOT NULL REFERENCES users(id),
                              application_date DATE NOT NULL,
                              status ENUM('LIKED', 'PENDING', 'ACCEPTED', 'REJECTED', 'DRAFT', 'SUBMITTED') NOT NULL
);

-- Create index on 'status' column of 'applications' table for faster querying
# CREATE INDEX idx_application_status ON applications(status);

CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          sender_id BIGINT NOT NULL REFERENCES users(id),
                          recipient_id BIGINT NOT NULL REFERENCES users(id),
                          content TEXT NOT NULL,
                          sent_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          isRead BOOLEAN NOT NULL DEFAULT FALSE
);

# CREATE INDEX idx_recipient_id ON messages (recipientId);
# CREATE INDEX idx_sender_id ON messages (senderId);

-- Insert roles into the roles table
INSERT INTO roles (name, description, created_at)
VALUES
    ('ADMIN', 'Administrator with full access', NOW()),
    ('EMPLOYER', 'Employer who posts jobs', NOW()),
    ('JOB_SEEKER', 'Job seeker looking for opportunities', NOW());



-- Insert demo users into the users table
INSERT INTO users (username, email, password, bio, resume_url, address, phone_number, website, created_at)
VALUES
    ('adminUser', 'admin@example.com', 'password123', 'Administrator user', NULL, '123 Admin Street', '555-1234', 'http://admin.com', NOW()),
    ('employerUser', 'employer@example.com', 'password123', 'Employer user', NULL, '456 Employer Avenue', '555-5678', 'http://employer.com', NOW()),
    ('jobSeekerUser', 'jobseeker@example.com', 'password123', 'Job seeker user', NULL, '789 Jobseeker Road', '555-9876', 'http://jobseeker.com', NOW());


-- Assign 'ADMIN' role to adminUser
INSERT INTO users_roles (user_id, role_id)
VALUES
    ((SELECT id FROM users WHERE username = 'adminUser'), (SELECT id FROM roles WHERE name = 'ADMIN'));

-- Assign 'EMPLOYER' role to employerUser
INSERT INTO users_roles (user_id, role_id)
VALUES
    ((SELECT id FROM users WHERE username = 'employerUser'), (SELECT id FROM roles WHERE name = 'EMPLOYER'));

-- Assign 'JOB_SEEKER' role to jobSeekerUser
INSERT INTO users_roles (user_id, role_id)
VALUES
    ((SELECT id FROM users WHERE username = 'jobSeekerUser'), (SELECT id FROM roles WHERE name = 'JOB_SEEKER'));


-- insert jobs
INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (3, 'Software Engineer', 'Develop and maintain web applications', 'Technology', 'San Francisco, CA', 120000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (3, 'Data Analyst', 'Analyze and interpret complex data sets', 'Finance', 'New York, NY', 85000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (3, 'Marketing Manager', 'Develop marketing strategies and campaigns', 'Marketing', 'Chicago, IL', 95000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (4, 'Graphic Designer', 'Create visual content for print and digital media', 'Design', 'Los Angeles, CA', 70000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (4, 'Project Manager', 'Oversee project timelines and deliverables', 'Construction', 'Dallas, TX', 105000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (15, 'Sales Representative', 'Generate leads and close sales', 'Retail', 'Atlanta, GA', 65000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (17, 'Human Resources Specialist', 'Manage employee relations and recruitment', 'Human Resources', 'Seattle, WA', 80000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (17, 'Mechanical Engineer', 'Design and develop mechanical systems', 'Engineering', 'Detroit, MI', 95000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (17, 'Cybersecurity Analyst', 'Monitor and secure IT infrastructure', 'Technology', 'Austin, TX', 115000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (17, 'Teacher', 'Instruct and guide students in academic subjects', 'Education', 'Boston, MA', 60000.00);

-- More technology jobs
INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (15, 'Frontend Developer', 'Build and optimize user interfaces', 'Technology', 'Seattle, WA', 110000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (15, 'DevOps Engineer', 'Automate infrastructure and deployments', 'Technology', 'Austin, TX', 125000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (17, 'UI/UX Designer', 'Design intuitive and attractive user experiences', 'Technology', 'New York, NY', 95000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (17, 'Cloud Engineer', 'Implement and manage cloud-based solutions', 'Technology', 'San Francisco, CA', 135000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (17, 'Full Stack Developer', 'Develop end-to-end software solutions', 'Technology', 'Denver, CO', 120000.00);

-- More Finance jobs
INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (3, 'Financial Advisor', 'Provide investment advice and planning', 'Finance', 'Chicago, IL', 85000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (3, 'Risk Analyst', 'Evaluate and mitigate financial risks', 'Finance', 'Houston, TX', 90000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (3, 'Accountant', 'Prepare financial statements and manage budgets', 'Finance', 'Phoenix, AZ', 75000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (3, 'Investment Analyst', 'Analyze market trends and investment opportunities', 'Finance', 'Boston, MA', 95000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (3, 'Loan Officer', 'Evaluate and approve loan applications', 'Finance', 'Miami, FL', 80000.00);

-- More Engineering jobs

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (6, 'Civil Engineer', 'Design and oversee construction projects', 'Engineering', 'San Francisco, CA', 95000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (6, 'Electrical Engineer', 'Develop and test electrical systems', 'Engineering', 'Dallas, TX', 90000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (6, 'Structural Engineer', 'Analyze structural integrity of buildings', 'Engineering', 'Boston, MA', 92000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (6, 'Aerospace Engineer', 'Design aircraft and spacecraft systems', 'Engineering', 'Seattle, WA', 120000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (6, 'Biomedical Engineer', 'Develop medical devices and equipment', 'Engineering', 'Houston, TX', 88000.00);

-- More Retails jobs

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (15, 'Store Manager', 'Oversee daily store operations', 'Retail', 'San Antonio, TX', 70000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (15, 'Visual Merchandiser', 'Design store layouts and product displays', 'Retail', 'Orlando, FL', 60000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (15, 'Customer Service Associate', 'Assist customers with purchases and queries', 'Retail', 'Las Vegas, NV', 45000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (15, 'Inventory Manager', 'Manage and optimize stock levels', 'Retail', 'Charlotte, NC', 65000.00);

INSERT INTO jobs (employer_ID, name, description, industry, location, salary)
VALUES (15, 'E-commerce Specialist', 'Manage online retail operations', 'Retail', 'Miami, FL', 70000.00);








