import React from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from 'recharts';

const ProfitChart = ({ data }) => {
  const chartData = data.map((item) => ({
    name: item.fieldName,
    profit: Number(item.profit),
  }));

  return (
    <div className="bg-white p-4 rounded shadow mb-6">
      <h3 className="text-lg font-bold mb-4">📈 Κέρδος ανά Χωράφι</h3>
      <ResponsiveContainer width="100%" height={300}>
        <BarChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis label={{ value: '€', position: 'insideTopLeft', offset: -5 }} />
          <Tooltip formatter={(value) => `${value} €`} />
          <Legend />
          <Bar dataKey="profit" fill="#4ade80" name="Κέρδος (€)" />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
};

export default ProfitChart;
