import { useNavigate } from "react-router-dom";

const LogoutButton = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Remove the token from localStorage
    localStorage.removeItem("authToken");
    console.log("Token removed, user logged out.");

    // Redirect to the login page
    navigate("/login");
  };

  return (
    <button
      className="text-black mx-2"
      style={{
        background: "none",
        border: "none",
        cursor: "pointer",
        padding: "0",
      }}
      onClick={handleLogout}
    >
      Logout
    </button>
  );
};

export default LogoutButton;
