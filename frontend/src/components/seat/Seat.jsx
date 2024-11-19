import React from 'react';
import { MdOutlineChair } from 'react-icons/md';

const Seat = ({ seatNumber, isSelected, onClick }) => {
  return (
    <MdOutlineChair
      className={`text-3xl -rotate-90 cursor-pointer ${
        isSelected ? 'text-violet-600' : 'text-neutral-600'
      }`}
      onClick={onClick}
    />
  );
};

export default Seat;
