import React from 'react';
import { Pie } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend
} from 'chart.js';

ChartJS.register(ArcElement, Tooltip, Legend);

const SummaryPieChart = ({ totals }) => {
  const data = {
    labels: ['Έσοδα', 'Έξοδα', 'Κέρδος'],
    datasets: [
      {
        data: [
          totals.totalRevenue,
          totals.totalExpenses,
          totals.totalProfit
        ],
        backgroundColor: ['#3B82F6', '#EF4444', '#10B981'],
        borderColor: ['#ffffff', '#ffffff', '#ffffff'],
        borderWidth: 2,
      },
    ],
  };

  return (
    <div className="mt-8 bg-white p-6 rounded shadow">
      <h3 className="text-lg font-semibold text-center mb-4">📊 Κατανομή Εσόδων, Εξόδων και Κέρδους</h3>
      <Pie data={data} />
    </div>
  );
};

export default SummaryPieChart;
