import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './HomePage.css';

import HomePage from './HomePage';
import EmployersPage from './EmployersPage';
import NotFoundPage from './NotFoundPage';
import JobsPage from './JobsPage';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} /> 
        <Route path="/employers" element={<EmployersPage />} />
        <Route path="/jobs" element={<JobsPage />} />
        <Route path="/*" element={<NotFoundPage />} />
      </Routes>
    </Router>
  );
};

export default App;
