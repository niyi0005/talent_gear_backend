import "bootstrap/dist/css/bootstrap.min.css";
import { useState } from "react";
import axios from "axios";

const RegisterPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [roles, setRole] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const rolesArray = [roles.toUpperCase()];
      const response = await axios.post("http://localhost:8080/register", {
        username,
        password,
        email,
        roles: rolesArray,
      });

      if (response.status === 200 || response.status === 201) {
        console.log("Registration successful:", response.data);
        // Reset the form
        setUsername("");
        setPassword("");
        setEmail("");
        setRole("");

        const token = response.data;

        // Save the token
        localStorage.setItem("authToken", token);

        console.log("Response data:", response.data);

        // FOR NOW - Redirect user to jobs page
        window.location.href = "/Jobs";
      } else {
        console.error("Unexpected response:", response);
      }
      console.log("Registration response:", response);
    } catch (error) {
      console.error(
        "Error during registration:",
        error.response?.data || error.message
      );
    }
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        width: "100vw",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f8f9fa",
      }}
    >
      <div
        className="card p-4"
        style={{ width: "500px", boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)" }}
      >
        <h2 className="text-center text-primary mb-4">Register</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="username" className="form-label">
              Username
            </label>
            <input
              type="text"
              id="username"
              name="username"
              className="form-control"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="password" className="form-label">
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              className="form-control"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">
              Email
            </label>
            <input
              type="email"
              id="email"
              name="email"
              className="form-control"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="role" className="form-label">
              Who are you?
            </label>
            <select
              id="role"
              name="role"
              className="form-select"
              value={roles}
              onChange={(e) => setRole(e.target.value)}
              required
            >
              <option value="" disabled>
                Select Role
              </option>
              <option value="job_seeker">Job Seeker</option>
              <option value="employer">Employer</option>
            </select>
          </div>
          <div className="mb-3"></div>
          <button type="submit" className="btn btn-primary w-100">
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;
