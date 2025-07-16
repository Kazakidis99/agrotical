
import React, { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import { createField, calculateCropResults } from '../services/fieldApi';
import { updateResult } from '../services/resultsApi';
import backgroundImage from '../assets/Image.png';

const AddField = () => {
  const [step, setStep] = useState(1);
  const [fieldName, setFieldName] = useState('');
  const [area, setArea] = useState('');
  const [cropType, setCropType] = useState('');
  const [fertilized, setFertilized] = useState(false);
  const [sprayed, setSprayed] = useState(false);
  const [irrigated, setIrrigated] = useState(false);
  const [careEnabled, setCareEnabled] = useState(false);
  const [results, setResults] = useState([]);
  const [editResultId, setEditResultId] = useState(null);

  useEffect(() => {
    const queryParams = new URLSearchParams(window.location.search);
    const isEdit = queryParams.get("edit") === "true";

    if (!isEdit) {
      localStorage.removeItem("editMode");
      localStorage.removeItem("editResult");
      return;
    }

    const stored = localStorage.getItem("editResult");
    if (stored) {
      const result = JSON.parse(stored);
      setStep(2);
      setFieldName(result.fieldName);
      setArea(result.areaInStremma);
      setCropType(result.cropType);
      setFertilized(result.fertilized);
      setSprayed(result.sprayed);
      setIrrigated(result.irrigated);
      setEditResultId(result.id);
      setCareEnabled(result.fertilized || result.sprayed || result.irrigated);
    }
  }, []);

  const nextStep = () => setStep((prev) => prev + 1);
  const prevStep = () => setStep((prev) => prev - 1);

  /*const calculateResult = () => {
    const cropSettings = {
      "σιτάρι": { yield: 17, seedCost: 20, sellPrice: 0.38 },
      "βαμβάκι": { yield: 20, seedCost: 105, sellPrice: 0.59 },
      "ηλιόσπορος": { yield: 18, seedCost: 90, sellPrice: 0.47 }
    };

    const crop = cropSettings[cropType];
    const parsedArea = parseInt(area);
    const seedKg = parsedArea * 15;
    const sacks = Math.ceil(seedKg / 20);
    const seedCost = sacks * crop.seedCost;
    const seedCostPerStremma = Math.round(seedCost / parsedArea);

    const baseYieldPerStremma = 15 * crop.yield;
    let productionPerStremma = baseYieldPerStremma;

    if (fertilized) productionPerStremma *= 1.35;
    if (sprayed) productionPerStremma *= 1.16;
    if (!irrigated) productionPerStremma *= 0.85;

    productionPerStremma = Math.round(productionPerStremma);
    const productionTotal = productionPerStremma * parsedArea;

    const fertCostPerStremma = fertilized ? 23 : 0;
    const sprayCostPerStremma = sprayed ? 12 : 0;
    const expensesPerStremma = seedCostPerStremma + fertCostPerStremma + sprayCostPerStremma;
    const totalExpenses = expensesPerStremma * parsedArea;

    const revenuePerStremma = Math.round(productionPerStremma * crop.sellPrice);
    const totalRevenue = revenuePerStremma * parsedArea;

    const profit = totalRevenue - totalExpenses;

    const result = {
      fieldName,
      cropType,
      area: parsedArea,
      productionPerStremma,
      productionTotal,
      expensesPerStremma,
      totalExpenses,
      revenuePerStremma,
      totalRevenue,
      profit
    };

    setResults([result]);
    setStep(7);
  };*/

  const saveResult = async () => {
  const confirm = window.confirm("Είσαι σίγουρος ότι θέλεις να αποθηκεύσεις το χωράφι;");
  if (!confirm) return;

  const rawUserId = localStorage.getItem("userId");
  const userId = rawUserId?.trim();

  if (!userId) {
    alert("Δεν υπάρχει χρήστης!");
    return;
  }

  try {
    let fieldId;

    if (editResultId) {
      await updateResult(editResultId, {
        cropType,
        areaInStremma: parseInt(area),
        fertilized,
        sprayed,
        irrigated
      });

      localStorage.removeItem("editMode");
      localStorage.removeItem("editResult");

      alert("✅ Το χωράφι ενημερώθηκε επιτυχώς!");
      return;
    } else {
      
      const fieldResponse = await createField(userId, {
        name: fieldName,
        areaInStremma: parseInt(area)
      });

      fieldId = fieldResponse.data?.id;

      if (!fieldId) {
        alert("⚠️ Το backend δεν επέστρεψε ID χωραφιού.");
        return;
      }
    }

    const resultResponse = await calculateCropResults({
  fieldId,
  cropType,
  areaInStremma: parseInt(area),
  fertilized,
  sprayed,
  irrigated
});

const backendResult = resultResponse.data;

console.log("✅ Backend result:", backendResult); 

const result = {
  fieldName: fieldName,
  cropType: backendResult.cropType,
  area: backendResult.area || backendResult.areaInStremma,
  production: backendResult.production || backendResult.productionTotal || 0,
  productionPerStremma: backendResult.productionPerStremma,
  productionTotal: backendResult.productionTotal,
  expenses: backendResult.expenses || backendResult.totalExpenses || 0,
  expensesPerStremma: backendResult.expensesPerStremma,
  totalExpenses: backendResult.totalExpenses,
  revenue: backendResult.revenue || backendResult.totalRevenue || 0,
  revenuePerStremma: backendResult.revenuePerStremma,
  totalRevenue: backendResult.totalRevenue,
  profit: backendResult.profit || 0,
};

setResults([result]);
setStep(7);

  } catch (error) {
    console.error(error);
    alert("❌ Σφάλμα αποθήκευσης. Δοκιμάστε ξανά.");
  }
};


  return (
    <div className="flex min-h-screen">
      <Sidebar username={localStorage.getItem('user')} />

      
      <div
        className="flex-1 p-8 bg-cover bg-center"
        style={{ backgroundImage: `url(${backgroundImage})` }}
      >
        <div className="max-w-xl mx-auto bg-white bg-opacity-90 shadow-lg rounded-lg p-6">
          
          <div className="mb-6">
            <div className="w-full bg-gray-200 rounded-full h-2.5">
              <div
                className="bg-green-500 h-2.5 rounded-full transition-all duration-500"
                style={{ width: `${(step > 5 ? 5 : step - 1) / 5 * 100}%` }}
              ></div>
            </div>
            <p className="text-sm text-center mt-2 text-gray-500">
                Βήμα {step > 5 ? 5 : step} από 5
                </p>

          </div>
          
          {step === 1 && (
            <>
              <h2 className="text-xl font-semibold mb-4">1. Όνομα Χωραφιού</h2>
              <input
                className="w-full border border-gray-300 p-2 rounded mb-4"
                type="text"
                value={fieldName}
                onChange={(e) => setFieldName(e.target.value)}
                placeholder="Εισάγετε όνομα χωραφιού"
              />
              <button onClick={nextStep} className="btn-primary">Επόμενο</button>
            </>
          )}

          {step === 2 && (
            <>
              <h2 className="text-xl font-semibold mb-4">2. Στρέμματα</h2>
              <input
                className="w-full border border-gray-300 p-2 rounded mb-4"
                type="number"
                value={area}
                onChange={(e) => setArea(e.target.value)}
                placeholder="Πόσα στρέμματα"
              />
              <div className="flex justify-between">
                <button onClick={prevStep} className="btn-secondary">Προηγούμενο</button>
                <button onClick={nextStep} className="btn-primary">Επόμενο</button>
              </div>
            </>
          )}

          {step === 3 && (
            <>
              <h2 className="text-xl font-semibold mb-4">3. Είδος Καλλιέργειας</h2>
              <select
                value={cropType}
                onChange={(e) => setCropType(e.target.value)}
                className="w-full border border-gray-300 p-2 rounded mb-4"
              >
                <option value="">-- Επιλογή --</option>
                <option value="σιτάρι">Σιτάρι</option>
                <option value="βαμβάκι">Βαμβάκι</option>
                <option value="ηλιόσπορος">Ηλίανθος</option>
              </select>
              <div className="flex justify-between">
                <button onClick={prevStep} className="btn-secondary">Προηγούμενο</button>
                <button onClick={nextStep} className="btn-primary">Επόμενο</button>
              </div>
            </>
          )}

          {step === 4 && (
              <>
                <h2 className="text-xl font-semibold mb-4">4. Φροντίδα Χωραφιού</h2>

                
                <label className="block mb-4">
                  
                  <select
                    className="block mt-1 w-full border p-2 rounded"
                    value={careEnabled ? "yes" : "no"}
                    onChange={(e) => setCareEnabled(e.target.value === "yes")}
                  >
                    <option value="no">Όχι</option>
                    <option value="yes">Ναι</option>
                  </select>
                </label>

                
                {careEnabled && (
                  <div className="space-y-4 mb-6">
                    
                    <div className="flex items-center justify-between">
                      <span className="text-gray-700">Λίπασμα</span>
                      <button
                        onClick={() => setFertilized(!fertilized)}
                        className={`w-12 h-6 flex items-center rounded-full p-1 transition duration-300 ease-in-out
                          ${fertilized ? 'bg-green-500' : 'bg-gray-300'}`}
                      >
                        <div
                          className={`bg-white w-4 h-4 rounded-full shadow-md transform transition duration-300 ease-in-out
                            ${fertilized ? 'translate-x-6' : 'translate-x-0'}`}
                        />
                      </button>
                    </div>

                    
                    <div className="flex items-center justify-between">
                      <span className="text-gray-700">Ράντισμα</span>
                      <button
                        onClick={() => setSprayed(!sprayed)}
                        className={`w-12 h-6 flex items-center rounded-full p-1 transition duration-300 ease-in-out
                          ${sprayed ? 'bg-green-500' : 'bg-gray-300'}`}
                      >
                        <div
                          className={`bg-white w-4 h-4 rounded-full shadow-md transform transition duration-300 ease-in-out
                            ${sprayed ? 'translate-x-6' : 'translate-x-0'}`}
                        />
                      </button>
                    </div>

                    
                    <div className="flex items-center justify-between">
                      <span className="text-gray-700">Άρδευση</span>
                      <button
                        onClick={() => setIrrigated(!irrigated)}
                        className={`w-12 h-6 flex items-center rounded-full p-1 transition duration-300 ease-in-out
                          ${irrigated ? 'bg-green-500' : 'bg-gray-300'}`}
                      >
                        <div
                          className={`bg-white w-4 h-4 rounded-full shadow-md transform transition duration-300 ease-in-out
                            ${irrigated ? 'translate-x-6' : 'translate-x-0'}`}
                        />
                      </button>
                    </div>
                  </div>
                )}

                
                <div className="bg-blue-50 border border-blue-300 text-blue-800 p-3 rounded mb-6">
                  💧 Ο παράγοντας βροχής έχει ήδη υπολογιστεί στην τελική απόδοση.
                </div>

                
                <div className="flex justify-between">
                  <button onClick={prevStep} className="btn-secondary">Προηγούμενο</button>
                  <button onClick={nextStep} className="btn-primary">Επόμενο</button>
                </div>
              </>
              )}



          {step === 5 && (
            <>
              <h2 className="text-xl font-semibold mb-4">5. Αξιολόγηση Φροντίδας</h2>
              <div className="mb-4">
                {(() => {
                  const count = [fertilized, sprayed, irrigated].filter(Boolean).length;
                  if (count === 3) return <p className="text-green-600 font-medium">✅ Πλήρης φροντίδα</p>;
                  if (count === 0) return <p className="text-red-600 font-medium">⚠️ Καμία φροντίδα</p>;
                  return <p className="text-yellow-600 font-medium">🟡 Μερική φροντίδα</p>;
                })()}
              </div>
              <div className="flex justify-between">
                <button onClick={prevStep} className="btn-secondary">Προηγούμενο</button>
                <button onClick={saveResult} className="btn-primary">Υπολογισμός & Αποθήκευση</button>
              </div>
            </>
          )}

          {step === 7 && (
          <>
          <h2 className="text-xl font-semibold mb-4">Αποτελέσματα Χωραφιού</h2>
          {results.map((res, index) => (
          <div key={index} className="bg-gray-100 p-4 mb-4 rounded shadow space-y-2">
          <h3 className="text-lg font-bold border-b pb-2 mb-2">{res.fieldName}</h3>
        
        <div className="grid grid-cols-2 gap-3">
          <div className="bg-white p-3 rounded border shadow-sm">
            <p className="font-medium">🌾 Καλλιέργεια</p>
            <p>{res.cropType}</p>
          </div>

          <div className="bg-white p-3 rounded border shadow-sm">
            <p className="font-medium">📐 Στρέμματα</p>
            <p>{res.area}</p>
          </div>

          <div className="bg-white p-3 rounded border shadow-sm">
            <p className="font-medium">🌱 Παραγωγή/στρ</p>
            <p>{res.productionPerStremma} κιλά</p>
          </div>

          <div className="bg-white p-3 rounded border shadow-sm">
            <p className="font-medium">📦 Σύνολο Παραγωγής</p>
            <p>{res.productionTotal} κιλά</p>
          </div>

          <div className="bg-white p-3 rounded border shadow-sm">
            <p className="font-medium">💸 Έξοδα/στρ</p>
            <p>{res.expensesPerStremma} €</p>
          </div>

          <div className="bg-white p-3 rounded border shadow-sm">
            <p className="font-medium">💸💸 Σύνολο Εξόδων</p>
            <p>{res.totalExpenses} €</p>
          </div>

          <div className="bg-white p-3 rounded border shadow-sm">
            <p className="font-medium">💰 Έσοδα/στρ</p>
            <p>{res.revenuePerStremma} €</p>
          </div>

          <div className="bg-white p-3 rounded border shadow-sm">
            <p className="font-medium">💰💰 Σύνολο Εσόδων</p>
            <p>{res.totalRevenue} €</p>
          </div>

          <div className="col-span-2 bg-white p-3 rounded border shadow-sm text-center">
            <p className="font-medium">📊 Κέρδος</p>
            <p className="text-lg font-semibold text-green-600">{res.profit} €</p>
          </div>
        </div>
      </div>
      ))}
      <button onClick={() => setStep(1)} className="btn-secondary w-full">➕ Νέο Χωράφι</button>
      </>
    )}


          </div>
      </div>
    </div>
  );
};

export default AddField;
