import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";

const Checkout = () => {
  const { state } = useLocation();
  const { selectedSeats, total, discount } = state || {};  // Obtén los valores desde el estado

  const seatDiscount = selectedSeats && selectedSeats[0] ? selectedSeats[0].discount : 0;
  const seatType = selectedSeats && selectedSeats[0] ? selectedSeats[0].type : "";
  const seatCode = selectedSeats && selectedSeats[0] ? selectedSeats[0].code : "";

  const subtotal = total - discount;
  const tax = seatDiscount || 4.00;
  const finalTotal = subtotal + tax;

  const [paymentMethod, setPaymentMethod] = useState("card");
  const [showMessage, setShowMessage] = useState(false);  // Estado para controlar el mensaje
  const [messageContent, setMessageContent] = useState("");  // Estado para el contenido del mensaje
  const navigate = useNavigate();

  const handleChangePaymentMethod = (method) => {
    setPaymentMethod(method);
  };

  const handleSubmit = () => {
    // Mostrar el mensaje flotante
    setMessageContent(`Gracias por tu pago realizado con el método ${paymentMethod}.`);
    setShowMessage(true);

    // Redirigir después de 3 segundos (ajustar el tiempo según sea necesario)
    setTimeout(() => {
      navigate("/invoice");
    }, 3000);  // Tiempo en milisegundos antes de redirigir
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
                Discount (20%) <span className="ml-auto font-bold">-${discount.toFixed(2)}</span>
              </li>
              <li className="flex flex-wrap gap-4 text-sm">Tax <span className="ml-auto font-bold">${tax.toFixed(2)}</span></li>
              <hr />
              <li className="flex flex-wrap gap-4 text-base font-bold">
                Total <span className="ml-auto">${finalTotal.toFixed(2)}</span>
              </li>
            </ul>
            {/* Aquí puedes agregar información adicional si lo deseas */}
            <div className="mt-4 text-sm text-gray-600">
              <p>Seat Type: {seatType}</p>
              <p>Seat Code: {seatCode}</p>
            </div>
          </div>
        </div>
      </div>

      {/* Mensaje flotante */}
      {showMessage && (
        <div className="fixed bottom-10 left-1/2 transform -translate-x-1/2 bg-green-500 text-white p-4 rounded-md shadow-lg">
          {messageContent}
        </div>
      )}
    </div>
  );
};

export default Checkout;
