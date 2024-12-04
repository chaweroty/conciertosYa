import React, { useState } from "react";

const SearchBar = ({ value, onChange }) => {
  const [focused, setFocused] = useState(false);

  return (
    <div
      className={`relative transition-all ${
        focused ? "w-64 bg-white" : "w-80"
      } mx-auto`}
    >
      <input
        type="text"
        value={value}
        onChange={onChange}
        onFocus={() => setFocused(true)}
        onBlur={() => setFocused(false)}
        placeholder="Buscar por artista, lugar o fecha "
        className={`w-full py-3 pl-11 pr-6 text-gray-700 bg-white border rounded-full outline-none transition-all ${
          focused ? "border-blue-500" : "border-gray-300"
        }`}
      />
      <div className="absolute inset-y-0 left-0 flex items-center pl-3">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className={`w-5 h-5 transition-all ${
            focused ? "text-blue-500" : "text-gray-400"
          }`}
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
          />
        </svg>
      </div>
    </div>
  );
};

export default SearchBar;
