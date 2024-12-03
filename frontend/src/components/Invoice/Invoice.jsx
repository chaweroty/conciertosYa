import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, Link, useLocation } from "react-router-dom";

const API_URL = "http://localhost:8080/invoices_details";

const Invoice = () => {
  const location = useLocation();
  const { invoice } = location.state || {}; 
  const [invoiceData, setInvoiceData] = useState(null);
  console.log("Datos de la factura recibidos:", invoice);

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (invoice) {
      FetchInvoiceDetailsForId();
    }
  }, [invoice]);

  const FetchInvoiceDetailsForId = async () => {
    try {
      const response = await axios.get(`${API_URL}/get-invoice-tickets/${invoice.id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setInvoiceData(response.data); // Set the fetched data to state
    } catch (err) {
      console.error(`Error: ${err.response.status} - ${err.response.data.message}`);
    }
  };
  
  console.log("Estos son los datos detallados de la factura",invoiceData);

  // Return a message if no invoice is found
  if (!invoice) {
    return <div>Factura no disponible</div>;
  }

  // Check if invoice data is fetched before rendering the details
  if (!invoiceData) {
    return <div>Cargando detalles de la factura...</div>;
  }

  // Static invoice data (if invoice data is not fetched yet)
  const invoiceDat = {
    company: "ConciertosYa",
    date: "01/05/2023",
    invoiceNumber: "INV12345",
    customer: {
      name: "John Doe",
      address: "123 Main St.",
      city: "Anytown, USA 12345",
      email: "johndoe@example.com",
    },
    items: [
      { description: "Product 1", amount: 100.0 },
      { description: "Product 2", amount: 50.0 },
      { description: "Product 3", amount: 75.0 },
    ],
    total: 225.0,
    note: "Thank you for your business!",
    paymentTerms: "Please remit payment within 30 days.",
  };

  return (
    <div className="bg-white border rounded-lg shadow-lg px-6 py-8 max-w-md mx-auto mt-8">
      {/* Header */}
      <h1 className="font-bold text-2xl my-4 text-center text-blue-600">
        {invoiceDat.company}
      </h1>
      <hr className="mb-2" />

      {/* Invoice Info */}
      <div className="flex justify-between mb-6">
        <h1 className="text-lg font-bold">Invoice</h1>
        <div className="text-gray-700 text-right">
          <div>Date: {invoiceDat.date}</div>
          <div>Invoice #: {invoiceDat.invoiceNumber}</div>
        </div>
      </div>

      {/* Customer Info */}
      <div className="mb-8">
        <h2 className="text-lg font-bold mb-4">Bill To:</h2>
        <div className="text-gray-700 mb-2">{invoiceDat.customer.name}</div>
        <div className="text-gray-700 mb-2">{invoiceDat.customer.address}</div>
        <div className="text-gray-700 mb-2">{invoiceDat.customer.city}</div>
        <div className="text-gray-700">{invoiceDat.customer.email}</div>
      </div>

      {/* Items Table */}
      <table className="w-full mb-8">
        <thead>
          <tr>
            <th className="text-left font-bold text-gray-700">Description</th>
            <th className="text-right font-bold text-gray-700">Amount</th>
          </tr>
        </thead>
        <tbody>
          {invoiceDat.items.map((item, index) => (
            <tr key={index}>
              <td className="text-left text-gray-700">{item.description}</td>
              <td className="text-right text-gray-700">${item.amount.toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
        <tfoot>
          <tr>
            <td className="text-left font-bold text-gray-700">Total</td>
            <td className="text-right font-bold text-gray-700">
              ${invoiceDat.total.toFixed(2)}
            </td>
          </tr>
        </tfoot>
      </table>

      {/* Footer Notes */}
      <div className="text-gray-700 mb-2">{invoiceDat.note}</div>
      <div className="text-gray-700 text-sm">{invoiceDat.paymentTerms}</div>
    </div>
  );
};

export default Invoice;
