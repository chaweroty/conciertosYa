import React, { useState, useEffect } from 'react';
import Seat from './Seat';
import { GiSteeringWheel } from 'react-icons/gi';
import { MdOutlineChair } from 'react-icons/md';
import { RiMoneyRupeeCircleLine } from 'react-icons/ri';
import { FaPersonRays } from "react-icons/fa6";

const ConcertSeatLayout = ({ capacityGeneral, capacityVip, capacityPalco }) => {
    const [selectedSeats, setSelectedSeats] = useState([]);

    // Crear asientos dinámicamente
  const generateSeats = (capacity, section) => {
    return Array.from({ length: capacity }, (_, index) => ({
      id: `${section}-${index + 1}`,
      section,
    }));
  };

  const seats = [
    ...generateSeats(capacityGeneral, "General"),
    ...generateSeats(capacityVip, "VIP"),
    ...generateSeats(capacityPalco, "Palco"),
  ];

  const handleSeatClick = (seatId) => {
    if (selectedSeats.includes(seatId)) {
      setSelectedSeats(selectedSeats.filter((seat) => seat !== seatId));
    } else if (selectedSeats.length < 10) {
      setSelectedSeats([...selectedSeats, seatId]);
    } else {
      alert("Solo puedes seleccionar un máximo de 10 asientos");
    }
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
                                <div key={rowIndex} className="w-full grid grid-cols-10 gap-x-3">
                                    {seats
                                        .slice(rowIndex * 10, rowIndex * 10 + 10)
                                      
                                        .map((seat) => (
                                            <Seat
                                                key={seat.id}
                                                seatNumber={seat.code}
                                                isSelected={selectedSeats.includes(seat.id)}
                                                onClick={() => handleSeatClick(seat.id)}
                                                seatType={seat.section} 
                                            />
                                        ))}
                                </div>
                            ))}
                        </>
                    ) : (
                        <p>Cargando asientos...</p>
                    )}
                </div>
            </div>

            {/* Instructions and Information */}
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
