import React, { useState } from 'react';
import Seat from './Seat';
import { GiSteeringWheel } from 'react-icons/gi';
import { MdOutlineChair } from 'react-icons/md';
import { RiMoneyRupeeCircleLine } from 'react-icons/ri';
import { FaPersonRays } from "react-icons/fa6";

const ConcertSeatLayout = () => {
  const totalSeats = 41;
  const [selectedSeats, setSelectedSeats] = useState([]);

  const handleSeatClick = (seatNumber) => {
    if (selectedSeats.includes(seatNumber)) {
      setSelectedSeats(selectedSeats.filter((seat) => seat !== seatNumber));
    } else {
      if (selectedSeats.length < 10) {
        setSelectedSeats([...selectedSeats, seatNumber]);
      } else {
        alert('Solo puedes seleccionar un máximo de 10 asientos');
      }
    }
  };

  const renderSeats = () => {
    let seats = [];
    for (let i = 1; i <= totalSeats; i++) {
      seats.push(
        <Seat
          key={i}
          seatNumber={i}
          isSelected={selectedSeats.includes(i)}
          onClick={() => handleSeatClick(i)}
        />
      );
    }
    return seats;
  };

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
            <FaPersonRays  className="text-6xl text-violet-600" />
            <span className="mt-2 text-lg font-semibold text-neutral-700 dark:text-neutral-300">
              Artista
            </span>
            <div className="w-full border-b border-gray-300 mt-4"></div>
          </div>
        </div>

        {/* Asientos */}
        <div className="flex flex-col items-center space-y-4">
          <div className="w-full grid grid-cols-10 gap-x-3">
            {renderSeats().slice(0, 10)}
          </div>
          <div className="w-full grid grid-cols-10 gap-x-3">
            {renderSeats().slice(10, 20)}
          </div>
          <div className="w-full grid grid-cols-10 gap-x-3">
            <div className="col-span-9"></div>
            {renderSeats().slice(20, 21)}
          </div>
          <div className="w-full grid grid-cols-10 gap-x-3">
            {renderSeats().slice(21, 31)}
          </div>
          <div className="w-full grid grid-cols-10 gap-x-3">
            {renderSeats().slice(31, 41)}
          </div>
          <div className="w-full grid grid-cols-10 gap-x-3">
            {renderSeats().slice(31, 41)}
          </div>
        </div>
      </div>

      {/* Instructions and Information */}
      <div className="space-y-3 w-28">
        <div className="flex items-center gap-x-2">
          <MdOutlineChair className="text-lg text-neutral-500 -rotate-90" />
          <p className="text-neutral-900 dark:text-neutral-200 text-sm font-normal">
            Disponibles
          </p>
        </div>
        <div className="flex items-center gap-x-2">
          <MdOutlineChair className="text-lg text-red-500 -rotate-90" />
          <p className="text-neutral-900 dark:text-neutral-200 text-sm font-normal">
            Reservado
          </p>
        </div>
        <div className="flex items-center gap-x-2">
          <MdOutlineChair className="text-lg text-violet-500 -rotate-90" />
          <p className="text-neutral-900 dark:text-neutral-200 text-sm font-normal">
            Seleccionado
          </p>
        </div>
        <div className="flex items-center gap-x-2">
          <RiMoneyRupeeCircleLine className="text-lg text-neutral-500" />
          <p className="text-neutral-900 dark:text-neutral-200 text-sm font-normal">
            Disponible
          </p>
        </div>
      </div>

      {/* Selected seats */}
      {selectedSeats.length > 0 && (
        <div className="!mt-10">
          <h3 className="text-lg font-bold">Asientos Seleccionados:</h3>
          <div className="flex flex-wrap">
            {selectedSeats.map((seat) => (
              <div
                key={seat}
                className="w-10 h-10 rounded-md m-1.5 text-lg font-medium bg-violet-600/30 flex items-center justify-center"
              >
                {seat}
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Calculate price */}
      {selectedSeats.length > 0 && (
        <div className="!mt-5 flex items-center gap-x-4">
          <h3 className="text-lg font-bold">Total a pagar:</h3>
          <p className="text-lg font-medium">Rs. {selectedSeats.length * 750}</p>
          <span className="text-sm text-neutral-400 dark:text-neutral-600 font-normal">
            (Se incluyen todos los impuestos)
          </span>
        </div>
      )}
    </div>
  );
};

export default ConcertSeatLayout;
