import React, { useState, useEffect } from "react";
import Seat from "./Seat";
import { FaPersonRays } from "react-icons/fa6";
import { MdOutlineChair } from "react-icons/md";
import { RiMoneyRupeeCircleLine } from "react-icons/ri";
import { Link } from "react-router-dom";
import axios from "axios";

const ConcertSeatLayout = ({ seats }) => {
  const [selectedSeats, setSelectedSeats] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const handleSeatClick = (seat) => {
    if (selectedSeats.some((selectedSeat) => selectedSeat.id === seat.id)) {
      // Si el asiento ya está seleccionado, deseleccionarlo
      setSelectedSeats(selectedSeats.filter((selectedSeat) => selectedSeat.id !== seat.id));
    } else if (selectedSeats.length < 10) {
      // Si hay menos de 10 asientos seleccionados, añadirlo
      setSelectedSeats([...selectedSeats, seat]);
    } else {
      // Si ya hay 10 asientos seleccionados, mostrar una alerta
      alert("Solo puedes seleccionar un máximo de 10 asientos");
    }
  };

  useEffect(() => {
    if (seats && seats.length > 0) {
      setIsLoading(false);
    }
  }, [seats]);

  if (isLoading) {
    return <p>Cargando asientos...</p>;
  }
  return (
    <div className="space-y-5">
      <h2 className="text-xl text-neutral-800 dark:text-neutral-100 font-medium">
        Elige un asiento
      </h2>

      {/* Seat Layout */}
      <div className="flex flex-col items-center">
        {/* Artista */}
        <div className="w-full flex justify-center items-center mb-6">
          <div className="flex flex-col items-center">
            <FaPersonRays className="text-6xl text-black-600" />
            <span className="mt-2 text-lg font-semibold text-neutral-700 dark:text-neutral-300">
              Artista
            </span>
            <div className="w-full border-b border-gray-300 mt-4"></div>
          </div>
        </div>

        {/* Asientos */}
        <div className="flex flex-col items-center space-y-4">
          {seats.length > 0 ? (
            <>
              {[...Array(Math.ceil(seats.length / 10))].map((_, rowIndex) => (
                <div
                  key={rowIndex}
                  className="w-full grid grid-cols-10 gap-x-3"
                >
                  {seats
                    .slice(rowIndex * 10, rowIndex * 10 + 10)
                    .map((seat) => (
                      <Seat
                        key={seat.id}
                        seatNumber={seat.code}
                        isSelected={selectedSeats.some((s) => s.id === seat.id)}
                        onClick={() => handleSeatClick(seat)}
                        seatType={seat.type}
                      />
                    ))}
                </div>
              ))}
            </>
          ) : (
            <p>No hay asientos disponibles</p>
          )}
        </div>
      </div>

      {/* Instrucciones e información */}
      <div className="space-y-3 w-28">
        <div className="flex items-center gap-x-2">
          <MdOutlineChair className="text-lg text-neutral-500 -rotate-300" />
          <p className="text-neutral-900 dark:text-neutral-200 text-sm font-normal">
            Disponibles
          </p>
        </div>
        <div className="flex items-center gap-x-2">
          <MdOutlineChair className="text-lg text-red-500 -rotate-300" />
          <p className="text-neutral-900 dark:text-neutral-200 text-sm font-normal">
            Reservado
          </p>
        </div>
        <div className="flex items-center gap-x-2">
          <MdOutlineChair className="text-lg text-violet-500 -rotate-300" />
          <p className="text-neutral-900 dark:text-neutral-200 text-sm font-normal">
            Seleccionado
          </p>
        </div>
        <div className="flex items-center gap-x-2">
          <RiMoneyRupeeCircleLine className="text-lg text-neutral-500" />
          <p className="text-neutral-900 dark:text-neutral-200 text-sm font-normal">
            Precio
          </p>
        </div>
      </div>

      {/* Asientos seleccionados */}
      {selectedSeats.length > 0 && (
        <div className="!mt-10">
          <h3 className="text-lg font-bold">Asientos Seleccionados:</h3>
          <div className="flex flex-wrap">
            {selectedSeats.map((seat) => (
              <div
                key={seat.id}
                className="w-10 h-10 rounded-md m-1.5 text-lg font-medium bg-violet-600/30 flex items-center justify-center"
              >
                {seat.code}
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Calcular precio */}
      {selectedSeats.length > 0 && (
        <div className="!mt-5 flex items-center gap-x-4">
          <h3 className="text-lg font-bold">Total a pagar:</h3>
          <p className="text-lg font-medium">
            USD:{" "}
            {selectedSeats.reduce((total, seat) => {
              return total + seat.price;
            }, 0)}
          </p>
        </div>
      )}

<Link
  to="/checkout"
  state={{
    selectedSeats,
    total: selectedSeats.reduce((total, seat) => total + (seat.price - (seat.price * (seat.discount / 100))), 0),  // Aplicando el descuento como porcentaje
    discount: selectedSeats.reduce((totalDiscount, seat) => totalDiscount + (seat.price * (seat.discount / 100)), 0)  // Calculando el total de los descuentos
   
  }}
> 
  <div className="flex justify-center items-center py-10">
    <button
      className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
      type="button"
    >
      Comprar Boletos
    </button>
  </div>
</Link>

    </div>
  );
};

export default ConcertSeatLayout;
