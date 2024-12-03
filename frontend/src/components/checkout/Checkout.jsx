import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";

const API_URL = "http://localhost:8080/invoice/add";

const Checkout = () => {
  const { state } = useLocation();
  const { event, selectedSeats = [], total = 0, discount = 0 } = state || {};

  const subtotal = selectedSeats.reduce((total, seat) => total + seat.price, 0);
  const tax = selectedSeats.reduce((totalTax, seat) => totalTax + (seat.tax || 0), 0);
  const totalDiscount = selectedSeats.reduce(
    (totalDiscount, seat) => totalDiscount + (seat.price * (seat.discount / 100)),
    0
  );
  const finalTotal = subtotal - totalDiscount;

  const [paymentMethod, setPaymentMethod] = useState("CREDIT_CARD");
  const [showMessage, setShowMessage] = useState(false);
  const [messageContent, setMessageContent] = useState("");
  const navigate = useNavigate();

  const handleChangePaymentMethod = (method) => {
    setPaymentMethod(method);
  };

  const handleSubmit = async () => {
    setMessageContent(`Gracias por tu pago realizado con el método ${paymentMethod}.`);
    setShowMessage(true);

    try {
      
      const invoiceData = {
        issueDate: new Date().toISOString().split('T')[0],  // Fecha de emisión
        total: finalTotal,
        paymentMethod: paymentMethod,
        client: { id: 3 }, 
        ourSeatsList: selectedSeats.map(seat => seat.id), 
        buyingDate: new Date().toISOString().split('T')[0],  // Fecha de compra
        event: {
          id: event.id, 
        },
      };

      await axios.post(API_URL, invoiceData);
      setTimeout(() => {
        navigate("/invoice");
      }, 3000);
    } catch (error) {
      console.error("Error al crear la factura:", error);
      setMessageContent("Hubo un error al procesar el pago. Intenta de nuevo.");
      setShowMessage(true);
    }
  };

  return (
    <div className="font-[sans-serif] lg:flex lg:items-center lg:justify-center lg:h-screen max-lg:py-4">
      <div className="bg-purple-100 p-8 w-full max-w-5xl max-lg:max-w-xl mx-auto rounded-md">
        <h2 className="text-3xl font-extrabold text-gray-800 text-center">Checkout</h2>

        <div className="grid lg:grid-cols-3 gap-6 max-lg:gap-8 mt-16">
          <div className="lg:col-span-2">
            <h3 className="text-lg font-bold text-gray-800">Choose your payment method</h3>

            <div className="grid gap-4 sm:grid-cols-2 mt-4">
              <div className="flex items-center">
                <input
                  type="radio"
                  className="w-5 h-5 cursor-pointer"
                  id="card"
                  checked={paymentMethod === "card"}
                  onChange={() => handleChangePaymentMethod("card")}
                />
                <label htmlFor="card" className="ml-4 flex gap-2 cursor-pointer">
                  <img src="https://readymadeui.com/images/visa.webp" className="w-12" alt="card1" />
                  <img src="https://readymadeui.com/images/american-express.webp" className="w-12" alt="card2" />
                  <img src="https://readymadeui.com/images/master.webp" className="w-12" alt="card3" />
                </label>
              </div>

              <div className="flex items-center">
                <input
                  type="radio"
                  className="w-5 h-5 cursor-pointer"
                  id="paypal"
                  checked={paymentMethod === "paypal"}
                  onChange={() => handleChangePaymentMethod("paypal")}
                />
                <label htmlFor="paypal" className="ml-4 flex gap-2 cursor-pointer">
                  <img src="https://readymadeui.com/images/paypal.webp" className="w-20" alt="paypalCard" />
                </label>
              </div>
            </div>

            <form className="mt-8">
              <div className="flex flex-wrap gap-4 mt-8">
                <button
                  type="button"
                  className="px-7 py-3.5 text-sm tracking-wide bg-white hover:bg-gray-50 text-gray-800 rounded-md"
                >
                  Pay later
                </button>
                <button
                  type="button"
                  onClick={handleSubmit}
                  className="px-7 py-3.5 text-sm tracking-wide bg-blue-600 text-white rounded-md hover:bg-blue-700"
                >
                  Submit
                </button>
              </div>
            </form>
          </div>

          <div className="bg-white p-6 rounded-md max-lg:-order-1">
            <h3 className="text-lg font-bold text-gray-800">Summary</h3>
            <ul className="text-gray-800 mt-6 space-y-3">
              <li className="flex flex-wrap gap-4 text-sm">
                Sub total <span className="ml-auto font-bold">${subtotal.toFixed(2)}</span>
              </li>
              <li className="flex flex-wrap gap-4 text-sm">
                Discount <span className="ml-auto font-bold">-${discount.toFixed(2)}</span>
              </li>
              <li className="flex flex-wrap gap-4 text-sm">Tax <span className="ml-auto font-bold">${tax.toFixed(2)}</span></li>
              <hr />
              <li className="flex flex-wrap gap-4 text-base font-bold">
                Total <span className="ml-auto">${finalTotal.toFixed(2)}</span>
              </li>
            </ul>

            <div className="mt-4 text-sm text-gray-600">
              <h4 className="font-bold">Selected Seats:</h4>
              <ul>
                {selectedSeats.map((seat, index) => (
                  <li key={index}>
                    {seat.type} - {seat.code} (${seat.price}) - Discount: {seat.discount}%
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>

      {showMessage && (
        <div className="fixed bottom-10 left-1/2 transform -translate-x-1/2 bg-green-500 text-white p-4 rounded-md shadow-lg">
          {messageContent}
        </div>
      )}
    </div>
  );
};

export default Checkout;
