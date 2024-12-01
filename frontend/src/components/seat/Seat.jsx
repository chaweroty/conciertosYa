import React from 'react';
import { MdOutlineChair } from 'react-icons/md';

const Seat = ({ seatNumber, isSelected, onClick, seatType }) => {
  const getSeatColor = () => {
    console.log(seatType);
    if (isSelected) return 'text-violet-600'; // Morado para asientos seleccionados
    switch (seatType) {
      case 'General':
        return 'text-gray-400'; // Gris para General
      case 'VIP':
        return 'text-black'; // Negro para VIP
      case 'Palco':
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
