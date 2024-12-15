import 'bootstrap/dist/css/bootstrap.min.css';
import AllJobs from './components/AllJobs';

const JobsPage = () => {
  return (
    <div style={{ minHeight: '100vh', width: '100vw', display: 'flex', justifyContent: 'center', alignItems: 'center', backgroundColor: '#f8f9fa' }}>
      <div className="card p-4 bg-primary" style={{ width: '800px', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)' }}>
        <h2 className="text-center text-primary mb-4 text-black">Jobs</h2>
        <AllJobs />
      </div>
    </div>
  );
};

export default JobsPage;
