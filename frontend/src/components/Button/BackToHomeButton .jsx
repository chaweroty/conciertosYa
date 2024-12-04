import React from "react";
import { useNavigate } from "react-router-dom";

const BackToHomeButton = () => {
  const navigate = useNavigate();

  return (
    <button
      className="
        group
        p-5
        cursor-pointer 
        relative  
        text-xl 
        font-normal 
        border-0 
        flex 
        items-center 
        justify-center
        bg-transparent
        text-blue-500 
        h-auto  
        w-[170px]  
        overflow-hidden   
        transition-all
        duration-100"
      onClick={() => navigate("/profile")} 
    >
      <span className="
        group-hover:w-full
        absolute 
        left-0 
        h-full 
        w-5 
        border-y
        border-l
        border-blue-500
        transition-all
        duration-500">
      </span>

      <p className="group-hover:opacity-0 group-hover:translate-x-[-100%] absolute translate-x-0 transition-all
        duration-200">Home Page</p>

      <span className="group-hover:translate-x-0 group-hover:opacity-100 absolute translate-x-full opacity-0 transition-all duration-200">Gracias!
      </span>

      <span
        className="group-hover:w-full absolute right-0 h-full w-5 border-y border-r  border-blue-500 transition-all duration-500">
      </span>
    </button>
  );
};

export default BackToHomeButton;
