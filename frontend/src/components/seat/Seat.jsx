import React from 'react';
import { MdOutlineChair } from 'react-icons/md';

const Seat = ({ seatNumber, isSelected, onClick, seatType }) => {
  const getSeatColor = () => {
    if (isSelected) return 'text-violet-600'; // Morado para asientos seleccionados
    switch (seatType) {
      case 'general':
        return 'text-gray-400'; // Gris para General
      case 'vip':
        return 'text-black'; // Negro para VIP
      case 'palco':
        return 'text-orange-400'; // Naranja para Palco
      default:
        return 'text-neutral-600';
    }
  };

  return (
    <MdOutlineChair
      className={`text-3xl -rotate-180 cursor-pointer ${getSeatColor()}`}
      onClick={onClick}
    />
  );
};

export default Seat;
