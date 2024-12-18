import "bootstrap/dist/css/bootstrap.min.css";
import "./styles//HomePage.css";
import NavBar from "./components/NavBar";
import { Link } from "react-router-dom";

const HomePage = () => {
  return (
    <div className="d-flex flex-column min-vh-100">
      {/* Navbar */}
      <NavBar />

      {/* Hero Section */}
      <header className="hero-section bg-primary text-white text-center py-5 mt-5">
        <div className="container">
          <h1 className="display-4">Connecting Jobseekers and Employers</h1>
          <p className="lead">
            Seamlessly find jobs or hire the best talent for your team.
          </p>
          <a href="/register" className="btn btn-light btn-lg mt-3">
            Get Started
          </a>
        </div>
      </header>

      {/* Features Section */}
      <section className="features-section py-5">
        <div className="container">
          <div className="row text-center">
            <div className="col-md-6">
              <i className="bi bi-person-circle display-3 text-primary"></i>
              <h3 className="mt-3">For Jobseekers</h3>
              <p>Create your profile, search for jobs, and connect with employers.</p>
              <Link to="/jobs" className="btn btn-outline-primary">
                Explore Jobs
              </Link>
            </div>
            <div className="col-md-6">
              <i className="bi bi-building display-3 text-primary"></i>
              <h3 className="mt-3">For Employers</h3>
              <p>Post job openings, manage applications, and find top talent.</p>
              <Link to="/employers" className="btn btn-outline-primary">
                Post a Job
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Call-to-Action Section */}
      <section className="cta-section bg-light py-5 text-center">
        <div className="container">
          <h2>Ready to get started?</h2>
          <p className="lead">
            Join a community of professionals and businesses shaping the future.
          </p>
          <a href="/register" className="btn btn-primary btn-lg">
            Join Talent Gear
          </a>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-dark text-white text-center py-3 mt-auto">
        <div className="container">
          <p className="mb-0">&copy; 2024 Talent Gear. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
};

export default HomePage;
