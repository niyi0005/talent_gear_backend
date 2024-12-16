import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

const NavBar = () => {
  const navigate = useNavigate();
  const isAuthenticated = localStorage.getItem("authToken") !== null;

  const handleLogout = () => {
    // Remove the token from localStorage
    localStorage.removeItem("authToken");
    console.log("Token removed, user logged out.");

    // Redirect to the login page
    navigate("/login");
  };

  return (
    <nav
      className="navbar navbar-expand-lg bg-primary"
      style={{
        padding: "10px 20px",
      }}
    >
      <div className="container-fluid d-flex justify-content-between align-items-center">
        <div className="d-flex">
          <Link to="/home" className="nav-link text-white mx-2">
            Home
          </Link>
          <Link to="/jobs" className="nav-link text-white mx-2">
            Jobs
          </Link>
          <Link to="/employers" className="nav-link text-white mx-2">
            Employers
          </Link>
        </div>

        <Link
          to="/"
          className="navbar-brand text-white flex-grow-1 text-center"
        >
          <h1 className="display-4">Talent Gear</h1>
        </Link>

        <div className="d-flex">
          {!isAuthenticated ? (
            <>
              <Link to="/login" className="nav-link text-white mx-2">
                Login
              </Link>
              <Link to="/register" className="nav-link text-white mx-2">
                Register
              </Link>
            </>
          ) : (
            <button
              className="nav-link text-white mx-2"
              style={{
                background: "none",
                border: "none",
                cursor: "pointer",
              }}
              onClick={handleLogout}
            >
              Logout
            </button>
          )}
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
