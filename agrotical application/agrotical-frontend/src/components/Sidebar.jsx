import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { SunIcon, MoonIcon } from '@heroicons/react/24/outline';

const Sidebar = ({ username, onToggleSidebar }) => {
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(true);
  const [darkMode, setDarkMode] = useState(false);

  const toggleSidebar = () => {
    const newState = !isOpen;
    setIsOpen(newState);
    onToggleSidebar?.(newState); 
  };

  const toggleDarkMode = () => setDarkMode(!darkMode);

  const menuItems = [
    { label: '🏠 Αρχική Σελίδα', path: '/dashboard' },
    { label: '➕ Προσθήκη Χωραφιού', path: '/add-field' },
    { label: '📊 Αποτελέσματα', path: '/results' },
    { label: '📋 Τελικός Υπολογισμός', path: '/summary' },
    {
      label: '🚪 Αποσύνδεση',
      action: () => {
        localStorage.removeItem('user');
        localStorage.removeItem('userId');
        navigate('/');
      },
    },
  ];

  return (
    <div
      className={`fixed top-0 left-0 h-full transition-all duration-300 z-50 ${
        isOpen ? 'w-64' : 'w-16'
      } ${darkMode ? 'bg-gray-900 text-white' : 'bg-green-700 text-white'}`}
    >
      <div className="flex justify-between items-center p-4">
        <div className="text-xl font-bold">{isOpen ? '🌿 Agrotical' : '🌿'}</div>
        <button onClick={toggleSidebar} className="text-lg">
          ☰
        </button>
      </div>

      {isOpen && (
        <>
          <div className="flex flex-col items-center mb-6">
            <div className="w-16 h-16 bg-white rounded-full flex items-center justify-center text-green-700 text-2xl font-bold shadow">
              👨🏻‍🌾
            </div>
            <div className="mt-2 text-sm">
              Καλωσήρθες, <b>{username}</b>
            </div>
          </div>

          <ul className="space-y-3 px-4">
            {menuItems.map((item, i) => (
              <li key={i}>
                <button
                  onClick={() =>
                    item.path ? navigate(item.path) : item.action()
                  }
                  className={`w-full text-left px-4 py-2 rounded transition duration-200 ${
                    darkMode
                      ? 'bg-gray-800 hover:bg-gray-700'
                      : 'bg-green-600 hover:bg-green-500'
                  }`}
                >
                  {item.label}
                </button>
              </li>
            ))}
          </ul>

          <div className="mt-10 flex justify-center px-4">
            <button
              onClick={toggleDarkMode}
              className="flex items-center gap-2 px-4 py-2 rounded bg-gray-100 text-gray-900 hover:bg-gray-200 transition duration-200"
            >
              {darkMode ? (
                <SunIcon className="w-5 h-5" />
              ) : (
                <MoonIcon className="w-5 h-5" />
              )}
              {darkMode ? 'Φωτεινό' : 'Σκοτεινό'} θέμα
            </button>
          </div>
        </>
      )}
    </div>
  );
};

export default Sidebar;
