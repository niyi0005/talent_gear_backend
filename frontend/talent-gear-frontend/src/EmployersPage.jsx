import "bootstrap/dist/css/bootstrap.min.css";
import LogoutButton from "./components/logout";

const EmployersPage = () => {
  const token = localStorage.getItem("authToken");
  console.log(token);
  if (!token) {
    window.location.href = "/login";
    return null;
  }

  return (
    
    <div className="container py-5">
      <div
        style={{
          position: "absolute",
          top: "10px",
          right: "20px",
        }}
      >
        <LogoutButton />
      </div>
      <h1>For Employers</h1>
      <p>Manage job postings and find the best talent.</p>
      
    </div>
  );
};

export default EmployersPage;
