import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser, registerUser } from "../services/userApi";

const LoginPage = () => {
  const [isRegistering, setIsRegistering] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (username.trim() === "" || password.trim() === "") {
      alert("Παρακαλώ συμπληρώστε όλα τα πεδία.");
      return;
    }

    try {
      const action = isRegistering ? registerUser : loginUser;
      const response = await action({ username, password });

      const userId = typeof response.data === "object" && response.data.id
        ? response.data.id
        : response.data;

      localStorage.setItem("user", username);
      localStorage.setItem("userId", userId.toString());

      navigate("/dashboard");
    } catch (error) {
      console.error("❌ Ενέργεια απέτυχε:", error);
      if (isRegistering) {
        alert("❌ Το όνομα χρήστη υπάρχει ήδη.");
      } else {
        alert("❌ Λάθος όνομα χρήστη ή κωδικός.");
      }
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-100 to-lime-200">
      <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
        <h2 className="text-2xl font-bold mb-4 text-green-700 text-center">
          {isRegistering ? "Δημιουργία Λογαριασμού" : "Σύνδεση Λογαριασμού"}
        </h2>
        <p className="text-sm text-gray-600 mb-6 text-center">
          {isRegistering
            ? "Συμπλήρωσε τα στοιχεία σου για εγγραφή"
            : "Συμπλήρωσε τα στοιχεία σου για να συνδεθείς"}
        </p>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            placeholder="Όνομα Χρήστη"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
          />
          <input
            type="password"
            placeholder="Κωδικός"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
          />

          <img
            src="/AGROTICAL.jpg"
            alt="Agrotical Logo"
            className="w-52 mx-auto mt-4 rounded shadow"
          />

          <button
            type="submit"
            className="w-full bg-green-500 text-white py-2 rounded hover:bg-green-600 transition duration-200"
          >
            {isRegistering ? "Εγγραφή" : "Σύνδεση"}
          </button>
        </form>

        <div className="mt-4 text-center">
          <button
            onClick={() => setIsRegistering(!isRegistering)}
            className="text-sm text-green-600 underline hover:text-green-800"
          >
            {isRegistering
              ? "Έχεις ήδη λογαριασμό; Σύνδεση"
              : "Δεν έχεις λογαριασμό; Εγγραφή"}
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
