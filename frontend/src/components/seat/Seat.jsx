import React from 'react';
import { MdOutlineChair } from 'react-icons/md';

const Seat = ({ seatNumber, isSelected, onClick, seatType, seatState }) => {
  // Función para obtener el color del asiento basado en su estado y tipo
  const getSeatColor = () => {
    console.log(`Seat State: ${seatState}, Seat Type: ${seatType}, Is Selected: ${isSelected}`);
    
    // Si el asiento está vendido, color rojo
    if (seatState === 'Sold') return 'text-red-400'; 
    
    // Si el asiento está seleccionado, color morado
    if (isSelected) return 'text-violet-600'; 
    
    // Dependiendo del tipo de asiento, cambiamos el color
    switch (seatType) {
      case 'General':
        return 'text-gray-400'; // Gris para General
      case 'VIP':
        return 'text-black'; // Negro para VIP
      case 'Palco':
        return 'text-orange-400'; // Naranja para Palco
      default:
        return 'text-neutral-600'; //
    }
  };

  return (
    <MdOutlineChair
      className={`text-3xl -rotate-180 ${getSeatColor()} ${seatState === 'Sold' ? 'cursor-not-allowed' : 'cursor-pointer'}`}
      onClick={seatState !== 'sold' ? onClick : null} // Deshabilitar click si el asiento está vendido
    />
  );
};

export default Seat;
