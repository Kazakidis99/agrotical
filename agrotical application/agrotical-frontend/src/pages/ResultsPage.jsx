import React, { useEffect, useState } from 'react';
import Sidebar from '../components/Sidebar';
import { getUserResults, deleteResultById } from '../services/resultsApi';
import { ChartBarIcon, XMarkIcon, PencilIcon } from '@heroicons/react/24/solid';
import ProfitChart from '../components/ProfitChart';
import backgroundImage from '../assets/Image.png';
import { useNavigate } from 'react-router-dom';

const ResultsPage = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showChart, setShowChart] = useState(false);
  const [filterCrop, setFilterCrop] = useState('');
  const [sortOrder, setSortOrder] = useState('desc');
  const [sidebarOpen, setSidebarOpen] = useState(true);

  const userId = localStorage.getItem('userId');
  const navigate = useNavigate();

  const fetchResults = () => {
    if (!userId) return;

    setLoading(true);
    getUserResults(userId)
      .then(res => {
        console.log("📦 Αποτελέσματα που ήρθαν:", res.data);
        setResults(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error("❌ Σφάλμα:", err);
        alert("❌ Αποτυχία φόρτωσης.");
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchResults();
  }, [userId]);

  const handleDelete = async (resultId) => {
    const confirmed = window.confirm("❌ Είσαι σίγουρος ότι θέλεις να διαγράψεις αυτό το αποτέλεσμα;");
    if (!confirmed) return;

    const cleanId = parseInt(resultId);
    try {
      await deleteResultById(cleanId);
      setResults(prev => prev.filter(r => r.id !== cleanId));
    } catch (error) {
      console.error("Σφάλμα διαγραφής:", error);
      alert("⚠️ Αποτυχία διαγραφής.");
    }
  };

  const handleEdit = (result) => {
    localStorage.setItem("editResult", JSON.stringify(result));
    localStorage.setItem("editMode", "true");
    navigate("/add-field?edit=true");
  };

  const filteredAndSorted = results
    .filter(r => !filterCrop || r.cropType === filterCrop)
    .sort((a, b) => (sortOrder === 'desc' ? b.profit - a.profit : a.profit - b.profit));

  return (
    <div className="flex">
      <Sidebar
        username={localStorage.getItem('user')}
        onToggleSidebar={() => setSidebarOpen(prev => !prev)}
        isOpen={sidebarOpen}
      />

      <div
        className={`transition-all duration-300 ${sidebarOpen ? 'ml-64' : 'ml-16'} flex-1 p-8 min-h-screen bg-cover bg-center`}
        style={{ backgroundImage: `url(${backgroundImage})` }}
      >
        <h2 className="text-2xl font-bold mb-6">📊 Αποτελέσματα Καλλιεργειών</h2>

        <div className="flex flex-wrap gap-4 mb-6 items-center">
          <select
            value={filterCrop}
            onChange={(e) => setFilterCrop(e.target.value)}
            className="border p-2 rounded"
          >
            <option value="">🌿 Όλες οι Καλλιέργειες</option>
            <option value="σιτάρι">🌾 Σιτάρι</option>
            <option value="βαμβάκι">🧵 Βαμβάκι</option>
            <option value="ηλιόσπορος">🌻 Ηλίανθος</option>
          </select>

          <select
            value={sortOrder}
            onChange={(e) => setSortOrder(e.target.value)}
            className="border p-2 rounded"
          >
            <option value="desc">🔽 Κέρδος Φθίνουσα</option>
            <option value="asc">🔼 Κέρδος Αύξουσα</option>
          </select>

          <button
            onClick={() => setShowChart(!showChart)}
            className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded"
          >
            <ChartBarIcon className="w-5 h-5" />
            {showChart ? "Απόκρυψη Γραφήματος" : "📈 Προβολή Γραφήματος"}
          </button>
        </div>

        {showChart && <ProfitChart data={filteredAndSorted} />}

        {loading ? (
          <p>⏳ Φόρτωση...</p>
        ) : filteredAndSorted.length === 0 ? (
          <p>⚠️ Δεν υπάρχουν αποτελέσματα για αυτό το φίλτρο.</p>
        ) : (
          <div className="grid md:grid-cols-2 gap-4">
            {filteredAndSorted.map((res, index) => (
              <div key={res.id || index} className="relative bg-white p-4 shadow rounded border">
                <div className="absolute top-2 right-2 flex gap-2">
                  <button
                    onClick={() => handleEdit(res)}
                    className="text-gray-400 hover:text-yellow-500"
                    title="Επεξεργασία"
                  >
                    <PencilIcon className="w-5 h-5" />
                  </button>
                  <button
                    onClick={() => handleDelete(res.id)}
                    className="text-gray-400 hover:text-red-500"
                    title="Διαγραφή"
                  >
                    <XMarkIcon className="w-5 h-5" />
                  </button>
                </div>

                <h3 className="text-lg font-semibold mb-2">🏷 {res.fieldName}</h3>
                <p>🌾 Καλλιέργεια: {res.cropType}</p>
                <p>📐 Στρέμματα: {res.areaInStremma || res.area}</p>
                <p>🌱 Παραγωγή: {res.production || res.productionKg} κιλά</p>
                <p>💸 Έξοδα: {res.expenses} €</p>
                <p>💰 Έσοδα: {res.revenue} €</p>
                <p className="font-semibold text-green-600">📊 Κέρδος: {res.profit} €</p>
              </div>
            ))}
          </div>
        )}

        <div className="mt-8 flex gap-4">
          <button onClick={fetchResults} className="btn-secondary">🔄 Ανανέωση</button>
          <button onClick={() => window.history.back()} className="btn-secondary">🔙 Πίσω</button>
        </div>
      </div>
    </div>
  );
};

export default ResultsPage;
