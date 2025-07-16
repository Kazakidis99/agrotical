import React, { useState } from 'react';
import Sidebar from '../components/Sidebar';
import { getUserResults } from '../services/resultsApi';
import SummaryPieChart from '../components/SummaryPieChart';
import backgroundImage from '../assets/Image.png'; 

const Summary = () => {
  const [summaryVisible, setSummaryVisible] = useState(false);
  const [totals, setTotals] = useState(null);
  const [loading, setLoading] = useState(false);
  const userId = localStorage.getItem("userId");

  const handleSummaryCalculation = async () => {
    if (!userId) return;
    setLoading(true);

    try {
      const res = await getUserResults(userId);
      const results = res.data;

      if (!results || results.length === 0) {
        alert("⚠️ Δεν υπάρχουν δεδομένα για υπολογισμό.");
        setLoading(false);
        return;
      }

      const totals = results.reduce(
        (acc, curr) => ({
          totalProduction: acc.totalProduction + parseFloat(curr.production || 0),
          totalRevenue: acc.totalRevenue + parseFloat(curr.revenue || 0),
          totalExpenses: acc.totalExpenses + parseFloat(curr.expenses || 0),
          totalProfit: acc.totalProfit + parseFloat(curr.profit || 0),
          totalArea: acc.totalArea + parseFloat(curr.area || 0),
        }),
        {
          totalProduction: 0,
          totalRevenue: 0,
          totalExpenses: 0,
          totalProfit: 0,
          totalArea: 0,
        }
      );

      setTotals(totals);
      setSummaryVisible(true);
    } catch (error) {
      console.error("❌ Σφάλμα στον υπολογισμό:", error);
      alert("❌ Απέτυχε ο υπολογισμός. Προσπαθήστε ξανά.");
    }

    setLoading(false);
  };

  return (
    <div className="flex">
      <Sidebar username={localStorage.getItem("user")} />

      <div
        className="flex-1 p-6 md:p-10 min-h-screen bg-gray-50"
        style={{
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: 'cover',
          backgroundRepeat: 'no-repeat',
          backgroundPosition: 'center',
        }}
      >
        {!summaryVisible ? (
          <div className="max-w-xl mx-auto bg-white shadow-md rounded-lg p-8 text-center">
            <h2 className="text-3xl font-bold text-green-700 mb-4">📋 Απολογισμός Χρονιάς</h2>
            <p className="text-gray-600 mb-6">Θέλετε να πραγματοποιήσουμε τον τελικό απολογισμό της χρονιάς;</p>
            <button
              onClick={handleSummaryCalculation}
              disabled={loading}
              className="btn-primary px-6 py-2"
            >
              {loading ? "⏳ Υπολογισμός..." : "🧮 Τελικός Υπολογισμός"}
            </button>
          </div>
        ) : (
          <div className="max-w-5xl mx-auto">
            <h2 className="text-3xl font-bold text-center text-green-800 mb-6">✅ Τελικά Αποτελέσματα</h2>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-10">
              <div className="bg-white p-6 rounded-lg shadow text-center">
                <p className="text-gray-500 text-sm">📐 Συνολικά Στρέμματα</p>
                <p className="text-2xl font-bold text-gray-800">{totals.totalArea.toFixed(2)}</p>
              </div>
              <div className="bg-white p-6 rounded-lg shadow text-center">
                <p className="text-gray-500 text-sm">📦 Συνολική Παραγωγή</p>
                <p className="text-2xl font-bold text-gray-800">{totals.totalProduction.toFixed(2)} κιλά</p>
              </div>
              <div className="bg-white p-6 rounded-lg shadow text-center">
                <p className="text-gray-500 text-sm">💸 Συνολικά Έξοδα</p>
                <p className="text-2xl font-bold text-red-600">{totals.totalExpenses.toFixed(2)} €</p>
              </div>
              <div className="bg-white p-6 rounded-lg shadow text-center">
                <p className="text-gray-500 text-sm">💰 Συνολικά Έσοδα</p>
                <p className="text-2xl font-bold text-blue-600">{totals.totalRevenue.toFixed(2)} €</p>
              </div>
              <div className="md:col-span-2 bg-green-100 p-6 rounded-lg shadow text-center">
                <p className="text-gray-700 text-sm">📊 Καθαρό Κέρδος</p>
                <p className="text-3xl font-bold text-green-700">{totals.totalProfit.toFixed(2)} €</p>
              </div>
            </div>

            <SummaryPieChart totals={totals} />

            <div className="flex justify-center">
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Summary;
