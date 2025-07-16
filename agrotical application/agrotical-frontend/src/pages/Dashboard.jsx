import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Sidebar from '../components/Sidebar';
import backgroundImage from '../assets/Image.png'; 

const Dashboard = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (!storedUser) {
      navigate('/');
    } else {
      setUsername(storedUser);
    }
  }, [navigate]);

  return (
    <div
      className="flex min-h-screen bg-cover bg-center"
      style={{ backgroundImage: `url(${backgroundImage})` }}
    >
      <Sidebar username={username} />

      <main className="flex-1 p-10">
        <div className="max-w-3xl mx-auto bg-white bg-opacity-90 backdrop-blur-md rounded-xl shadow-md p-8">
          <h2 className="text-2xl font-bold text-green-700 mb-4">📋 Κεντρική Σελίδα</h2>
          <p className="text-gray-700 text-lg mb-2">
            🌾 Η εφαρμογή <span className="font-semibold text-green-700">Agrotical</span> σας βοηθά να υπολογίζετε τα έσοδα και τα έξοδα των καλλιεργειών σας.
          </p>
          <p className="text-gray-600 text-md">
            Χρησιμοποίησε το μενού στα αριστερά για να ξεκινήσεις. 📌
          </p>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;
