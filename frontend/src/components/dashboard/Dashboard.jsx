import axios from "axios";
import React, { useEffect, useState } from "react";

const API_URL = "http://localhost:8080/events";
const API_URL2 = "http://localhost:8080/invoices";
const API_URL3 = "http://localhost:8080/auth";

const Dashboard = () => {
  const [events, setEvents] = useState([]);
  const [invoices, setInvoices] = useState([]);
  const [totalEvents, setTotalEvents] = useState(0); 
  const [totalRevenue, setTotalRevenue] = useState(0); 
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchEvents();
    fetchInvoices();
  }, []);

  useEffect(() => {
    setTotalEvents(events.length); 
  }, [events]);

  // Calcular la facturación total
  useEffect(() => {
    const total = invoices.reduce((sum, invoice) => sum + invoice.total, 0); 
    setTotalRevenue(total);
  }, [invoices]);

  const fetchEvents = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_URL}/get-all`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setEvents(response.data.ourEventsList || []);
    } catch (err) {
      setError(
        "Error al obtener los eventos. Verifica tu conexión o permisos."
      );
      console.error("Error fetching events:", err);
    } finally {
      setLoading(false);
    }
  };

  const fetchInvoices = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_URL2}/get-all`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setInvoices(response.data.ourInvoicesList || []);
    } catch (err) {
      setError(
        "Error al obtener las facturas. Verifica tu conexión o permisos."
      );
      console.error("Error fetching invoices:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="h-screen dark:bg-gray-800 flex justify-center items-center">
      <section className="grid gap-6 md:grid-cols-3 p-4 md:p-8 max-w-5xl mx-auto w-full">
        <div className="p-6 bg-white shadow rounded-2xl dark:bg-gray-900">
          <dl className="space-y-2">
            <dt className="text-sm font-medium text-gray-500 dark:text-gray-400">
              Usuarios Registrados
            </dt>
            <dd className="text-5xl font-light md:text-6xl dark:text-white">
              20
            </dd>
            <dd className="flex items-center space-x-1 text-sm font-medium text-green-500 dark:text-green-400">
              <span>10 </span>
              <svg
                className="w-7 h-7"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
              >
                <path
                  stroke="currentColor"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="1.5"
                  d="M17.25 15.25V6.75H8.75"
                ></path>
                <path
                  stroke="currentColor"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="1.5"
                  d="M17 7L6.75 17.25"
                ></path>
              </svg>
            </dd>
          </dl>
        </div>

        <div className="p-6 bg-white shadow rounded-2xl dark:bg-gray-900">
          <dl className="space-y-2">
            <dt className="text-sm font-medium text-gray-500 dark:text-gray-400">
              Eventos
            </dt>
            <dd className="text-5xl font-light md:text-6xl dark:text-white">
              {totalEvents}
            </dd>
            <dd className="flex items-center space-x-1 text-sm font-medium text-green-500 dark:text-green-400">
              <span>7% </span>
              <svg
                className="w-7 h-7"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
              >
                <path
                  stroke="currentColor"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="1.5"
                  d="M17.25 15.25V6.75H8.75"
                ></path>
                <path
                  stroke="currentColor"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="1.5"
                  d="M17 7L6.75 17.25"
                ></path>
              </svg>
            </dd>
          </dl>
        </div>

        <div className="p-6 bg-white shadow rounded-2xl dark:bg-gray-900">
          <dl className="space-y-2">
            <dt className="text-sm font-medium text-gray-500 dark:text-gray-400">
              Facturación total:
            </dt>
            <dd className="text-5xl font-light md:text-6xl dark:text-white">
              ${totalRevenue.toFixed(2)} {/* Mostrar la facturación total */}
            </dd>
            <dd className="flex items-center space-x-1 text-sm font-medium text-green-500 dark:text-green-400">
              <span>3% </span>
              <svg
                className="w-7 h-7"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
              >
                <path
                  stroke="currentColor"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="1.5"
                  d="M17.25 15.25V6.75H8.75"
                ></path>
                <path
                  stroke="currentColor"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="1.5"
                  d="M17 7L6.75 17.25"
                ></path>
              </svg>
            </dd>
          </dl>
        </div>
      </section>
    </div>
  );
};

export default Dashboard;
