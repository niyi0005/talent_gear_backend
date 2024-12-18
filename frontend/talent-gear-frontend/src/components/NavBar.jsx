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
    <div className="w-100">
      <nav className="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
        <div className="container-fluid d-flex justify-content-between">
          <Link to="/home" className="navbar-brand">
            Talent Gear
          </Link>

          <div className="d-flex align-items-center">
            <Link to="/jobs" className="nav-link text-white mx-2">
              Jobs
            </Link>

            <div
              className="border-start border-light mx-2"
              style={{ height: "24px" }}
            ></div>

            <Link to="/employers" className="nav-link text-white mx-2">
              Employers
            </Link>

            {!isAuthenticated ? (
              <>
                <div
                  className="border-start border-light mx-2"
                  style={{ height: "24px" }}
                ></div>
                <Link to="/login" className="nav-link text-white mx-2">
                  Login
                </Link>

                <div
                  className="border-start border-light mx-2"
                  style={{ height: "24px" }}
                ></div>

                <Link to="/register" className="nav-link text-white mx-2">
                  Register
                </Link>
              </>
            ) : (
              <>
                <div
                  className="border-start border-light mx-2"
                  style={{ height: "24px" }}
                ></div>

                <button
                  className="nav-link btn text-white mx-2"
                  onClick={handleLogout}
                >
                  Logout
                </button>
              </>
            )}
          </div>
        </div>
      </nav>
    </div>
  );
};

export default NavBar;
