import 'bootstrap/dist/css/bootstrap.min.css';
import './HomePage.css';
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div className="homepage" style={{ minHeight: '100vh', width: '100vw', display: 'flex', flexDirection: 'column' }}>
      {/* Hero Section */}
      <header className="hero-section text-center bg-primary text-white py-5" style={{ width: '100%', position: 'relative' }}>
        <div className="container-fluid">
          <div style={{ position: 'absolute', top: '10px', right: '20px' }}>
            <Link to="/login" className="text-white mx-2">Login</Link>
            <Link to="/register" className="text-white mx-2">Register</Link>
          </div>
          <h1 className="display-4">Welcome to Talent Gear</h1>
          <p className="lead">Connecting jobseekers and employers with seamless efficiency.</p>
          <a href="/register" className="btn btn-light btn-lg mt-3">Get Started</a>
        </div>
      </header>

      {/* Features Section */}
      <section className="features-section py-5" style={{ width: '100%' }}>
        <div className="container-fluid">
          <div className="row">
            <div className="col-md-6 text-center">
              <i className="bi bi-person-circle display-3 text-primary"></i>
              <h3 className="mt-3">For Jobseekers</h3>
              <p>Create your profile, search for jobs, and connect with employers.</p>
              <a href="/jobs" className="btn btn-outline-primary">Explore Jobs</a>
            </div>
            <div className="col-md-6 text-center">
              <i className="bi bi-building display-3 text-primary"></i>
              <h3 className="mt-3">For Employers</h3>
              <p>Post job openings, manage applications, and find top talent.</p>
              <Link to="/employers" className="btn btn-outline-primary">Post a Job</Link>
            </div>
          </div>
        </div>
      </section>

      <section className="cta-section bg-light py-5" style={{ width: '100%' }}>
        <div className="container-fluid text-center">
          <h2>Ready to get started?</h2>
          <p className="lead">Sign up today and join a community of professionals and businesses shaping the future.</p>
          <a href="/register" className="btn btn-primary btn-lg">Join Talent Gear</a>
        </div>
      </section>

      <footer className="footer bg-dark text-white py-4 mt-auto" style={{ width: '100%' }}>
        <div className="container-fluid text-center">
          <p>&copy; 2024 Talent Gear. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
};

export default HomePage;
