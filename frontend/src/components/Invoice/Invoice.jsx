import React, { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import BackToHomeButton from '../Button/BackToHomeButton ';

const API_URL = "http://localhost:8080/invoices_details";

const Invoice = () => {
  const location = useLocation();
  const { invoice } = location.state || {};
  const [invoiceData, setInvoiceData] = useState(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (invoice) {
      FetchInvoiceDetailsForId();
    }
  }, [invoice]);

  const FetchInvoiceDetailsForId = async () => {
    try {
      const response = await axios.get(
        `${API_URL}/get-invoice-tickets/${invoice.id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setInvoiceData(response.data);
    } catch (err) {
      console.error(`Error: ${err.response?.status} - ${err.response?.data?.message}`);
    }
  };

  // Return a message if no invoice is found
  if (!invoice) {
    return <div>Factura no disponible</div>;
  }

  // Check if invoice data is fetched before rendering the details
  if (!invoiceData) {
    return <div>Cargando detalles de la factura...</div>;
  }

  // Get invoice details
  const invoiceDetails = invoiceData.ourInvoicesDetailList;
  const invoiceDetailss = invoiceData.ourInvoicesDetailList[0];
  const { issueDate, paymentMethod, client } = invoiceDetailss.invoice;
  const { event, price, seat, row, discount } = invoiceDetailss.ticket;

  return (
    <div className="max-w-md mx-auto mt-8 py-20">
      {/* El contenedor de la factura */}
      <div className="bg-white border rounded-lg shadow-lg px-6 py-8 mb-8">
        <h1 className="font-bold text-2xl my-4 text-center text-blue-600">
          ConciertosYa
        </h1>
        <hr className="mb-2" />

        {/* Invoice Info */}
        <div className="flex justify-between mb-6">
          <h1 className="text-lg font-bold">Factura</h1>
          <div className="text-gray-700 text-right">
            <div>Date: {invoice.issueDate}</div>
            <div>Invoice #: {invoice.id}</div>
          </div>
        </div>

        {/* Customer Info */}
        <div className="mb-8">
          <h2 className="text-lg font-bold mb-4">Facturado a:</h2>
          <div className="text-gray-700 mb-2">{client.name}</div>
          <div className="text-gray-700 mb-2">{client.city}</div>
          <div className="text-gray-700 mb-2">{client.email}</div>
        </div>

        {/* Items Table */}
        <table className="w-full mb-8">
          <thead>
            <tr>
              <th className="text-left font-bold text-gray-700">Evento</th>
              <th className="text-right font-bold text-gray-700">Cantidad</th>
              <th className="text-right font-bold text-gray-700">Asiento</th>
              <th className="text-right font-bold text-gray-700">Precio con descuento</th>
            </tr>
          </thead>
          <tbody>
            {invoiceDetails.map((item, index) => {
              const { event, price, seat, row, priceWithDiscount } = item.ticket;
              return (
                <tr key={index}>
                  <td className="text-left text-gray-700">{event?.name || "Evento no disponible"}</td>
                  <td className="text-right text-gray-700">${price.toFixed(2)}</td>
                  <td className="text-right text-gray-700">{`Silla ${seat.id || "N/A"}`}</td>
                  <td className="text-right text-gray-700">
                    ${priceWithDiscount ? priceWithDiscount.toFixed(2) : "0.00"}
                  </td>
                </tr>
              );
            })}
          </tbody>
          <tfoot>
            <tr>
              <td className="text-left font-bold text-gray-700">Total</td>
              <td className="text-right font-bold text-gray-700">${invoice.total}</td>
            </tr>
          </tfoot>
        </table>

        {/* Footer Notes */}
        <div className="text-gray-700 mb-2">Gracias por confiar en nosotros!</div>
      </div>

      <div className="flex items-center justify-center">
  <BackToHomeButton />
</div>
    </div>
  );
};

export default Invoice;
