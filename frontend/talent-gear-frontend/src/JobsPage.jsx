import "bootstrap/dist/css/bootstrap.min.css";
import AllJobs from "./components/AllJobs";
import NavBar from "./components/NavBar";

const JobsPage = () => {
  return (
    <div
      className="container-fluid d-flex"
      style={{
        flexDirection: "column",
        minHeight: "100vh",
        width: "100vw",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f8f9fa",
        position: "relative",
      }}
    >
      <NavBar />
      <div
        className="card p-4 bg-primary"
        style={{
          width: "800px",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
        }}
      >
        <h2 className="text-center text-primary mb-4 text-black">Jobs</h2>
        <AllJobs />
      </div>

      <div
        style={{
          position: "absolute",
          top: "10px",
          right: "20px",
        }}
      ></div>
    </div>
  );
};

export default JobsPage;
