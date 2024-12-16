import "bootstrap/dist/css/bootstrap.min.css";
import "./HomePage.css";
import NavBar from "./components/NavBar";
import { Link } from "react-router-dom";

const HomePage = () => {
  return (
    <div style={{ height: "100vh" }}>
      <div>
        <NavBar />
      </div>
      <div
        style={{
          width: "100vw",
          height: "100vh",
          display: "flex",
          flexDirection: "column",
        }}
      >
        <header
          className="text-center bg-primary text-white py-4"
          style={{ width: "100%", position: "relative" }}
        >
          <div className="container-fluid">
            <h1 className="lead">
              Connecting jobseekers and employers with seamless efficiency.
            </h1>
            <a href="/register" className="btn btn-light btn-lg mt-3">
              Get Started
            </a>
          </div>
        </header>

        <section className="py-5" style={{ width: "100%" }}>
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-6 text-center">
                <i className="bi bi-person-circle display-3 text-primary"></i>
                <h3 className="mt-3">For Jobseekers</h3>
                <p>
                  Create your profile, search for jobs, and connect with
                  employers.
                </p>
                <a href="/jobs" className="btn btn-outline-primary">
                  Explore Jobs
                </a>
              </div>
              <div className="col-md-6 text-center">
                <i className="bi bi-building display-3 text-primary"></i>
                <h3 className="mt-3">For Employers</h3>
                <p>
                  Post job openings, manage applications, and find top talent.
                </p>
                <Link to="/employers" className="btn btn-outline-primary">
                  Post a Job
                </Link>
              </div>
            </div>
          </div>
        </section>

        <section className="bg-light py-5" style={{ width: "100%" }}>
          <div className="container-fluid text-center">
            <h2>Ready to get started?</h2>
            <p className="lead">
              Sign up today and join a community of professionals and businesses
              shaping the future.
            </p>
            <a href="/register" className="btn btn-primary btn-lg">
              Join Talent Gear
            </a>
          </div>
        </section>

        <footer
          className="bg-dark text-white py-4 mt-auto"
          style={{ width: "100%" }}
        >
          <div className="container-fluid text-center">
            <p>&copy; 2024 Talent Gear. All rights reserved.</p>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default HomePage;
